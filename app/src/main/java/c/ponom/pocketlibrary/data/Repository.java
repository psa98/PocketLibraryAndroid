package c.ponom.pocketlibrary.data;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import c.ponom.pocketlibrary.di.DIСlass;
import c.ponom.pocketlibrary.data.dao.AuthorDAO;
import c.ponom.pocketlibrary.data.dao.BookDAO;
import c.ponom.pocketlibrary.data.dao.SubChapterDAO;
import c.ponom.pocketlibrary.data.room_entities.Author;
import c.ponom.pocketlibrary.data.room_entities.BaseEntity;
import c.ponom.pocketlibrary.data.room_entities.Book;
import c.ponom.pocketlibrary.data.room_entities.SubChapter;



public class Repository {


        private static AuthorDAO mAuthorDAO;
        private static BookDAO mBookDAO;
        private static SubChapterDAO mSubChapterDAO;

        // сохранение позиции WebView в текущей книге оказалось нетривиальной задачей.
        // Возможные варианты  - ли все таки решить эту проблему в нем, или перейти на вкладки Google
        // через поднятие внутреннего сервера - возможно там само запоминает позицию или есть удобный метод.
        // Да и быстрее он

        //private static HashMap<String, Bundle> webViewState = new HashMap<>();




    public Repository() {
            DaoDatabase db = DIСlass.getDaoDatabase();
            mAuthorDAO = db.getAuthorDAO();
            mBookDAO=db.getBooksDAO();
            mSubChapterDAO=db.getSubChapterDAO();

        }



        public LiveData<List<SubChapter>> getAllSubChapterLiveList()
        {return mSubChapterDAO.getAllSubChaptersLiveData();}


        public LiveData<List<Book>> getBooksForAuthorAndSubChapter(Author author)
        {

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

    public  String saveFile(String fileUriUUid, String newFile) {
        // сохраняем переданную строку в файл с полученным именем
        Context context = DIСlass.getAppContextAnywhere();
        File file = new File(context.getExternalFilesDir(null), fileUriUUid);
        try(FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(newFile.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            return "";
        }
        return file.getAbsolutePath();
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



    //todo вынести из основного потока (и сохранение может тоже)
    public String loadSavedFile(String fileUriUUid) {
        File file = new File(fileUriUUid );
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(2048*1024);
        byte[] byteBuff = new byte[512];
        int len;
        try (FileInputStream inputStream = new FileInputStream(file)) {
            while (true) {
                len = inputStream.read(byteBuff);
                if (len <= 0) break;
                byteArrayOutputStream.write(byteBuff, 0, len);

            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(DIСlass.getAppContextAnywhere(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }

        return new String(byteArrayOutputStream.toByteArray());
    }






}
