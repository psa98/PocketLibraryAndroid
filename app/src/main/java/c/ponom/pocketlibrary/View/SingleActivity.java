package c.ponom.pocketlibrary.View;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Objects;


import c.ponom.pocketlibrary.Dagger._DaggerTestClass;
import c.ponom.pocketlibrary.Database.DaoDatabase;
import c.ponom.pocketlibrary.Database.NetworkLoaders.HTMLCustomParsers;
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

    String YandexAPI = "AOpkMF0BAAAAklyiHQQD5AL6FpeRxKCBZlBhqklDbcA2Up8AAAAAAAAAAABupmQxoJIt_0yufDgylSz2Ishx5A==";
    public static DaoDatabase database;
    public static Repository repository;
    Toolbar toolbar=null;
  //  private FirebaseAnalytics mFirebaseAnalytics;
    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //_DaggerTestClass daggerTestClass=new _DaggerTestClass();
        //daggerTestClass.run();
        pd = new ProgressDialog(this);
        setContentView(R.layout.start_screen);
    //  mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        database = DaoDatabase.getDatabase(this);
        repository=Repository.getRepository(this.getApplication());
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
            loadChapterList(this);
            showMainFragment();
        }

    }

    public void showMainFragment() {
        final Fragment fragment = SubChaptersFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.mainViewPager,fragment)
              //  .addToBackStack(null)
                .commit();
    }

    public void showLoadedListFragment() {
        final Fragment fragment = LoadedListFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.mainViewPager,fragment)
                 .addToBackStack(null)
                .commit();
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
                // todo - вызов обработчика
                break;
            }

            case R.id.action_loaded_book_list: {
                showLoadedListFragment();
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setNewTitle(String title, String subtitle){
        if (toolbar == null) {
            return;
        }
        toolbar.setSubtitle(subtitle);
        toolbar.setTitle(title);
    }


     public static void loadChapterList(final Context context){
        RequestQueue queue = Volley.newRequestQueue(context);
        //final TextView textView = findViewById(R.id.errorText);

       StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://lib.ru/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        HTMLCustomParsers.parseMainPage(response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
        queue.add(stringRequest);
    }



    public static void loadAuthorsList(final SubChapter subChapter, final Context context){
        //todo - определиться что делать c контекстом и вообще этими методами.
        // И еще  с проверкой на наличие как связи на момент вызова, так и скачанного и распарсенного ранее
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, subChapter.url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        ArrayList<Author> parsedSubChapter= HTMLCustomParsers.parseSubChapter(response,subChapter);
                        //в принципе этот список не особо нужен.
                        //Хотя в принципе можно в статусной строке вывести число позиций тут и аналогично в других уровнях

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
        queue.add(stringRequest);
    }



    public static void loadBooksList (final Author author, final Context context){
        //todo - определиться что делать c контекстом и вообще этими методами.
        // И еще  с проверкой на наличие как связи на момент вызова, так и скачанного и распарсенного ранее
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, author.url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        HTMLCustomParsers.parseAuthorInSubChapter(response, author,author.url);
                        //в принципе этот список не особо нужен.
                        //Хотя в принципе можно в статусной строке вывести число позиций тут и аналогично в других уровнях

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
        queue.add(stringRequest);
    }




    public void showAuthorsListForSubChapter(SubChapter subChapter) {


        Fragment authorsFragment = AuthorsListFragment.newInstance(subChapter,this);
        getSupportFragmentManager().beginTransaction().replace(R.id.mainViewPager,authorsFragment)
                .addToBackStack(null)
                .commit();

    }


    public void setBackVisibility(boolean status) {


        Objects.requireNonNull(getSupportActionBar()).
                setDisplayHomeAsUpEnabled(status);
        getSupportActionBar().setHomeButtonEnabled(status);
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

    public void showProgressDialog (){
        pd.setTitle("Please wait");
        pd.setMessage("");
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setIndeterminate(true);
        pd.show();
    }
    public void hideProgressDialog(){
        pd.dismiss();
    }


    /* глобальные задачи
    todo рисование нормальных списков по Материал.
    реализовано вызов webView переделать на загрузку файла в дата каталог и открытие Вебвью для него
    отменено  Добавить кнопку загрузки всех произведений для уровня "авторы"
    todo добавить поле "загружено" в базу книг, кнопку/иконку  "загрузить" в списке, чекбокс или иную выборку для удаления
         загруженного


    отлоожено - такие разделы просто пока будут игнорироваться
     переделка структуры базы данных на последнем уровне - под возможность работы с html и ссылками на az и другое плюс
    todo список исключаемых подразделов первого уровня
    исключено - там все одно другие размеры чем на диске победить в регулярке размеры файла, построить пересчет их вверх по иерарархи
    реализовано  определиться с отображением текстового файла - возможно стоит перейти на html (если оно поддерживается вообще)
    реализовано  подготовить загрузку текстовых файлов (блоб? или все таки толпа файлов?)
    todo сделать уникальный индекс для базы книг (раздел, автор, название) и вообще пересмотреть индексы
    реализовано  перетасовать все по подразделам (модели, адаптеры, фрагменты, активности)
    todo переделать вызов базовой активности из фрагментов - на прямое общение фрагментов или на интерфейсы?
    todo объединить загрузку и вызов парсеров в одном модуле
    todo реализовать использование dagger - с хранением параметров и общих данных (контеста или хотя бы ссылок на репозиториии, базу)
    todo подготовить фрагмент с информацией о программе, лицензиями
    реализовано  убрать из интерфейсов DAO, базы данных  все лишнее
    todo переписать асинкТаски на что-то посовременннее. В учебных целях. Вероятно экзекьюторы все же.
    реализовано  реализовать массовую вставку / замену /удаление  записей, путем передачи массива через асинктаски и ДАО
    реализовано  переделать обычные адаптеры на list - и реализовать обновление на экране только реально измененных записей
    todo реализовать обработку отсутствия интернета (не пытаться в этом случае получить данные)
    todo обеспечить минимальный набор проверок при парсинге
    реализовано  обеспечить вывод сообщений об ошибках связи
    todo минимальная обработка полей WebView. Обеспечить невозможность выхода за пределы домена
    todo - посмотреть почему местами не убирается <b>
    сделано кроме удаления   - реализовать экран-список уже загруженных файлов, просмотр их и исключение с удалением.
    todo после того как зафиксировано развитие репозитория - написать для него юнит-тесты, или хотя бы андроид-тесты
    отменено - излишнее - для парсеров - написать юнит тест, грузящий рекрурсивно всю либу.
    todo - вариант использовать custom



    отменено  - добавить транзакции для массовых записей - типа такого
    причина - целостность базы интересует мало, поскольку она постояно подзагружается, а единственная массовая операция у нас - удаление

    @Dao
abstract class UserDao {

    @Transaction
    open fun updateData(users: List<User>) {
        deleteAllUsers()
        insertAll(users)
    }
    @Insert
    abstract fun insertAll(users: List<User>)
    @Query("DELETE FROM Users")
    abstract fun deleteAllUsers()
}





     */



}



    /*
    Задачи - освоение и использование следующего стека технологий:
    dagger 2; - в финальном варианте будет.
    room; - вроде разобрался. и с концепцией репозитария тоже
    rx - рановато, и тема проекта не оптимальная
    MVVM+lifedata - в одну сторону разобрался, осталось сделать автоматически меняющиеся данные (в обе стороны?) поле и вызов из Вью
    retrofit - заменено пока на volley.
    Впрочем, можно попробовать последний уровень им разобрать

     */



