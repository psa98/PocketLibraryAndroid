package c.ponom.pocketlibrary.DI;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import c.ponom.pocketlibrary.Database.Repository;

public class SharedPrefModule {



    SharedPreferences sharedPreferences;

    public SharedPrefModule(Application application) {
        sharedPreferences  = application.getSharedPreferences("settings", Context.MODE_PRIVATE);
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }
}
