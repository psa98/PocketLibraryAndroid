package c.ponom.pocketlibrary.DI;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;



class SharedPrefModule {



    private SharedPreferences sharedPreferences;

    SharedPrefModule(Application application) {
        sharedPreferences  = application.getSharedPreferences("settings", Context.MODE_PRIVATE);
    }

    SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }
}
