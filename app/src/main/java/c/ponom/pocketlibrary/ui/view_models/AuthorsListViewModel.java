package c.ponom.pocketlibrary.ui.view_models;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;


import c.ponom.pocketlibrary.di.DIСlass;
import c.ponom.pocketlibrary.data.network_loaders.NetworkLoaders;
import c.ponom.pocketlibrary.data.Repository;
import c.ponom.pocketlibrary.data.room_entities.Author;
import c.ponom.pocketlibrary.data.room_entities.SubChapter;

public class AuthorsListViewModel extends AndroidViewModel {

    private LiveData<List<Author>> mAuthorsListForSubChapter;
    private Repository mRepository;
    private SubChapter currentSubChapter=null;


    public AuthorsListViewModel(Application application) {
        super(application);
        mRepository = DIСlass.getRepository();

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




