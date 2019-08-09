package c.ponom.pocketlibrary;

import android.app.Application;
import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.core.app.ApplicationProvider;

import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;

import c.ponom.pocketlibrary.DI.DIclass;
import c.ponom.pocketlibrary.Database.NetworkLoaders.HTMLCustomParsers;
import c.ponom.pocketlibrary.Database.Repository;
import c.ponom.pocketlibrary.Database.RoomEntities.Author;
import c.ponom.pocketlibrary.Database.RoomEntities.Book;
import c.ponom.pocketlibrary.Database.RoomEntities.SubChapter;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class UnitTestSampleJava {
        private static final String FAKE_STRING = "HELLO_WORLD";

        private Context context;




    @Before
    public void init() {
        context = ApplicationProvider.getApplicationContext();
    }



        @Test
        public synchronized void testRepository()  {

            Repository repository;
            Application application= (Application) context.getApplicationContext();
            repository = DIclass.getRepository();
            assertNotNull(repository);
            assertNotNull(application);

            testBookAddRemove(repository);
            testChapterLoad(context);

        }

    private void testChapterLoad(Context context) {
        ArrayList<SubChapter> subChapters =HTMLCustomParsers.parseMainPage(initStringMainPage());
         assertTrue (subChapters.size()>15);

         for (SubChapter chapter:subChapters) System.out.println(chapter.subChapterName);
        ArrayList<Author> authors =HTMLCustomParsers.parseSubChapter(getChapter(),subChapters.get(6));
        for (Author author:authors) System.out.println(author.authorName);


    }

    private void testBookAddRemove(Repository repository) {
        Book book=new Book();
        book.bookName="testBook";
        book.sizeInKb=12;

        repository.insertRecord(book);
        assertEquals(12, repository.getTotalLoadedKbForTest());

        book=new Book();
        book.bookName="testBook2";
        book.sizeInKb=14;
        repository.insertRecord(book);
        System.out.println(repository.getTotalLoadedKbForTest());
        assertEquals(26, repository.getTotalLoadedKbForTest());

        repository.clearBooks();
        assertEquals(0, repository.getTotalLoadedKbForTest());
    }

    private String initStringMainPage() {
        return "<html><head><title>Lib.Ru: Библиотека Максима Мошкова</title></head><body><pre>\n" +
                "<center><h1><small><a href=http://lib.ru/>Lib.Ru</a>:</small> Библиотека Максима Мошкова<br><font size=-2>При поддержке Федерального агентства по печати и массовым коммуникациям.</font></h1><font size=-1><form action=GrepSearch method=GET><b>Поиск</b>:<INPUT TYPE=text NAME=Search size=9><input type=submit value=search></font> <b><a href=/PROZA/>Проза</a> <a href=/INPROZ/>Переводы</a> <a href=/POEZIQ/>Поэзия</a> <a href=/RUFANT/>Фантастика</a> <a href=/RUSS_DETEKTIW/>Детективы</a> <a href=http://az.lib.ru/>Классика</a> <a href=/HISTORY/>История</a> &nbsp;<a href=/.dir_StripDir.html>И ДР.<small>>>></small></a></b></form></center><center>[<a href=What-s-new><b>НОВИНКИ</b></a>][<a href=/HITPARAD/><b>Хитпарад</b></a>][<a href=http://samlib.ru/>Самиздат</a>][<a href=http://music.lib.ru/>Музыка</a>][<a href=http://www.artlib.ru/>Художники</a>][<a href=http://world.lib.ru>Заграница</a>][<a href=http://turizm.lib.ru>Туризм</a>][<a href=http://artofwar.ru><b>ArtOfWar</b></a>][<a href=http://okopka.ru>Окопка</a>][<a href=/Forum/>Форум</a>]<br>Авторские разделы: <a href=http://lit.lib.ru>Современная</a> <a href=http://fan.lib.ru>Фантастика</a> <a href=http://det.lib.ru>Остросюжетная</a> <a href=http://4put.ru><font color=brown><b>Фотохостинг</b></font></a></noindex> [<a href=Mirrors><b>Зеркала</b></a>]<small> [<b><a \n" +
                " href=/koi/>koi</a></b>-<b><a \n" +
                " href=/win/>win</a></b>-<b><a \n" +
                " href=/lat/>lat</a></b>] </small> <img src=http://counter.rambler.ru/top100.cnt?59291 width=1 height=1 border=0><!--hr noshade size=1--></center></pre><font size=-1><b>23 aug 06. 5.5Gb</b><META NAME=\"keywords\" CONTENT=\"CompuLib,LibMain,LibMoshkow,LibAuthor\">. Самая известная в Рунете www-библиотека, открыта в 1994. Авторы и читатели\n" +
                "ежедневно пополняют ее. Художественная литература, фантастика и политика, техдокументация и юмор,\n" +
                "история и поэзия, КСП и русский рок, туризм и парашютизм, философия и эзотерика, и т.д. и т.п.<br></font>\n" +
                "<font size=-1>\n" +
                "<a href=AWARDS/>Награды</a>:<b>НИП-2003,POTOP, IT-100,РИФ-2001</b>.\n" +
                "<br></font>\n" +
                "<font size=-2><b>ЗЕРКАЛА:</b> <noindex><a\n" +
                "href=http://lib.ru/>lib.ru</a>, <a\n" +
                "href=http://www.kulichki.com/moshkow/>зеркало</a> на <a href=http://www.kulichki.com/>kulichki.com</a>,\n" +
                "<a\n" +
                "href=http://moshkow.tomsk.ru/koi/>tomsk.ru</a> <a\n" +
                "href=http://lib.misto.kiev.ua/>Украина (UA-IX)</a> <a\n" +
                "href=http://lib.himoza.org/>himoza.org</a>\n" +
                "</noindex>\n" +
                "</font>\n" +
                "<table align=right><tr><td>\n" +
                "<table border=1 cellspacing=0 width=260><tr>\n" +
                "<td bgcolor=blue>\n" +
                "<a href=What-s-new><b><font color=white>Новинки Lib.ru</font></b></a>\n" +
                "</td></tr><tr><td><noindex><small>\n" +
                "<a href=http://lit.lib.ru/w/wojskunskij_e_l/text_0010.shtml\n" +
                "><b>Е.Войскунский</b>. Балтийская сага</a>\n" +
                "<small>(Проза)</small><br>\n" +
                "<a href=http://okopka.ru/r/rybas_t_m/text_0010.shtml\n" +
                "><b>Т.Рыбас</b>. Красный снег</a>\n" +
                "<small>(Гражданская война)</small><br>\n" +
                "<a href=http://okopka.ru/b/bobrow_g_l/text_1040-1.shtml\n" +
                "><b>Комкин, Бобров</b>. Новий свiт</a>\n" +
                "<small>(Сценарий)</small><br>\n" +
                "<a href=http://lit.lib.ru/f/fedjukin_weniamin_konstantinowich/newedit.shtml\n" +
                "><b>В.Федюкин</b>. Теория сверхдианамагничиваемости</a>\n" +
                "<small>(Физические гипотезы)</small><br>\n" +
                "<a href=http://fan.lib.ru/e/eskov/marlowe.shtml\n" +
                "><b>К.Еськов</b>. Чиста английское убийство</a>\n" +
                "<small>(Исторический детектив)</small><br>\n" +
                "<a href=http://lit.lib.ru/c/chernyshkow_a_w/galya.shtml\n" +
                "><b>А.Чернышков</b>. Галя</a>\n" +
                "<small>(Лирическая повесть)</small><br>\n" +
                "<a href=http://lit.lib.ru/a/ancharow_m_l/ancharov_biografia.shtml\n" +
                "><b>Ревич, Юровский</b>. Михаил Анчаров. Писатель, бард, художник</a>\n" +
                "<small>(ЖЗЛ)</small><br>\n" +
                "<a href=http://artofwar.ru/k/klimkow_o_g/hellfire.shtml\n" +
                "><b>Э.Мейси</b>. Хэллфайр</a>\n" +
                "<small>(Военные мемуары)</small><br>\n" +
                "<a href=http://lit.lib.ru/k/kriger_b_j/text_0360.shtml\n" +
                "><b>Б.Кригер</b>. В бездне</a>\n" +
                "<small>(Роман)</small><br>\n" +
                "<a href=http://lit.lib.ru/s/sadur_n_n/text_0250.shtml\n" +
                "><b>Н.Садур</b>. Мальчик-небо\n" +
                "<small>(Пьеса)</small><br>\n" +
                "</small></noindex>\n" +
                "</td></tr></table></td></tr><tr><td>\n" +
                "</td></tr></table>\n" +
                "<br>\n" +
                "<table border=1 bgcolor=#eeeeee><tr><td align=center>&nbsp;&nbsp;&nbsp;<b><a href=http://pressa-voiny.ru/><font color=brown>Проект \"Пресса Войны\" просит помощи</font></a></b>&nbsp;&nbsp;&nbsp;<br>Пресса, статьи, фотографии, сканы газет<br> сообщения Совинформбюро за 1941-1945 гг</td></tr></table><a href=http://www.recself.ru><b>\"RecSelf\"</b> - служба пользовательских торговых объявлений \"куплю/продам\"</a><br>\n" +
                "<a href=https://boriskriger.com/audiobooks/>Аудиокниги <b>БОРИСА КРИГЕРА</b></a><br>\n" +
                "<br>\n" +
                "<li><tt><small>( &nbsp;0k)  &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=What-s-new><b>НОВЫЕ ПОСТУПЛЕНИЯ В БИБЛИОТЕКУ</b></A>\n" +
                "<li><tt><small>(153 ) <font color=green> 25 Jun</font></small></tt> <A HREF=PROZA/><b>РУССКАЯ СОВРЕМЕННАЯ ПРОЗА</b></A>\n" +
                "<li><tt><small> </small></tt> <A HREF=http://artofwar.ru><b><tt><small>(163 ) </small></tt><font color=brown>Мемуары Чеченской войны</font></b></A>\n" +
                "<li><tt><small>(203 )  &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=POEZIQ/><b>РУССКАЯ И ЗАРУБЕЖНАЯ ПОЭЗИЯ</b></A>\n" +
                "<li><tt><small>(193 )  &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=INPROZ/><b>ЗАРУБЕЖНАЯ ПРОЗА</b></A>\n" +
                "<li><tt><small>( 56 )  &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=DETEKTIWY/><b>ЗАРУБЕЖНЫЙ ДЕТЕКТИВ</b></A>\n" +
                "<li><tt><small>(239 )  &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=RUFANT/><b>СОВЕТСКАЯ ФАНТАСТИКА</b></A>\n" +
                "<li><tt><small>(190 )  &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=INOFANT/><b>ЗАРУБЕЖНАЯ ФАНТАСТИКА</b></A>\n" +
                "<li><tt><small>(300 ) <font color=green> 15 Jun</font></small></tt> <A HREF=KSP/><b>Авторская песня и русский рок</b></A>\n" +
                "<li><tt><small>(174 )  &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=ALPINISM/><b>Альпинизм и горный туризм</b></A>\n" +
                "<li><tt><small>(198 )  &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=PARACHUTE/><b>Парашютизм</b></A>\n" +
                "<noindex>\n" +
                "<li><tt><small> </small></tt> <A HREF=http://www.artlib.ru><b><tt><small>(797 ) </small></tt><font color=green>Библиотека изобразительных искусств</font></b></A>\n" +
                "<li><tt><small> </small></tt> <A HREF=http://samlib.ru><b><tt><small>(9897) </small></tt><font color=green>Литературный журнал \"Самиздат\"</font></b></A>\n" +
                "<li><tt><small> </small></tt> <A HREF=http://music.lib.ru><b><tt><small>(8467) </small></tt><font color=green>MP3: \"Музыкальный хостинг\"</font></b></A>\n" +
                "<li><tt><small> </small></tt> <A HREF=http://world.lib.ru><b><tt><small>(847 ) </small></tt><font color=green>Зарубежные впечатления \"Заграница\"</font></b></A>\n" +
                "<br>\n" +
                "<li><tt><small>(266 )  &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=EMIGRATION/><b>Впечатления о заграничной жизни</b></A>\n" +
                "<li><tt><small>(110 )  &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=ENGLISH/><b>Учим английский язык</b></A>\n" +
                "<li><tt><small>( 57 )  &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=CINEMA/><b>Кинофильмы, TV, video...</b></A>\n" +
                "<li><tt><small>(110 )  &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=SONGS/><b>Зарубежная рок-музыка</b></A>\n" +
                "</noindex>\n" +
                "<li><tt><small>(248 )  &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=ANEKDOTY/><b>Юмор</b></A>\n" +
                "<br>\n" +
                "<dir><dir><a name=0></a><h2>ПРОЗА, ПОЭЗИЯ</h2></dir></dir>\n" +
                "<br>\n" +
                "<li><tt><small>(203 )  &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=POEZIQ/><b>РУССКАЯ И ЗАРУБЕЖНАЯ ПОЭЗИЯ</b></A>\n" +
                "<li><tt><small>(153 ) <font color=green> 25 Jun</font></small></tt> <A HREF=PROZA/><b>РУССКАЯ   СОВРЕМЕННАЯ ПРОЗА</b></A>\n" +
                "<li><tt><small>( 64 )  &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=RUSSLIT/><b>РУССКАЯ   ДОВОЕННАЯ ЛИТЕРАТУРА</b></A>\n" +
                "<li><tt><small>(212 )  &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=LITRA/><b>РУССКАЯ   КЛАССИКА</b></A>\n" +
                "<li><tt><small>( &nbsp;6 )  &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=SU/><b>ЛИТЕРАТУРА БЛИЖНЕГО ЗАРУБЕЖЬЯ</b></A>\n" +
                "<li><tt><small>( 75 )  &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=PXESY/><b>СОВРЕМЕННАЯ ДРАМАТУРГИЯ</b></A>\n" +
                "<li><tt><small>(231 )  &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=NEWPROZA/><b>ПРОЗА 90-х - 2000-х годов</b></A>\n" +
                "<li><tt><small>(193 )  &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=INPROZ/><b>ПЕРЕВОДНАЯ ПРОЗА</b></A>\n" +
                "<br>\n" +
                "<br>\n" +
                "<dir><dir><a name=1></a><h2>СТАРИННАЯ ЛИТЕРАТУРА</h2></dir></dir>\n" +
                "<li><tt><small>( 60 )  &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=INOOLD/><b>СТАРИННАЯ ЕВРОПЕЙСКАЯ ЛИТЕРАТУРА</b></A>\n" +
                "<li><tt><small>( 75 )  &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=POEEAST/><b>АНТИЧНАЯ  ЛИТЕРАТУРА</b></A>\n" +
                "<li><tt><small>( 34 )  &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=POECHIN/><b>КИТАЙСКАЯ ПОЭЗИЯ</b></A>\n" +
                "<br>\n" +
                "<dir><dir><a name=2></a><h2>ДЕТСКАЯ И ПРИКЛЮЧЕНЧЕСКАЯ</h2></dir></dir>\n" +
                "<li><tt><small>(144 )  &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=TALES/><b>СКАЗКИ</b></A>\n" +
                "<li><tt><small>(102 )  &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=PRIKL/><b>ПРИКЛЮЧЕНИЯ</b></A>\n" +
                "<br clear=all>\n" +
                "<br>\n" +
                "<br>\n" +
                "<br>\n" +
                "<table border=1 align=right width=40%><tr><td>\n" +
                "<dir><dir><a name=3></a><h2>МОИ ГОСТИ</h2></dir></dir>\n" +
                " <div align=right>Мавр сделал свое дело.</div>\n" +
                " <div align=right>Мавр может гулять смело.</div>\n" +
                " <div align=right>Василий Звягинцев</div>\n" +
                "<small><noindex>\n" +
                "<li><tt><small> </small></tt> <A HREF=http://foto.lib.ru/><b>Фотоархивы</b></A>\n" +
                "<li><tt><small> </small></tt> <A HREF=http://annensky.lib.ru><b>Цифровой архив Иннокентия Анненского</b></A>\n" +
                "<li><tt><small> </small></tt> <A HREF=http://ancharov.lib.ru/><b>Михаил Анчаров: официальный сайт</b></A>\n" +
                "<li><tt><small> </small></tt> <A HREF=http://vernadsky.lib.ru/><b>Архив В.И.Вернадского</b></A>\n" +
                "<li><tt><small> </small></tt> <A HREF=http://gorchev.lib.ru/><b>Дмитрий Горчев: официальный сайт</b></A>\n" +
                "<li><tt><small> </small></tt> <A HREF=http://lazandr.lib.ru/><b>Андрей Лазарчук, Ирина Андронати: официальный сайт</b></A>\n" +
                "<li><tt><small> </small></tt> <A HREF=http://sukharev.lib.ru/><b>Дмитрий Сухарев: официальный сайт</b></A>\n" +
                "<li><tt><small> </small></tt> <A HREF=http://bolotin.lib.ru/><b>Владимир Болотин: официальный сайт</b></A>\n" +
                "<li><tt><small> </small></tt> <A HREF=http://publ.lib.ru/publib.html><b>Книжная полка Вадима Ершова</b></A>\n" +
                "<li><tt><small> </small></tt> <A HREF=http://wesha.lib.ru/><b>Библиотека леопарда Веши</b></A>\n" +
                "<li><tt><small> </small></tt> <A HREF=http://yanko.lib.ru/><b>Библиотека \"Fort/Da\"  Славы Янко</b></A>\n" +
                "<li><tt><small> </small></tt> <A HREF=http://ihtik.lib.ru/><b>Библиотека Ихтика: Философия</b></A>\n" +
                "<li><tt><small> </small></tt> <A HREF=http://zzl.lib.ru/><b>Жизнь Замечательных Людей</b></A>\n" +
                "<li><tt><small> </small></tt> <A HREF=http://soc.lib.ru/><b>Библиотека \"Социология, Психология, Управление\"</b></A>\n" +
                "<li><tt><small> </small></tt> <A HREF=http://tarranova.lib.ru/><b>Архив переводов \"TarraNova\"</b></A>\n" +
                "<li><tt><small> </small></tt> <A HREF=http://ldn-knigi.lib.ru/><b>Приватное собрание книг LDN-KNIGI</b></A>\n" +
                "<li><tt><small> </small></tt> <A HREF=http://krylov.lib.ru/><b>Евгений Крылов: IT-страница</b></A>\n" +
                "<li><tt><small> </small></tt> <A HREF=http://bt.lib.ru/><b>Детская газета \"Бермудский треугольник</b></A>\n" +
                "<li><tt><small> </small></tt> <A HREF=http://avia.lib.ru/><b>История отечественной авиации</b></A>\n" +
                "<li><tt><small> </small></tt> <A HREF=http://lit.lib.ru/d/dedovshchina/><b>Армия-Дедовщина-Общество</b></A>\n" +
                "<li><tt><small> </small></tt> <A HREF=http://slovar.lib.ru/><b>Словарь литературоведческих терминов</b></A>\n" +
                "<li><tt><small> </small></tt> <A HREF=http://psi.lib.ru/><b>Psi-Project: детская психология</b></A>\n" +
                "<li><tt><small> </small></tt> <A HREF=http://tvtorrent.ru/><b>Слава Янко: ТВ-трекер</b></A>\n" +
                "</noindex>\n" +
                "<li><tt><small> </small></tt> <A HREF=http://www.krugozor-kolobok.ru/><b>Архивы звукового журнала \"Кругозор\"</b></A>\n" +
                "<li><tt><small> </small></tt> <A HREF=http://militera.lib.ru/><b>Militera - Военная литература</b></A>\n" +
                "</small></td></tr></table>\n" +
                "<dir><dir><a name=4></a><h2>ФАНТАСТИКА</h2></dir></dir>\n" +
                " <div align=right>В этом нет ничего нового,</div>\n" +
                " <div align=right>Ибо вообще ничего нового нет.</div>\n" +
                " <div align=right>Николай Рерих</div>\n" +
                "<li><tt><small>(239 )  &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=RUFANT/><b>СОВЕТСКАЯ ФАНТАСТИКА</b></A>\n" +
                "<li><tt><small>(190 )  &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=INOFANT/><b>ЗАРУБЕЖНАЯ ФАНТАСТИКА</b></A>\n" +
                "<li><tt><small>( 40 )  &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=TRANSLATION/><b>Russian sci-fi in English translation</b></A>\n" +
                "<li><tt><small>( 17 )  &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=SOCFANT/><b>СОЦИАЛИСТИЧЕСКАЯ ФАНТАСТИКА</b></A>\n" +
                "<li><tt><small>( 66 )  &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=RAZNOE/><b>ЗАРУБЕЖНАЯ ФАНТАСТИКА РОССЫПЬЮ</b></A>\n" +
                "<li><tt><small>( 94 )  &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=SCIFICT/><b>Клуб любителя фантастики</b></A>\n" +
                "<li><tt><small>( 17 )  &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=TRANSLATORS/><b>БЮРО ПЕРЕВОДОВ И ПЕРЕВОДЧИКОВ</b></A>\n" +
                "<br>\n" +
                "<dir><dir><a name=5></a><h2>ИСТОРИЯ</h2></dir></dir>\n" +
                " <div align=right>Побольше историй</div>\n" +
                " <div align=right>красивых и разных</div>\n" +
                "<li><tt><small>( 40 )  &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=HIST/><b>ИСТОРИЧЕСКИЕ РОМАНЫ(отечественные)</b></A>\n" +
                "<li><tt><small>( 16 )  &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=INOSTRHIST/><b>ИСТОРИЧЕСКИЕ РОМАНЫ(переводы)</b></A>\n" +
                "<li><tt><small>(112 )  &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=MEMUARY/><b>МЕМУАРЫ И ЖИЗНЕОПИСАНИЯ</b></A>\n" +
                "<li><tt><small>(128 )  &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=HISTORY/><b>ИСТОРИЯ (наука и гипотезы)</b></A>\n" +
                "<br>\n" +
                "<dir><dir><a name=6></a><h2>ДЕТЕКТИВЫ</h2></dir></dir>\n" +
                " <div align=right>\"Было бы величайшей ошибкой</div>\n" +
                " <div align=right>думать\"</div>\n" +
                " <div align=right>В.И.Ленин, ПСС, т.41, с.55.</div>\n" +
                "<li><tt><small>( 66 )  &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=RUSS_DETEKTIW/><b>РУССКИЙ ДЕТЕКТИВ И БОЕВИК</b></A>\n" +
                "<li><tt><small>( 56 )  &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=DETEKTIWY/><b>ПЕРЕВОДНЫЕ ДЕТЕКТИВЫ</b></A>\n" +
                "<li><tt><small>( 68 )  &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=PRAWO/><b>Законы, акты, постановления</b></A>\n" +
                "<br>\n" +
                "<dir><dir><a name=7></a><h2>КУЛЬТУРА, ...СОФИЯ, ...ЛОГИЯ...</h2></dir></dir>\n" +
                "<li><tt><small>( 59 )  &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=CULTURE/><b>КУЛЬТУРА</b></A>\n" +
                "<li><tt><small>( 72 )  &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=FILOSOF/><b>ФИЛОСОФИЯ</b></A>\n" +
                "<li><tt><small>( 64 )  &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=URIKOVA/SANTEM/><b>ЙОГА</b></A>\n" +
                "<li><tt><small>( 81 )  &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=URIKOVA/><b>ЭЗОТЕРИКА</b></A>\n" +
                "<li><tt><small>( &nbsp;9 )  &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=ASTROLOGY/><b>АСТРОЛОГИЯ</b></A>\n" +
                "<li><tt><small>( &nbsp;8 )  &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=RELIGION/><b>РЕЛИГИОЗНАЯ ЛИТЕРАТУРА</b></A>\n" +
                "<li><tt><small>( 20 )  &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=DIALEKTIKA/><b>\"Диалектические\" книги</b></A>\n" +
                "<li><tt><small>(176 )  &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=POLITOLOG/><b>ПОЛИТОЛОГИЯ</b></A>\n" +
                "<li><tt><small>( 70 )  &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=PSIHO/><b>ПСИХОЛОГИЯ</b></A>\n" +
                "<li><tt><small>( 39 )  &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=NLP/><b>Нейролингвистическое программирование</b></A>\n" +
                "<li><tt><small>( 68 ) <font color=#eed011> 25 Jul</font></small></tt> <A HREF=DPEOPLE/><b>Руководства по прикладной психологии</b></A>\n" +
                "<br>\n" +
                "<dir><dir><a name=8></a><h2>НАУЧНАЯ И УЧЕБНИКИ</h2></dir></dir>\n" +
                "<br>\n" +
                "<li><tt><small>( 36 )  &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=KIDS/><b>Взрослым о детях</b></A>\n" +
                "<li><tt><small>( 44 )  &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=NTL/><b>Научно-популярная литература</b></A>\n" +
                "<li><tt><small>( &nbsp;6 )  &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=DIC/><b>Словари</b></A>\n" +
                "<li><tt><small>( 30 )  &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=TEXTBOOKS/><b>Учебники</b></A>\n" +
                "<li><tt><small>( 18 )  &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=NATUR/><b>Природоведение и зоология</b></A>\n" +
                "<li><tt><small>( 19 )  &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=NTL/MED/><b>Медицинская литература</b></A>\n" +
                "<li><tt><small>( &nbsp;5 )  &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=BIBLIOGR/><b>Библиография (наука такая)</b></A>\n" +
                "<li><tt><small>( &nbsp;7 )  &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=NTL/SPORT/><b>Спортивная литература</b></A>\n" +
                "<li><tt><small>( 26 )  &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=ECONOMY/><b>Бухучет, финансы, банки</b></A>\n" +
                "<br>\n" +
                "<dir><dir><a name=9></a><h2>UNIX'ОИДАМ ВСЕХ СТРАН</h2></dir></dir>\n" +
                " <div align=right>\"Есть многое на свете, друг Горацио,</div>\n" +
                " <div align=right>что человеку знать не положено\".</div>\n" +
                " <div align=right>Гамлет</div>\n" +
                "<br>\n" +
                "<li><tt><small> </small></tt> <A HREF=unixhelp/><b>Справочник начинающего Unix'иста</b></A>\n" +
                "<li><tt><small> </small></tt> <A HREF=VMWARE/><b>VMware, vSphere, ESX</b></A>\n" +
                "<li><tt><small> </small></tt> <A HREF=LINUXGUIDE/><b>Книги по Unix на русском</b></A>\n" +
                "<li><tt><small> </small></tt> <A HREF=UNIXOID/><b>Клуб Настоящих Unix'оидов</b></A>\n" +
                "<li><tt><small> </small></tt> <A HREF=WEBMASTER/><b>Записки Web-мастера</b></A>\n" +
                "<li><tt><small> </small></tt> <A HREF=SECURITY/><b>Security и firewall'ы</b></A>\n" +
                "<li><tt><small> </small></tt> <A HREF=TECHBOOKS/><b>Технические руководства</b></A>\n" +
                "<li><tt><small> </small></tt> <A HREF=CTOTOR/><b>УЧЕБНИКИ ПО ПРОГРАММИРОВАНИЮ, C, C++</b></A>\n" +
                "<br>\n" +
                "<dir><dir><a name=10></a><h2>ОЖИДАЕТСЯ</h2></dir></dir>\n" +
                " <div align=right>They might or they might not,</div>\n" +
                " <div align=right>You  never  can  tell  with bees.</div>\n" +
                " <div align=right>Winnie-the-Pooh.</div>\n" +
                "Дмитрий Гусаров \"За чертой милосердия\"\n" +
                "<!-- Evgenij V. Keppul\" <eugene@pub.osf.lt> -->\n" +
                "<a href=Forum/newocr.unl\n" +
                ">И другие книжки, перечисленные здесь</a>\n" +
                "Выставить оценки хитпарада в оглавления каталогов\n" +
                "Сплющить корневую страницу до 10 Kb\n" +
                "<li><tt><small> </small></tt> <A HREF=DESIGN/><b>С 1994 года библиотека не меняла дизайн. И не планирует.</b></A>\n" +
                "Спец-guestbook: Аннотации к книгам. Букинистическо-обменный \"магазин\"\n" +
                "Может, еще кого посоветуете?\n" +
                "<br>\n" +
                "<dir><dir><a name=11></a><h2>ХОТЕЛОСЬ БЫ ДОСТАТЬ</h2></dir></dir>\n" +
                " <div align=right>\"Собрать все книги бы да сжечь!\"</div>\n" +
                " <div align=right>Грибоедов</div>\n" +
                "<br>\n" +
                "<br>\n" +
                "Олаф Стэйплдон, Станислав Дыгат \"Путешествия\",\n" +
                "Арнольд Казьмин \"Теория интеллекта\"\n" +
                "<li><tt><small> </small></tt> <A HREF=Forum/book.unl><b>А здесь пожелания читателей библиотеки</b></A>\n" +
                "<br>\n" +
                "<dir><dir><a name=12></a><h2>БИБЛИОТЕКАРЮ, СЛУЖЕБНОЕ</h2></dir></dir>\n" +
                " <div align=right>\"Единственное,  что  надежнее,</div>\n" +
                " <div align=right>чем магия - это твои друзья\".</div>\n" +
                " <div align=right>Макбет</div>\n" +
                "<li><tt><small> </small></tt> <A HREF=litarhiwy.dir><b>Литературные архивы в Интернетe</b></A>\n" +
                "<li><tt><small> </small></tt> <A HREF=COMPULIB/><b>Электронные библиотеки, объединяйтесь</b></A>\n" +
                "<li><tt><small> </small></tt> <A HREF=TXT/><b>Разнообразные тексты</b></A>\n" +
                "<li><tt><small> </small></tt> <A HREF=TXT/incoming.txt><b>Если у Вас есть что положить в \"мою библиотеку\"</b></A>\n" +
                "<li><tt><small> </small></tt> <A HREF=COPYRIGHT/><b>О правах, разрешениях, copyright</b></A>\n" +
                "<br>\n" +
                "<br>\n" +
                "<br>\n" +
                "<dir><dir><a name=13></a><h2>СТАТИСТИКА</h2></dir></dir>\n" +
                " <div align=right>Big browser is watching you.</div>\n" +
                " <div align=right>Старший Брат</div>\n" +
                "<br>\n" +
                "На 1 Oct 2003 библиотека содержит 4000Mb/21200 шт. текстовых\n" +
                "и 400Mb/37000 шт. прочих файлов.\n" +
                ". Статистикой зафиксировано с 1 по 30 сентября:\n" +
                ". Uniq.hosts                                      -    42,000\n" +
                ". Всего выдано документов                         - 25,800,000\n" +
                ". Total traffic                                   -  1,200 Gb\n" +
                "</pre><hr>\n" +
                "<center>Visitors: <small>\n" +
                "<b>1994 Dec:</b>18,\n" +
                "<b>1995 Oct:</b>750,930,1300,\n" +
                "<b>1996:</b>1500,1900,2600,2900,3300,4100,5200,5200,6000,7000,11000,6500,\n" +
                "<b>1997:</b>10800,10300,6000,6000,6500,6100,4000,4000,4000,6200,8800,12600\n" +
                "<b>1998:</b>14000,11200,13900,,7700,18000,,,29500,32500\n" +
                "</small></center>\n" +
                "</pre><center><hr noshade><table cellpadding=0 cellspacing=0 border=0 width=99%><tr><td align=left><b>l8+&nbsp;</b> <small>Свидетельство о регистрации СМИ Эл No ФС 77-20625 выдано ФС по надзору за соблюдением законодательства в сфере массовых коммуникаций и охране культурного наследия 12.05.2005</small><td></tr></table><TABLE cellpadding=0 cellspacing=0 border=0 WIDTH=99% align=center><TR><td align=left><b>Home: </b><a href=http://lib.ru/~moshkow/><small><i>http://lib.ru/~moshkow/</i></small></a></td><td align=center><b>Маil: </b><u><small><i>max&#64lib&#46ru</i></small></u></td><td align=right><noindex><b>Hosted by <u>RTComm</u></b></noindex></td></tr></table><hr noshade></center>\n" +
                "<table cellpadding=0 cellspacing=0 border=0 bgcolor=#1111aa align=center><tr><td bgcolor=#aaffee><center><small></small></center>\n" +
                "</td></td><td bgcolor=#eeeeee valign=top align=center><p><small>\n" +
                "<a href=http://www.litportal.ru/>Литпортал</a>\n" +
                "</small>\n" +
                "<br clear=all></td><td width=88 valign=top><noindex><a target=_top href=http://top.list.ru/jump?from=105282><img src=http://top.list.ru/counter?id=105282;t=99 border=0 height=24 width=88 alt=TopList></a><a href=http://top100.rambler.ru/top100/Literature/><img src=http://top100-images.rambler.ru/top100/b0.gif alt=Rambler_s_Top100 width=81 height=31 border=0></a><table cellspacing=0 cellpadding=0 border=0 width=88><tr bgcolor=#dadada><td align=left><img src=http://u083.69.spylog.com/cnt?p=0 alt=spylog border=0 width=88 height=24></a></td></tr></table></noindex></td></tr></table></body></html>";
    }



    private String getChapter(){
    return "<html><head><title>Lib.Ru: Русская литература 10x-50х годов</title></head>\n"+
            "<body>\n"+
            "<pre><center><h1><small><a href=/>Lib.Ru</a>: </small>Русская литература 10x-50х годов</h1><font size=-1><form action=GrepSearch method=GET><b>Поиск</b>:<INPUT TYPE=text NAME=Search size=9><input type=submit value=search></font> <b><a href=/PROZA/>Проза</a> <a href=/INPROZ/>Переводы</a> <a href=/POEZIQ/>Поэзия</a> <a href=/RUFANT/>Фантастика</a> <a href=/RUSS_DETEKTIW/>Детективы</a> <a href=http://az.lib.ru/>Классика</a> <a href=/HISTORY/>История</a> &nbsp;<a href=/.dir_StripDir.html>И ДР.<small>>>></small></a></b></form></center><center>[<a href=What-s-new><b>НОВИНКИ</b></a>][<a href=/HITPARAD/><b>Хитпарад</b></a>][<a href=http://samlib.ru/>Самиздат</a>][<a href=http://music.lib.ru/>Музыка</a>][<a href=http://www.artlib.ru/>Художники</a>][<a href=http://world.lib.ru>Заграница</a>][<a href=http://turizm.lib.ru>Туризм</a>][<a href=http://artofwar.ru><b>ArtOfWar</b></a>][<a href=http://okopka.ru>Окопка</a>][<a href=/Forum/>Форум</a>]<br>Авторские разделы: <a href=http://lit.lib.ru>Современная</a> <a href=http://fan.lib.ru>Фантастика</a> <a href=http://det.lib.ru>Остросюжетная</a> <a href=http://4put.ru><font color=brown><b>Фотохостинг</b></font></a></noindex> [<a href=Mirrors><b>Зеркала</b></a>]<small> [<b><a href=/koi/RUSSLIT/>koi</a></b>-<b><a href=/win/RUSSLIT/>win</a></b>-<b><a href=/lat/RUSSLIT/>lat</a></b>]</small> </center><hr noshade size=1></pre><small>(размер) [rate]  дата модиф. </small><dir><dir><a name=0></a><h2>См. также</h2></dir></dir>\n"+
            "<li><tt><small><b>dir</b>(193 )  &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=../INPROZ/><b>Переводная проза</b></A>\n"+
            "<li><tt><small><b>www</b>( 14 )  [ &nbsp;35] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=http://az.lib.ru><b>Русская классика</b></A>\n"+
            "<li><tt><small><b>dir</b>(153 ) <font color=green> 25 Jun</font></small></tt> <A HREF=../PROZA/><b>Современная русская проза</b></A>\n"+
            "<li><tt><small><b>dir</b>(231 ) [ &nbsp;16] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=../NEWPROZA/><b>Русская проза 90-х годов</b></A>\n"+
            "<li><tt><small><b>www</b>( 14 )  [ &nbsp;36] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=http://lit.lib.ru><b>Русская проза 2000-х годов</b></A>\n"+
            "<br>\n"+
            "<dir><dir><a name=1></a><h2>Авторы</h2></dir></dir>\n"+
            "<li><tt><small><b>dir</b>( &nbsp;7 ) [ &nbsp; 3] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=AWERCHENKO/><b>Аверченко Аркадий</b></A>\n"+
            "<li><tt><small><b>dir</b>( &nbsp;1 ) [ &nbsp; 3] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=AWILOWA/><b>Авилова Лидия</b></A>\n"+
            "<li><tt><small><b>dir</b>( &nbsp;2 ) [ &nbsp; 4] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=../AGEEV/><b>Агеев Михаил</b></A>\n"+
            "<li><tt><small><b>www</b>( 14 )  [ &nbsp;38] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=http://az.lib.ru/a/aldanow_m_a/><b>Алданов Марк</b></A>\n"+
            "<li><tt><small><b>www</b>( 14 )  [ &nbsp;32] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=http://az.lib.ru/a/alekseew_g_w/><b>Алексеев Глеб</b></A>\n"+
            "<li><tt><small><b>www</b>( 14 )  [ &nbsp;34] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=http://az.lib.ru/a/andreew_l_n/><b>Андреев Леонид</b></A>\n"+
            "<li><tt><small><b>dir</b>( &nbsp;2 ) [ &nbsp; 5] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=ARHANGELSKY/><b>Архангельский Александр</b></A>\n"+
            "<li><tt><small><b>www</b>( 14 )  [ &nbsp;36] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=http://az.lib.ru/a/arcybashew_m_p/><b>Арцыбашев Михаил</b></A>\n"+
            "<br>\n"+
            "<li><tt><small><b>dir</b>( 19 ) [ &nbsp; 6] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=../PROZA/BABEL/><b>Бабель Исаак</b></A>\n"+
            "<li><tt><small><b>dir</b>( &nbsp;2 ) [ &nbsp; 2] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=../PXESY/BAHTEREW/><b>Бахтерев Игорь</b></A>\n"+
            "<li><tt><small><b>www</b>( 14 )  [ &nbsp;32] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=http://az.lib.ru/b/belyh_g_g/><b>Белых Григорий</b></A>\n"+
            "<li><tt><small><b>dir</b>( 66 ) [ &nbsp;10] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=../BULGAKOW/><b>Булгаков Михаил</b></A>\n"+
            "<li><tt><small><b>www</b>( 14 )  [ &nbsp;29] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=http://az.lib.ru/b/bunin_i_a/><b>Бунин Иван</b></A>\n"+
            "<li><tt><small><b>dir</b>( &nbsp;3 ) [ &nbsp; 5] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=BUHOW_A/><b>Бухов Аркадий</b></A>\n"+
            "<br>\n"+
            "Привет всем твоим!\n"+
            "<li><tt><small><b>www</b>( 14 )  [ &nbsp;32] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=http://az.lib.ru/w/waginow_k_k/><b>Вагинов Константин</b></A>\n"+
            "<li><tt><small><b>www</b>( 14 )  [ &nbsp;35] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=http://az.lib.ru/w/weresaew_w_w/><b>Вересаев Викентий</b></A>\n"+
            "<li><tt><small><b>dir</b>( &nbsp;2 ) [ &nbsp; 3] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=../WESELYJ/><b>Веселый Артем</b></A>\n"+
            "<li><tt><small><b>www</b>( 14 )  [ &nbsp;35] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=http://az.lib.ru/w/winogradow_a_k/><b>Виноградов Анатолий</b></A>\n"+
            "<li><tt><small><b>www</b>( 14 )  [ &nbsp;38] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=http://az.lib.ru/w/woloshin_m_a/><b>Волошин Максимилиан</b></A>\n"+
            "<br>\n"+
            "<li><tt><small><b>dir</b>( 22 ) [ &nbsp; 9] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=../GOLIKOW/><b>Гайдар Аркадий</b></A>\n"+
            "<li><tt><small><b>dir</b>( &nbsp;5 ) [ &nbsp; 3] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=GARIN/><b>Гарин-Михайловский Николай</b></A>\n"+
            "<li><tt><small><b>www</b>( 14 )  [ &nbsp;37] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=http://az.lib.ru/g/gashek_j/><b>Гашек Ярослав</b></A>\n"+
            "<li><tt><small><b>dir</b>( &nbsp;4 ) [ &nbsp; 8] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=GILQROWSKIJ/><b>Гиляровский Владимир</b></A>\n"+
            "<li><tt><small><b>www</b>( 14 )  [ &nbsp;28] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=http://az.lib.ru/g/gnedich_p_p/><b>Гнедич Петр</b></A>\n"+
            "<li><tt><small><b>www</b>( 14 )  [ &nbsp;26] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=http://az.lib.ru/g/gorxkij_m/><b>Горький Максим</b></A>\n"+
            "<li><tt><small><b>www</b>( 14 )  [ &nbsp;31] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=http://az.lib.ru/g/grigorxew_s_t/><b>Григорьев Сергей</b></A>\n"+
            "<li><tt><small><b>dir</b>(139 ) [ &nbsp;10] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=GRIN/><b>Грин Александр</b></A>\n"+
            "<li><tt><small><b>dir</b>( &nbsp;6 ) [ &nbsp; 2] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=GUL/><b>Гуль Роман</b></A>\n"+
            "<li><tt><small><b>dir</b>( 13 ) [ &nbsp; 6] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=../GUMILEW/><b>Гумилев Николай</b></A>\n"+
            "<br>\n"+
            "<li><tt><small><b>www</b>( 14 )  [ &nbsp;27] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=http://az.lib.ru/d/dobychin_l_i/><b>Добычин Леонид</b></A>\n"+
            "<li><tt><small><b>dir</b>( &nbsp;3 ) [ &nbsp; 2] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=DOROSHEWICH/><b>Дорошевич Влас</b></A>\n"+
            "<li><tt><small><b>www</b>( 14 )  [ &nbsp;36] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=http://az.lib.ru/d/dzhiwelegow_a_k/><b>Дживелегов Алексей</b></A>\n"+
            "<br>\n"+
            "<li><tt><small><b>dir</b>( 18 ) [ &nbsp; 3] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=ZHABOTINSKIJ/><b>Жаботинский Зеев</b></A>\n"+
            "<li><tt><small><b>www</b>( 14 )  [ &nbsp;29] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=http://az.lib.ru/z/zhitkow_b_s/><b>Житков Борис</b></A>\n"+
            "<br>\n"+
            "<li><tt><small><b>dir</b>( &nbsp;2 ) [ &nbsp; 6] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=ZAZUBRIN/><b>Зазубрин Владимир</b></A>\n"+
            "<li><tt><small><b>dir</b>( &nbsp;3 ) [ &nbsp; 7] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=ZAJCEW/><b>Зайцев Борис</b></A>\n"+
            "<li><tt><small><b>www</b>( 14 )  [ &nbsp;31] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=http://az.lib.ru/z/zamjatin_e_i/><b>Замятин Евгений</b></A>\n"+
            "<li><tt><small><b>dir</b>( 10 ) [ &nbsp; 5] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=ZOSHENKO/><b>Зощенко Михаил</b></A>\n"+
            "<br>\n"+
            "<li><tt><small><b>dir</b>( &nbsp;7 ) [ &nbsp; 2] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=../IWANOWWS/><b>Иванов Всеволод</b></A>\n"+
            "<li><tt><small><b>dir</b>( &nbsp;8 ) [ &nbsp; 4] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=IWANOWG/><b>Иванов Георгий</b></A>\n"+
            "<li><tt><small><b>www</b>( 14 )  [ &nbsp;28] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=http://az.lib.ru/w/wjacheslaw_i_i/><b>Иванов Вячеслав</b></A>\n"+
            "<li><tt><small><b>www</b>( 14 )  [ &nbsp;35] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=http://az.lib.ru/i/ignatxew_a_a/><b>Игнатьев Алексей Алексеевич</b></A>\n"+
            "<br>\n"+
            "<li><tt><small><b>dir</b>( &nbsp;4 ) [ &nbsp; 2] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=KIN_W/><b>Кин Виктор</b></A>\n"+
            "<li><tt><small><b>www</b>( 14 )  [ &nbsp;29] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=http://az.lib.ru/k/korolenko_w_g/><b>Короленко Владимир</b></A>\n"+
            "<li><tt><small><b>www</b>( 14 )  [ &nbsp;31] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=http://az.lib.ru/k/kr_k_k/><b>К.Р.(Константин Романов)</b></A>\n"+
            "<br>\n"+
            "<li><tt><small><b>dir</b>( &nbsp;2 ) [ &nbsp; 3] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=LAWRENEW/><b>Лавренев Борис</b></A>\n"+
            "<li><tt><small><b>dir</b>( &nbsp;1 ) [ &nbsp; 6] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=../LEONOWL/><b>Леонов Леонид</b></A>\n"+
            "<li><tt><small><b>dir</b>( &nbsp;1 ) [ &nbsp; 3] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=LUNC_L/><b>Лунц Лев</b></A>\n"+
            "<br>\n"+
            "<li><tt><small><b>dir</b>( &nbsp;4 ) [ &nbsp; 8] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=MARIENGOF/><b>Мариенгоф Анатолий</b></A>\n"+
            "<li><tt><small><b>www</b>( 14 )  [ &nbsp;37] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=http://az.lib.ru/m/majakowskij_w_w/><b>Маяковский Владимир</b></A>\n"+
            "<li><tt><small><b>dir</b>( &nbsp;3 ) [ &nbsp; 3] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=MEREZHKOWSKIJ/><b>Мережковский Дмитрий</b></A>\n"+
            "<li><tt><small><b>www</b>( 14 )  [ &nbsp;38] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=http://az.lib.ru/m/morozow_m_m/><b>Морозов Михаил Михайлович</b></A>\n"+
            "<br>\n"+
            "<li><tt><small><b>www</b>( 14 )  [ &nbsp;27] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=http://az.lib.ru/o/ognew_n/><b>Огнев Николай</b></A>\n"+
            "<li><tt><small><b>www</b>( 14 )  [ &nbsp;34] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=http://az.lib.ru/o/osorgin_m_a/><b>Осоргин Михаил</b></A>\n"+
            "<li><tt><small><b>dir</b>( &nbsp;2 ) [ &nbsp; 3] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=OSTROWSKIJ/><b>Островский Николай</b></A>\n"+
            "<br>\n"+
            "<li><tt><small><b>www</b>( 14 )  [ &nbsp;37] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=http://az.lib.ru/n/newerow_a_s/><b>Неверов Александр</b></A>\n"+
            "<li><tt><small><b>dir</b>( &nbsp;3 ) [ &nbsp; 5] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=NOWIKOW_I_A/><b>Новиков Иван</b></A>\n"+
            "<br>\n"+
            "<li><tt><small><b>dir</b>( 55 ) [ &nbsp; 3] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=PANTELEEW/><b>Пантелеев Алексей; Белых Григорий</b></A>\n"+
            "<br>\n"+
            "<li><tt><small><b>dir</b>( &nbsp;2 ) [ &nbsp; 6] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=../PROZA/REMIZOW/><b>Ремизов А.</b></A>\n"+
            "<li><tt><small><b>www</b>( 14 )  [ &nbsp;26] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=http://az.lib.ru/r/romanow_p_s/><b>Романов Пантелеймон</b></A>\n"+
            "<br>\n"+
            "<li><tt><small><b>www</b>( 14 )  [ &nbsp;35] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=http://az.lib.ru/s/sawinkow_b_w/><b>Савинков Борис</b></A>\n"+
            "<li><tt><small><b>dir</b>( &nbsp;2 ) [ &nbsp; 1] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=SWIRSKIJ_A/><b>Свирский Алексей</b></A>\n"+
            "<li><tt><small><b>www</b>( 14 )  [ &nbsp;36] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=http://az.lib.ru/s/semenow_s_a/><b>Семенов Сергей</b></A>\n"+
            "<li><tt><small><b>dir</b>( &nbsp;5 ) [ &nbsp; 8] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=SERAFIMOWICH/><b>Серафимович Александр</b></A>\n"+
            "<li><tt><small><b>dir</b>( 52 ) [ &nbsp; 6] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=CENSKIJ/><b>Сергеев-Ценский Сергей</b></A>\n"+
            "<li><tt><small><b>www</b>( 14 )  [ &nbsp;32] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=http://az.lib.ru/s/sipowskij_w_w/><b>Сиповский Василий</b></A>\n"+
            "<li><tt><small><b>dir</b>( &nbsp;2 ) [ &nbsp; 1] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=SKALDIN/><b>Скалдин Алексей</b></A>\n"+
            "<li><tt><small><b>www</b>( 14 )  [ &nbsp;35] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=http://az.lib.ru/s/skitalec/><b>Скиталец</b></A>\n"+
            "<li><tt><small><b>www</b>( 14 )  [ &nbsp;27] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=http://az.lib.ru/s/slezkin_j_l/><b>Слезкин Юрий</b></A>\n"+
            "<li><tt><small><b>dir</b>( 23 ) [ &nbsp; 4] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=SOBOLEW/><b>Соболев Леонид</b></A>\n"+
            "<li><tt><small><b>www</b>( 14 )  [ &nbsp;35] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=http://az.lib.ru/s/sologub_f/><b>Сологуб Федор</b></A>\n"+
            "<li><tt><small><b>dir</b>( 68 ) [ &nbsp; 6] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=STANYUKOWICH/><b>Станюкович Константин</b></A>\n"+
            "<br>\n"+
            "<li><tt><small><b>www</b>( 14 )  [ &nbsp;32] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=http://az.lib.ru/t/tanbogoraz_w_g/><b>Тан-Богораз Владимир</b></A>\n"+
            "<li><tt><small><b>www</b>( 14 )  [ &nbsp;36] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=http://az.lib.ru/t/tolstoj_a_n/><b>Толстой Алексей</b></A>\n"+
            "<li><tt><small><b>www</b>( 14 )  [ &nbsp;26] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=http://az.lib.ru/t/tynjanow_j_n/><b>Тынянов Юрий</b></A>\n"+
            "<li><tt><small><b>dir</b>( &nbsp;6 ) [ &nbsp; 8] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=TEFFI/><b>Тэффи</b></A>\n"+
            "<br>\n"+
            "<li><tt><small><b>dir</b>( &nbsp;8 ) [ &nbsp; 2] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=FADEEW/><b>Фадеев Александр</b></A>\n"+
            "<li><tt><small><b>dir</b>( &nbsp;2 ) [ &nbsp; 2] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=FEDOROWA_N/><b>Федорова Нина</b></A>\n"+
            "<li><tt><small><b>dir</b>( &nbsp;1 ) [ &nbsp; 3] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=FURMANOW/><b>Фурманов Дмитрий</b></A>\n"+
            "<br>\n"+
            "<li><tt><small><b>dir</b>( 12 ) [ &nbsp; 6] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=../HARMS/><b>Хармс Даниил</b></A>\n"+
            "<li><tt><small><b>www</b>( 14 )  [ &nbsp;29] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=http://az.lib.ru/h/hodasewich_w_f/><b>Ходасевич Владислав</b></A>\n"+
            "<br>\n"+
            "<li><tt><small><b>www</b>( 14 )  [ &nbsp;30] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=http://az.lib.ru/c/charskaja_l_a/><b>Чарская Лидия Алексеевна</b></A>\n"+
            "<li><tt><small><b>www</b>( 14 )  [ &nbsp;30] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=http://az.lib.ru/c/chajanow_a_w/><b>Чаянов Александр</b></A>\n"+
            "<li><tt><small><b>www</b>( 14 )  [ &nbsp;38] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=http://az.lib.ru/c/chernow_w_m/><b>Чернов Виктор Михайлович</b></A>\n"+
            "<li><tt><small><b>www</b>( 14 )  [ &nbsp;37] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=http://az.lib.ru/c/chernyj_s/><b>Черный Саша</b></A>\n"+
            "<li><tt><small><b>www</b>( 14 )  [ &nbsp;35] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=http://az.lib.ru/c/chulkow_g_i/><b>Чулков Георгий</b></A>\n"+
            "<br>\n"+
            "<li><tt><small><b>dir</b>( &nbsp;2 ) [ &nbsp; 6] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=../SHAGINQN/><b>Шагинян Мариэтта</b></A>\n"+
            "<li><tt><small><b>dir</b>( &nbsp;3 ) [ &nbsp; 1] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=SHILIN_G/><b>Шилин Георгий</b></A>\n"+
            "<li><tt><small><b>dir</b>( &nbsp;5 ) [ &nbsp; 4] &nbsp; &nbsp; &nbsp; </small></tt> <A HREF=SMELEW/><b>Шмелев Иван</b></A>\n"+
            "<br>\n"+
            "<dir><dir><a name=2></a><h2>См. также</h2></dir></dir>\n"+
            "<li><tt><small><b>www</b> </small></tt> <A HREF=http://www.russianresources.lt/archive/><b>Балтийский архив</b></A>\n"+
            "<br>\n"+
            "</pre><center><hr noshade><TABLE cellpadding=0 cellspacing=0 border=0 WIDTH=99% align=center><TR><td align=left><b>l8<i></i>+</b>&nbsp; <small>Свидетельство о регистрации СМИ Эл No ФС 77-20625 от 12.05.2005</small></td><td valign=top align=right><i><small>При поддержке Федерального агентства по печати и массовым коммуникациям.</small></i></td></tr></table><TABLE cellpadding=0 cellspacing=0 border=0 WIDTH=99% align=center><TR><td align=left><b>Home: </b><a href=http://lib.ru/~moshkow/><small><i>http://lib.ru/~moshkow/</i></small></a>  <td align=center><b>Маil: </b><u><small><i>max&#64lib&#46ru</i></small></u><td align=right><b>Hosted by <u>RTComm</u></b></tr><tr></tr></table><hr noshade></center>\n"+
            "<table cellpadding=0 cellspacing=0 border=0 bgcolor=#1111aa align=center><tr><td bgcolor=#aaffee><noindex><center><font size=-1 color=#555555>\n"+
            "<b><a href=http://pressa-voiny.ru/><font color=brown>Проект \"Пресса Войны\" просит помощи</font></a></b><br>Статьи, фотографии, <a href=http://pressa-voiny.ru/> Совинформбюро за 1941-1945</а>\n"+
            "<p>\n"+
            "<!-- a href=http://artofwar.ru/>ArtofWar.ru</a> - Мемуары чеченской войны\n"+
            "<p-->\n"+
            "<a href=http://www.artlib.ru>Художники</a> - картинные галереи</a>\n"+
            "</font></center></noindex>\n"+
            "</td></td><td bgcolor=#eeeeee valign=top align=center><br clear=all></td></tr></table>Популярность: <b>18</b></body></html>";}


}

