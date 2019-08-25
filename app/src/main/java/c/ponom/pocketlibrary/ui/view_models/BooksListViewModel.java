package c.ponom.pocketlibrary.ui.view_models;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;


import c.ponom.pocketlibrary.di.DIСlass;
import c.ponom.pocketlibrary.data.network_loaders.NetworkLoaders;
import c.ponom.pocketlibrary.data.Repository;
import c.ponom.pocketlibrary.data.room_entities.Author;
import c.ponom.pocketlibrary.data.room_entities.Book;


public class BooksListViewModel extends AndroidViewModel {

    private LiveData<List<Book>> mBookList;
    private Repository mRepository;


    public BooksListViewModel(Application application) {
        super(application);
        mRepository = DIСlass.getRepository();

    }

    public void initViewModel(Author author){

    NetworkLoaders.loadBooksList (author);

    mBookList=mRepository.getBooksForAuthorAndSubChapter(author);
    //случай, когда автора два в разных разделах у нас решен просто - показываем все его книги

    }


    public LiveData<List<Book>> getBookList() { return mBookList; }

}




