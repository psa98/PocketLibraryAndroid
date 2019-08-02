package c.ponom.pocketlibrary.View.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;


import c.ponom.pocketlibrary.Database.Repository;
import c.ponom.pocketlibrary.Database.RoomEntities.Author;
import c.ponom.pocketlibrary.Database.RoomEntities.Book;
import c.ponom.pocketlibrary.View.MainActivity;

public class BooksListViewModel extends AndroidViewModel {

    private LiveData<List<Book>> mBookList;
    private Repository mRepository;


    public BooksListViewModel(Application application) {
        super(application);
        mRepository = Repository.getRepository(application);

    }

    public void initViewModel(Author author){
        
    MainActivity.loadBooksList (author,this.getApplication().getApplicationContext());

    mBookList=mRepository.getBooksForAuthorAndSubChapter(author);
    //случай, когда автора два в разных разделах у нас решен просто - показываем все его книги

    }


    public LiveData<List<Book>> getBookList() { return mBookList; }

}




