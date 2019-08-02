package c.ponom.pocketlibrary.View.ViewModels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;


import c.ponom.pocketlibrary.Database.Repository;
import c.ponom.pocketlibrary.Database.RoomEntities.Book;

public class LoadedListViewModel extends AndroidViewModel {

    private LiveData<List<Book>> mLoadedBooksList;
    private Repository mRepository;


    public LoadedListViewModel(Application application) {
        super(application);
        mRepository = Repository.getRepository(application);

    }

    public void initViewModel(){

        mLoadedBooksList=mRepository.getListOfLoadedBooks();

    }



    public LiveData<List<Book>> getListOfLoadedBooks() { return mLoadedBooksList; }
    public LiveData<Integer> getTotalLoadedKb() { return mRepository.getTotalLoadedKb(); }
}




