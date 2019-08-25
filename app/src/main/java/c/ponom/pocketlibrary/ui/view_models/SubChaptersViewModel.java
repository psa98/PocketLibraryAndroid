package c.ponom.pocketlibrary.ui.view_models;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;


import c.ponom.pocketlibrary.di.DIСlass;
import c.ponom.pocketlibrary.data.Repository;
import c.ponom.pocketlibrary.data.room_entities.SubChapter;

public class SubChaptersViewModel extends AndroidViewModel {

    private LiveData<List<SubChapter>> mSubChapters;
    private Repository mRepository;


    public SubChaptersViewModel(Application application) {
        super(application);
        mRepository= DIСlass.getRepository();
        mSubChapters = mRepository.getAllSubChapterLiveList();
    }

    public LiveData<List<SubChapter>> getAllSubChapters() { return mSubChapters; }

}




