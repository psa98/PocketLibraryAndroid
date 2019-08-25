package c.ponom.pocketlibrary.di;

import android.app.Application;

import c.ponom.pocketlibrary.data.DaoDatabase;

class DatabaseModule {
    private DaoDatabase daoDatabase;

    DaoDatabase getDaoDatabase() {
        return daoDatabase;
    }

    DatabaseModule(Application application) {
        daoDatabase =DaoDatabase.getDatabase(application);
    }
}
