package c.ponom.pocketlibrary.View;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import java.util.Objects;

import c.ponom.pocketlibrary.DI.App;
import c.ponom.pocketlibrary.DI.DIclass;
import c.ponom.pocketlibrary.Database.NetworkLoaders.NetworkLoaders;
import c.ponom.pocketlibrary.Database.Repository;
import c.ponom.pocketlibrary.Database.RoomEntities.Author;
import c.ponom.pocketlibrary.Database.RoomEntities.Book;
import c.ponom.pocketlibrary.Database.RoomEntities.SubChapter;
import c.ponom.pocketlibrary.R;
import c.ponom.pocketlibrary.View.Fragments.AuthorsListFragment;
import c.ponom.pocketlibrary.View.Fragments.BooksListFragment;
import c.ponom.pocketlibrary.View.Fragments.LoadedListFragment;
import c.ponom.pocketlibrary.View.Fragments.SubChaptersFragment;
import c.ponom.pocketlibrary.View.WebView.WebViewFragment;

public class SingleActivity extends AppCompatActivity {


    //String YandexAPI = "AOpkMF0BAAAAklyiHQQD5AL6FpeRxKCBZlBhqklDbcA2Up8AAAAAAAAAAABupmQxoJIt_0yufDgylSz2Ishx5A==";
    Repository repository;
    Toolbar toolbar=null;
  //  private FirebaseAnalytics mFirebaseAnalytics;
    ProgressDialog pd;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getApplicationComponent().getActivitiesModule().removeSingleActivity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getApplicationComponent().getActivitiesModule().injectSingleActivity(this);
        pd = new ProgressDialog(this);
        setContentView(R.layout.start_screen);
    //  mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        repository= DIclass.getRepository();
        toolbar =findViewById(R.id.main_toolbar);
        toolbar.inflateMenu(R.menu.menus);
        //int a =2/0; //test error
        toolbar.setOnMenuItemClickListener(
                new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return onOptionsItemSelected(item);
                    }
                });

         setSupportActionBar(toolbar);
         toolbar.setTitle("");

        if (savedInstanceState==null) {
            // todo - вообще это потенциально глючно, проверить что будет если запустить
            //  пустое приложение без сети, потом восстановить его при включенной сети - вроде тогда загрузка
            //  не вызовется.  Надо другой метод придумать
            NetworkLoaders.loadChapterList();
            showMainFragment();
        }

    }



    @Override
    protected void onResume() {
        super.onResume();
        toolbar.setTitle("");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menus, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case android.R.id.home: {
                super.onBackPressed();
                break;
            }
            case R.id.action_about_item: {
                // todo - вызов обработчика будет тут
                break;
            }

            case R.id.action_loaded_book_list: {
                showLoadedListFragment();
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void showProgressDialog (){
        pd.setTitle(this.getString(R.string.pleasewait));
        pd.setMessage("");
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setIndeterminate(true);
        pd.show();
    }
    public void hideProgressDialog(){
        pd.dismiss();
    }

    public void setNewTitle(String title, String subtitle){
        if (toolbar == null) {
            return;
        }
        toolbar.setTitle(title);
        toolbar.setSubtitle(subtitle);

    }

    public void setBackButtonVisibility(boolean status) {


        Objects.requireNonNull(getSupportActionBar()).
                setDisplayHomeAsUpEnabled(status);
        getSupportActionBar().setHomeButtonEnabled(status);
    }

    public void showAuthorsListForSubChapter(SubChapter subChapter) {


        Fragment authorsFragment = AuthorsListFragment.newInstance(subChapter,this);
        getSupportFragmentManager().beginTransaction().replace(R.id.mainViewPager,authorsFragment)
                .addToBackStack(null)
                .commit();

    }


    public void showBooksForSubChapterAuthor(Author author) {


        Fragment bookFragment = BooksListFragment.newInstance(author,this);

        getSupportFragmentManager().beginTransaction().replace(R.id.mainViewPager,bookFragment)
                .addToBackStack(null)
                .commit();

    }


    public  void  launchWebView(String filePath, Book book){

        Fragment bookFragment = WebViewFragment.newInstance(book,filePath,this);

        getSupportFragmentManager().beginTransaction().replace(R.id.mainViewPager,bookFragment)
                .addToBackStack(null)
                .commit();

    }


    public void showMainFragment() {
        final Fragment fragment = SubChaptersFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.mainViewPager,fragment)
                .commit();
    }

    public void showLoadedListFragment() {
        final Fragment fragment = LoadedListFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.mainViewPager,fragment)
                .addToBackStack(null)
                .commit();
    }




    /* глобальные задачи
    todo рисование нормальных списков по Материал.
    реализовано вызов webView переделать на загрузку файла в дата каталог и открытие Вебвью для него
    отменено  Добавить кнопку загрузки всех произведений для уровня "авторы"
    todo добавить чекбокс или иную выборку для удаления  загруженного


    отлоожено - такие разделы просто пока будут игнорироваться
     переделка структуры базы данных на последнем уровне - под возможность работы с html и ссылками на az и другое плюс
    сделано  список исключаемых подразделов первого уровня
    исключено - там все одно другие размеры чем на диске победить в регулярке размеры файла, построить пересчет их вверх по иерарархи
    реализовано  определиться с отображением текстового файла - возможно стоит перейти на html (если оно поддерживается вообще)
    реализовано  подготовить загрузку текстовых файлов (блоб? или все таки толпа файлов?)
    todo сделать уникальный индекс для базы книг (раздел, автор, название) и вообще пересмотреть индексы
    реализовано  перетасовать все по подразделам (модели, адаптеры, фрагменты, активности)
    todo переделать вызов базовой активности из фрагментов - на прямое общение фрагментов или на интерфейсы?
    сделано  объединить загрузку и вызов парсеров в одном модуле/пакете
    сделано без даггера  реализовать использование dagger - с хранением параметров и общих данных
    (контеста или хотя бы ссылок на репозиториии, базу)
    todo подготовить фрагмент с информацией о программе, лицензиями
    реализовано  убрать из интерфейсов DAO, базы данных  все лишнее
    todo переписать асинкТаски на что-то посовременннее. В учебных целях. Вероятно экзекьюторы все же.
    реализовано  реализовать массовую вставку / замену /удаление  записей, путем передачи массива через асинктаски и ДАО
    реализовано  переделать обычные адаптеры на list - и реализовать обновление на экране только реально измененных записей
    todo реализовать обработку отсутствия интернета (не пытаться в этом случае получить данные)
    todo обеспечить минимальный набор проверок при парсинге
    реализовано  обеспечить вывод сообщений об ошибках связи
    todo минимальная обработка полей WebView. Обеспечить невозможность выхода за пределы домена
    сделано, убрано - посмотреть почему местами не убирается <b>
    сделано кроме удаления   - реализовать экран-список уже загруженных файлов, просмотр их и исключение с удалением.
    сделано  после того как зафиксировано развитие репозитория - написать для него юнит-тесты, или хотя бы андроид-тесты
    отменено - излишнее - для парсеров - написать юнит тест, грузящий рекрурсивно всю либу.
    отменено  - добавить транзакции для массовых записей - типа такого
    причина - целостность базы интересует мало, поскольку она постояно подзагружается, а единственная массовая операция у нас - удаление



     */



}



    /*
    Задачи - освоение и использование следующего стека технологий:
    dagger 2; - заменено на самописный DI
    room; - вроде разобрался. и с концепцией репозитария тоже
    rx - рановато, и тема проекта не оптимальная
    MVVM+lifedata - в одну сторону разобрался, осталось сделать автоматически меняющиеся данные (в обе стороны?)
    поле и вызов из Вью
    retrofit - заменено пока на volley.
    Впрочем, можно попробовать последний уровень им разобрать

     */



