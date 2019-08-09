package c.ponom.pocketlibrary.DI;

import android.app.Application;

import c.ponom.pocketlibrary.Database.DaoDatabase;

public class DatabaseModule {
    DaoDatabase daoDatabase;

    public DaoDatabase getDaoDatabase() {
        return daoDatabase;
    }

     DatabaseModule(Application application) {
        daoDatabase =DaoDatabase.getDatabase(application);
    }
}
