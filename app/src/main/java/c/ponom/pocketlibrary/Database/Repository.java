package c.ponom.pocketlibrary.Database;

import android.app.Application;
import androidx.lifecycle.LiveData;
import android.os.AsyncTask;
import android.os.Bundle;

import java.util.HashMap;
import java.util.List;


import javax.inject.Inject;
import javax.inject.Singleton;

import c.ponom.pocketlibrary.Database.DAO.AuthorDAO;
import c.ponom.pocketlibrary.Database.DAO.BookDAO;
import c.ponom.pocketlibrary.Database.DAO.SubChapterDAO;
import c.ponom.pocketlibrary.Database.RoomEntities.Author;
import c.ponom.pocketlibrary.Database.RoomEntities.BaseEntity;
import c.ponom.pocketlibrary.Database.RoomEntities.Book;
import c.ponom.pocketlibrary.Database.RoomEntities.SubChapter;
import c.ponom.pocketlibrary.View.SingleActivity;
import dagger.Provides;



public class Repository {

    private static Repository INSTANCE;



    /**
     * Abstracted Repository as promoted by the Architecture Guide.
     * https://developer.android.com/topic/libraries/architecture/guide.html
     */

        private static AuthorDAO mAuthorDAO;
        private static BookDAO mBookDAO;
        private static SubChapterDAO mSubChapterDAO;
        // класс реализуется как синглтон, так что мы можем позволить себе использовать статические ссылки
        // они нужны для работы с AsyncTask.
        // todo переписать с асинков на экзекьюторы?
        private static HashMap<String, Bundle> webViewState = new HashMap<>();

    SingleActivity singleActivity;




    public  static Repository getRepository(final Application application) {
        if (INSTANCE == null) {
            synchronized (Repository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Repository(application);
                }
            }
        }
        return INSTANCE;
    }




    public static Repository getINSTANCE() {
        return INSTANCE;
    }

    private Repository(Application application) {
            DaoDatabase db = DaoDatabase.getDatabase(application);
            mAuthorDAO = db.getAuthorDAO();
            mBookDAO=db.getBooksDAO();
            mSubChapterDAO=db.getSubChapterDAO();

        }



        public LiveData<List<SubChapter>> getAllSubChapterLiveList()
        {return mSubChapterDAO.getAllSubChaptersLiveData();}


        public LiveData<List<Book>> getBooksForAuthorAndSubChapter(Author author)
        {   SingleActivity singleActivity;

            return mBookDAO.getBooksByAuthorAndChapterLiveData(author.authorName,author.subChapterName);}


        public LiveData<List<Author>> getAuthorsForSubChapter(SubChapter subChapter)
        {return mAuthorDAO.getListOfAuthorsForSubchapters(subChapter.subChapterName);}

        public LiveData<List<Book>> getListOfLoadedBooks()
        {return mBookDAO.getLoadedBooksList();}

        public LiveData<Integer> getTotalLoadedKb()
        {return mBookDAO.getLoadedBooksTotalKB();}

        public int getTotalLoadedKbForTest()
        {return mBookDAO.getTotalLoadedKbForTest();}


    public void insertRecord(BaseEntity record) {
        new insertAsyncTaskAll().execute(record);

    }


    public void updateRecord(BaseEntity record) {
        new updateAsyncTaskAll().execute(record);

    }



    public void clearAuthors() {
        new clearTableAsyncTask().execute(Author.class);
    }

    public void clearSubChapters() {
        new clearTableAsyncTask().execute(SubChapter.class);
    }

    public void clearBooks() {
        new clearTableAsyncTask().execute(Book.class);
    }

    public void saveWebState(String name,Bundle outState) {
        // в данном случае мы храним привязанные к каждой книге текущего сеанса пары <Название Книги, состояние > в ХэшСет;
        // метод статичный, так что проверки не нужны
        // todo - можно сохранять этот набор в базе или Gson каждый раз и подгружать при запуске
        webViewState.put(name,outState);

    }

    public Bundle loadWebState(String name) {

        return webViewState.get(name);

    }



    private static  class clearTableAsyncTask extends AsyncTask<Class, Void, Void> {

        @Override
        protected Void doInBackground(final Class... classes) {
            if (classes[0] == Author.class) {
                mAuthorDAO.clearTableOfAuthors();
                return null;
            }
            if (classes[0]  == SubChapter.class) {
                mSubChapterDAO.clearTableSubChapters();
                return null;
            }
            if (classes[0]  == Book.class) {
                mBookDAO.clearBooks();
                return null;
            }
            return null;
        }


    }







    private static  class insertAsyncTaskAll extends AsyncTask<BaseEntity, Void, Void> {
        // todo - оставить возможность передать в задачу массив сразу
        //  такая замена должна сработать -> Author[]) params, надо только протестировать

            @Override
            protected Void doInBackground(final BaseEntity... params) {
            if (params[0].getClass() == Author.class) {
                mAuthorDAO.insert((Author) params[0]);
                return null;
            }
            if (params[0].getClass() == SubChapter.class) {
                mSubChapterDAO.insert((SubChapter) params[0]);
                return null;
            }
            if (params[0].getClass() == Book.class) {
                mBookDAO.insert((Book) params[0]);
                return null;
            }
                return null;
        }



    }



    private static  class updateAsyncTaskAll extends AsyncTask<BaseEntity, Void, Void> {


        @Override
        protected Void doInBackground(final BaseEntity... params) {

            if (params[0].getClass() == Author.class) {
                mAuthorDAO.update((Author) params[0]);
                return null;
            }
            if (params[0].getClass() == SubChapter.class) {
                mSubChapterDAO.update((SubChapter) params[0]);
                return null;
            }
            if (params[0].getClass() == Book.class) {
                mBookDAO.update((Book) params[0]);
                return null;
            }
            return null;
        }



    }


}
