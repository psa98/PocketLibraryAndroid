package c.ponom.pocketlibrary.DI;

import android.app.Application;

import c.ponom.pocketlibrary.Database.DaoDatabase;

class DatabaseModule {
    private DaoDatabase daoDatabase;

    DaoDatabase getDaoDatabase() {
        return daoDatabase;
    }

    DatabaseModule(Application application) {
        daoDatabase =DaoDatabase.getDatabase(application);
    }
}
