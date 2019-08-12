package c.ponom.pocketlibrary.DI;

import android.content.Context;
import android.content.SharedPreferences;

import c.ponom.pocketlibrary.data.DaoDatabase;
import c.ponom.pocketlibrary.data.Repository;
import c.ponom.pocketlibrary.ui.SingleActivity;

public class DIclass {
    static synchronized public  SingleActivity getSingleActivity(){
        return App.getApplicationComponent().getActivitiesModule().getSingleActivity();
    }
    static synchronized public Repository getRepository(){
        return App.getApplicationComponent().getRepositoryModule().getRepository();
    }

    static synchronized public SharedPreferences getSharedPrefModule(){
        return App.getApplicationComponent().getSharedPrefModule().getSharedPreferences();
    }

    static synchronized public DaoDatabase getDaoDatabase(){
        return App.getApplicationComponent().getDatabaseModule().getDaoDatabase();
    }

    static synchronized public Context getAppContextAnywhere(){
        return App.getApplicationComponent().getApplication().getApplicationContext();
    }


    /*todo - (1) данный набор классов DIclass вполне расширяем. Добавление общедоступных элементов можно сделать
    через добавление отдельных модулей или включение их в имеющиеся к аналогичным
    (2) todo  - в данный момент у меня база данных  самостоятельно обеспечивают свое существование
    как синглтон. Этот код там не нужен - и его нужно ликвидировать, за единственностью следит соответствующий модуль.

     */




}
