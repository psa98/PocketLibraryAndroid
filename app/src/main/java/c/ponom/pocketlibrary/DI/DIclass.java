package c.ponom.pocketlibrary.DI;

import android.content.Context;
import android.content.SharedPreferences;

import c.ponom.pocketlibrary.Database.DaoDatabase;
import c.ponom.pocketlibrary.Database.Repository;
import c.ponom.pocketlibrary.View.SingleActivity;

public class DIclass {
    static public  SingleActivity getSingleActivity(){
        return App.getApplicationComponent().getActivitiesModule().getSingleActivity();
    }
    static public Repository getRepository(){
        return App.getApplicationComponent().getRepositoryModule().getRepository();
    }

    static public SharedPreferences getSharedPrefModule(){
        return App.getApplicationComponent().getSharedPrefModule().getSharedPreferences();
    }

    static public DaoDatabase getDaoDatabase(){
        return App.getApplicationComponent().getDatabaseModule().getDaoDatabase();
    }

    static public Context getAppContextAnywhere(){
        return App.getApplicationComponent().getApplication();
    }


    /*todo - (1) данный набор классов DIclass вполне расширяем. Добавление общедоступных элементов можно сделать
    через добавление отдельных модулей или включение их в имеющиеся к аналогичным
    (2) todo  - в данный момент у меня репозиторий и все остальное самостоятельно обеспечивают свое существование
    как синглтоны. Этот код там не нужен - и его нужно ликвидировать, за единственностью следит соответствующий модуль.
    (3) для будущих проектов - следует обеспечить синхронизацию получения объектов, сейчас это реализовано в коде
    соответствующих слассов синглтонов


     */




}
