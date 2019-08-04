package c.ponom.pocketlibrary.Dagger;

import android.app.Application;
import android.content.Context;

import javax.inject.Inject;

import dagger.android.DaggerActivity;

public class _DaggerTestClass {

    @Inject
    Context context;

    @Inject
    DbHelper dbHelper;

    @Inject
    Application myApp;

    public void run() {

        context.databaseList();
        dbHelper.toString();
        myApp.getApplicationContext();
        int i = 1;
    }


}
