package c.ponom.pocketlibrary.DI;

import android.content.SharedPreferences;

import c.ponom.pocketlibrary.Database.DaoDatabase;
import c.ponom.pocketlibrary.Database.Repository;
import c.ponom.pocketlibrary.View.SingleActivity;

public class DI {
    static public  SingleActivity injectSingleActivity(){
        return App.getApplicationComponent().getActivitiesModule().getSingleActivity();
    }
    static public Repository injectRepository(){
        return App.getApplicationComponent().getRepositoryModule().getRepository();
    }

    static public SharedPreferences injectSharedPrefModule(){
        return App.getApplicationComponent().getSharedPrefModule().getSharedPreferences();
    }

    static public DaoDatabase injectDaoDatabase(){
        return App.getApplicationComponent().getDatabaseModule().getDaoDatabase();
    }

    /*todo - (1) данный набор классов DI вполне расширяем. Добавление общедоступных элементов можно сделать
    через добавление отдельных модулей или включение их в имеющиеся к аналогичным
    (2) todo  - в данный момент у меня








     */




}
