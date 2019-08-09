package c.ponom.pocketlibrary.DI;

import android.app.Application;

import c.ponom.pocketlibrary.Database.Repository;

public class RepositoryModule {
    Repository repository;

    public RepositoryModule(Application application) {
    repository = Repository.getRepository(application);
    }

    public Repository getRepository() {
        return repository;
    }
}
