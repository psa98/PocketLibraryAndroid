package c.ponom.pocketlibrary.di;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import c.ponom.pocketlibrary.data.DaoDatabase;
import c.ponom.pocketlibrary.data.Repository;
import c.ponom.pocketlibrary.ui.SingleActivity;

public class DIСlass {

    // вероятно правильнее было бы называть этот модуль сервис локатором, а не DI. но в случае андроида
    // это дискуссионный вопрос, где проходит граница между ними, поскольку для многих объектов инициализация
    // осуществляется системой, а не вызываемым программистом конструктором, и чистый DI из учебника не реализуем

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


    static synchronized public Resources getAppResources(){
        return getAppContextAnywhere().getResources();
    }

    /* данный набор классов DIСlass вполне расширяем. Добавление общедоступных элементов можно сделать
    через добавление отдельных модулей или включение их в имеющиеся к аналогичным

     */




}
