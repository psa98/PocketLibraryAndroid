package c.ponom.pocketlibrary.data.NetworkLoaders;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import c.ponom.pocketlibrary.DI.DIclass;
import c.ponom.pocketlibrary.data.Repository;
import c.ponom.pocketlibrary.data.RoomEntities.Author;
import c.ponom.pocketlibrary.data.RoomEntities.Book;
import c.ponom.pocketlibrary.data.RoomEntities.SubChapter;

public class HTMLCustomParsers {





    public static ArrayList<SubChapter> parseMainPage(String documentString){


        Repository repository = DIclass.getRepository();
        Document doc= Jsoup.parse(documentString);
        Elements links = doc.select(	"a[href~=^\\w[A-Z]+\\/{1}$");

        // регулярное выражение выше дает ок. выборку, с которой можно работать дальше


        String s = links.outerHtml();
        //s содержит примерно следубщее <a href ="XXX/"> <b>Название</b></a>


        Pattern removeInStart = Pattern.
                compile("(?:<a href=\"|<\\/?b>|<\\/a>|>|<br>|<\\/?i>|\")",Pattern.MULTILINE);
        Matcher matcher = removeInStart.matcher(s);
        // вычищаем все применяемые сейчас теги

        //"(?:<a href=\"|<\\/?b>|<\\/a>|>|\")"
        //"(?:<a href=\"|<\\/?b>|<\\/a>|>|<br>||<\\/?i>|\")"
        String result = matcher.replaceAll("");

        /* Получаем примерно такое:
        AWARDS/"Награды
        PROZA/"РУССКАЯ СОВРЕМЕННАЯ ПРОЗА
        POEZIQ/"РУССКАЯ И ЗАРУБЕЖНАЯ ПОЭЗИЯ
        INPROZ/"ЗАРУБЕЖНАЯ ПРОЗА
        что разбирается сначала по \n по строкам, а затем каждое на два значения по кавычке как разделителю
         */
        ArrayList<SubChapter> listResult = new ArrayList<>();
        String[] subChapters = result.split("\n");
        for (String subChapter:subChapters)
        { String[] record = subChapter.split("/");
            SubChapter listRecord = new SubChapter();
            listRecord.url="http://lib.ru/"+record[0]+"/";
            listRecord.subChapterName=record[1];
            if (!excludeSubChapters(listRecord.subChapterName)){
            listResult.add(listRecord);
            repository.insertRecord(listRecord);}

            /// todo - тут будут все проверки на то что не съехало ничего/
            // потом убрать возвращаемые списки - они не нужны
            }
        return listResult;
    }



    private static boolean excludeSubChapters(String testString){

        String[] excludedSubChapters={"Награды",
                "Кинофильм", "Авторская", "Парашю","английский","Зарубежная", "БЛИЖНЕГО","Russian",
                "РОССЫПЬЮ","Клуб ", "Законы", "АСТРОЛОГИЯ", "Диалект", "Нейро","иблио", "Бухучет",
                "VMw","Unix","Web","fire","Технические", "ПРОГРАММ",  "Разноо", "О правах"
        };


        for (String excludedSubChapter:excludedSubChapters)
            if (testString.contains(excludedSubChapter)) return true;
        return false;
    }




