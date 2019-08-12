package c.ponom.pocketlibrary.ui.ViewModels;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;


import c.ponom.pocketlibrary.DI.DIclass;
import c.ponom.pocketlibrary.data.Repository;
import c.ponom.pocketlibrary.data.RoomEntities.SubChapter;

public class SubChaptersViewModel extends AndroidViewModel {

    private LiveData<List<SubChapter>> mSubChapters;
    private Repository mRepository;


    public SubChaptersViewModel(Application application) {
        super(application);
        mRepository= DIclass.getRepository();
        mSubChapters = mRepository.getAllSubChapterLiveList();
    }

    public LiveData<List<SubChapter>> getAllSubChapters() { return mSubChapters; }

}



