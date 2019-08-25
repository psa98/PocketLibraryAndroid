package c.ponom.pocketlibrary.ui.view_models;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;


import c.ponom.pocketlibrary.di.DIСlass;
import c.ponom.pocketlibrary.data.Repository;
import c.ponom.pocketlibrary.data.room_entities.Book;

public class LoadedListViewModel extends AndroidViewModel {

    private LiveData<List<Book>> mLoadedBooksList;
    private Repository mRepository;


    public LoadedListViewModel(Application application) {
        super(application);
        mRepository = DIСlass.getRepository();

    }

    public void initViewModel(){

        mLoadedBooksList=mRepository.getListOfLoadedBooks();

    }



    public LiveData<List<Book>> getListOfLoadedBooks() { return mLoadedBooksList; }
    public LiveData<Integer> getTotalLoadedKb() { return mRepository.getTotalLoadedKb(); }
}