    public static ArrayList<Author> parseSubChapter(String documentString,SubChapter currentSubChapter){


        Repository repository = DIclass.getRepository();
        // todo - заменить это везде на даггер
        Document doc= Jsoup.parse(documentString);
        Elements links = doc.select(	"a[href~=^\\w[A-Z]+\\/{1}$");
        Elements links1 = doc.select(	"[href$=txt]");

        //links - обычные ссылки, на страницу автора. links1 - на страницу произведения, но оказавшиеся в авторах.

        // регулярное выражение выше дает ок. выборку, с которой можно работать дальше


        String s = links.outerHtml();
        String s1 = links1.outerHtml();

        // содержит примерно следубщее <a href ="XXX/"> <b>Название</b></a>

        Pattern removeInStart = Pattern.compile("(?:<a href=\"|<\\/?b>|<\\/a>|>|<br>|<\\/?i>|\")",Pattern.MULTILINE);
        Pattern removeInStart1 = Pattern.compile("(?:<a href=\"|<\\/?b>|<\\/a>|>)",Pattern.MULTILINE);

        Matcher matcher = removeInStart.matcher(s);
        // вычищаем все применяемые сейчас теги
        String result = matcher.replaceAll("");

        matcher = removeInStart1.matcher(s1);
        // вычищаем все применяемые сейчас теги
        String result1 = matcher.replaceAll("");




        String url=currentSubChapter.url;

          /*
        todo Некоторые разделы содержат не список авторов, а сразу ссылки на произведения
        Такие следует заносить отдельно, отличаются они тем, что ссылка на пункт заканчивается на .txt
        Адаптер по работе с авторами разберется с ними сам. НО!
        Пример - авторская песня, но:
        Иногда такая ссылка не имеет ничего - то есть просто одно произведение, идентифицируемое по автору и названия, а иногда
        идет пакет произведений одного автора или на одну тему
        в одном разделе см. раздел Альпинизм lib.ru . В таком случае надо понять что это подраздел
        и идентифицировать автора выше, после чего до конца раздела (который тоже не очнь понятно как разобрать) лепить автора
        в начало названия. Если будет сложно - оставим эту идею как есть, не выгружая "листья" второго уровя


        Отдельно надо разобрать:
        Религиозная литература  -
            Христианство
(  0k)       Восточная религиозная философия

dir( 26 ) [  58]       Атеистическая публицистика
        - ссылки пропали, вероятно по причине "внешнести" - у них 2 точки в начале, для этого раздела их надо добавить в разбор





         */


        ArrayList<Author> listResult = new ArrayList<>();
        if (result.isEmpty()) return listResult;
        String[] authors = result.split("\n");
        for (String currentAuthor:authors)
        { String[] record = currentAuthor.split("/");

        //todo - для result1 - возможны следующие варианты:
            /*

            ../PROZA/SOLOUHIN/adygene.txtВладимир Солоухин. Прекрасная Адыгене
            beduj.txtГорячий Ключ-Бедуй. Дневник. С картинками

то есть, в отличие от result - (1)  / не может восприниматься как нормальный разделитель, он не конечен как в обычном
(2) строка разбивается не /, а по границе между txt и следующей буквой.
То есть, надо бы подобрать для разбивки s1 отд.регексп




             */

            Author listRecord = new Author();
            listRecord.url=url +record[0]+"/";// (record[0].contains(".txt")? "": "/");
            listRecord.authorName=record[1];
            listRecord.subChapterName=currentSubChapter.subChapterName;
            listResult.add(listRecord);
            repository.insertRecord(listRecord);
         /// todo - тут будут все проверки на то что не съехало ничего
            if (!currentSubChapter.isLoaded){
                currentSubChapter.isLoaded=true;
                repository.updateRecord(currentSubChapter);
            }

        }

        //repository.insertRecord((SubChapter[])listResult.toArray());

 //       String[] authors1 = result1.split("\n");


        /* for (String currentAuthor:authors1)
        { String[] record = currentAuthor.split("\"");




            ../PROZA/SOLOUHIN/adygene.txtВладимир Солоухин. Прекрасная Адыгене
            beduj.txtГорячий Ключ-Бедуй. Дневник. С картинками

то есть, в отличие от result - (1)  / не может восприниматься как нормальный разделитель, он не конечен как в обычном
(2) строка разбивается не /, а по границе между txt и следующей буквой.
То есть, надо бы подобрать для разбивки s1 отд.регексп





            //todo - эта штука долна ломаться на кавычках в тексте. Проверить. Поменять нужную кавычку на более экзотический символ
            Author listRecord = new Author();
            listRecord.url=url +record[0];
            Log.e("!!", "parseSubChapter: "+listRecord.url );
            listRecord.authorName=record[1];
            listRecord.subChapterName=currentSubChapter.subChapterName;
            listResult.add(listRecord);
            repository.insertRecord(listRecord);
            /// todo - тут будут все проверки на то что не съехало ничего
        }

  */



        return listResult;
    }

