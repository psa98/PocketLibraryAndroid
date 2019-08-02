package c.ponom.pocketlibrary.View.ViewModels;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;


import c.ponom.pocketlibrary.Database.Repository;
import c.ponom.pocketlibrary.Database.RoomEntities.Author;
import c.ponom.pocketlibrary.Database.RoomEntities.SubChapter;
import c.ponom.pocketlibrary.View.MainActivity;

public class AuthorsListViewModel extends AndroidViewModel {

    private LiveData<List<Author>> mAuthorsListForSubChapter;
    private Repository mRepository;
    private SubChapter currentSubChapter=null;


    public AuthorsListViewModel(Application application) {
        super(application);
        mRepository = Repository.getRepository(application);

    }

    public void initViewModel(SubChapter subChapter){


        if (currentSubChapter==null||currentSubChapter!=subChapter){
        MainActivity.loadAuthorsList(subChapter, getApplication().getApplicationContext());
        mAuthorsListForSubChapter=mRepository.getAuthorsForSubChapter(subChapter);
        currentSubChapter=subChapter;
        }

    }



    public LiveData<List<Author>> getAuthorsListForSubChapter() { return mAuthorsListForSubChapter; }

}




