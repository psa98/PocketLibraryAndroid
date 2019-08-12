package c.ponom.pocketlibrary.ui.ViewModels;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;


import c.ponom.pocketlibrary.DI.DIclass;
import c.ponom.pocketlibrary.data.NetworkLoaders.NetworkLoaders;
import c.ponom.pocketlibrary.data.Repository;
import c.ponom.pocketlibrary.data.RoomEntities.Author;
import c.ponom.pocketlibrary.data.RoomEntities.SubChapter;

public class AuthorsListViewModel extends AndroidViewModel {

    private LiveData<List<Author>> mAuthorsListForSubChapter;
    private Repository mRepository;
    private SubChapter currentSubChapter=null;


    public AuthorsListViewModel(Application application) {
        super(application);
        mRepository = DIclass.getRepository();

    }

    public void initViewModel(SubChapter subChapter){


        if (currentSubChapter==null||currentSubChapter!=subChapter){
        NetworkLoaders.loadAuthorsList(subChapter);
        // todo - переместить это в репозиторий,
        // мы должны запросить у него данные, а он там у себя отдает все что есть сразу+грузит новое если
        // (1) есть связь, (а)причем безлимитная (?). (2) или  у нас новый сеанс работы.
        // (3) совсем красиво было бы скачать все как изначально  было в список,
        // потом прямо в репозитории сравнить список и базу, при отклонении длины обновить модель, иначе ничего не делать
        mAuthorsListForSubChapter=mRepository.getAuthorsForSubChapter(subChapter);
        currentSubChapter=subChapter;
        }

    }



    public LiveData<List<Author>> getAuthorsListForSubChapter() { return mAuthorsListForSubChapter; }

}