    static ArrayList<Book> parseAuthorInSubChapter(String documentString,
                                                   Author author, String url){
        /*текущая подзадача следующая:
        на входе получаем как сейчас документ, то есть вызываем это уже асинхронно из метода загрузки что
        Возвращаем List of Books, правильно заполненный в плане имен каталогов, либо пустой список/null
        если на входе ерунда, или ошибка разбора.
         */


        Repository repository = DIclass.getRepository();

        Document doc= Jsoup.parse(documentString);
        Element body =doc.body();

         //todo - проблема? размеры в отдельных элементах от ссылок, вероятно их придется разбирать
        // отдельно и сращивать, или вообще использовать другой метод
        //String s = links.outerHtml();
        String bodyString =body.toString();


        /*
        Далее нам надо:
        1. Преобразовать Body через toString
        2. разобрать строку в массив (список) по переводам строки
        3. Удалить элементы, не содержащие href, либо содержащиее ссылки на другие страницы
        4. из оставшихся в цикле выбрать название файла, название произведения и размер (123k), игнорируя оглавления

        */
        ArrayList<String> splittedStrings;
        String subChapterName=author.subChapterName;
        String authorName=author.authorName;

        splittedStrings = new ArrayList<>(Arrays.asList(bodyString.split("\n")));

        StringBuilder cleanedString=new StringBuilder();
        for (String string:splittedStrings){
            if (!string.contains("a href=\"")) continue;
            if (string.contains("a href=\"http")) continue;
            if (string.contains("a href=\"/")) continue;
            if (string.contains("href=\"What-s-new")) continue;
            if (string.contains("href=\"Mirrors")) continue;
            cleanedString.append(string).append("\n");
        }

        if (cleanedString.length()<=3) return null;

        ArrayList<Book> listResult = new ArrayList<>();

        splittedStrings = new ArrayList<>(Arrays.asList(cleanedString.toString().split("\n")));

        Pattern findSizeInKb = Pattern.compile("(\\(.\\d*\\dk\\))");
        Pattern contentsToRemove = Pattern.compile("(.*?<a href=\"|<\\/b>|><b>|<br>|<\\/a>|<\\/li>)");


        for (String record:splittedStrings) {
        Book recordToAdd = new Book();

        Matcher matcher = findSizeInKb.matcher(record);
        String result=null;
        if (matcher.matches())
        result= matcher.
                group(1);
        // todo разобраться с размером - не распарсивается пока

        if (result!=null) {
        //надо убрать первую скобку и последние два символа
        String s= result.replace("k)","");
        s= result.replace("(","");
        int size=0;
        try {
        size=Integer.valueOf(s);}
        catch (NumberFormatException e)
        {
        e.printStackTrace();
            }
        recordToAdd.sizeInKb=size;
        }
        // осталось найти паттерн <a href="stout47.txt">, но не  <li><tt><small><a href="stout46.txt_Contents">
        Matcher remover = contentsToRemove.matcher(record);
        String restOfString = remover.replaceAll("");
        // примерно такой формат выйдет: ааа.txt"Название
        String[] string =restOfString.split("\"");
        recordToAdd.url=url+string[0];
        recordToAdd.subChapterName=subChapterName;
        recordToAdd.authorName=authorName;
        recordToAdd.bookName=string[1].trim();
        repository.insertRecord(recordToAdd);
        listResult.add(recordToAdd);

        /// todo - тут будут все проверки на то что не съехало ничего
        }
        if (!author.isLoaded){
        author.isLoaded=true;
        repository.updateRecord(author);
        }
        return listResult;
    }

}
