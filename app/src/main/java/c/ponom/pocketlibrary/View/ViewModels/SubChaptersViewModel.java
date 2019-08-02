package c.ponom.pocketlibrary.View.ViewModels;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;


import c.ponom.pocketlibrary.Database.Repository;
import c.ponom.pocketlibrary.Database.RoomEntities.SubChapter;

public class SubChaptersViewModel extends AndroidViewModel {

    private LiveData<List<SubChapter>> mSubChapters;


    public SubChaptersViewModel(Application application) {
        super(application);
        Repository mRepository = Repository.getRepository(application);
        mSubChapters = mRepository.getAllSubChapterLiveList();
    }

    public LiveData<List<SubChapter>> getAllSubChapters() { return mSubChapters; }

}




