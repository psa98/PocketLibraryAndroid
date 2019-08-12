package c.ponom.pocketlibrary.ui.ViewModels;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;


import c.ponom.pocketlibrary.DI.DIclass;
import c.ponom.pocketlibrary.data.NetworkLoaders.NetworkLoaders;
import c.ponom.pocketlibrary.data.Repository;
import c.ponom.pocketlibrary.data.RoomEntities.Author;
import c.ponom.pocketlibrary.data.RoomEntities.Book;



public class BooksListViewModel extends AndroidViewModel {

    private LiveData<List<Book>> mBookList;
    private Repository mRepository;


    public BooksListViewModel(Application application) {
        super(application);
        mRepository = DIclass.getRepository();

    }

    public void initViewModel(Author author){

    NetworkLoaders.loadBooksList (author);

    mBookList=mRepository.getBooksForAuthorAndSubChapter(author);
    //случай, когда автора два в разных разделах у нас решен просто - показываем все его книги

    }


    public LiveData<List<Book>> getBookList() { return mBookList; }

}



