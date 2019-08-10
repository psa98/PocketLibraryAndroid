package c.ponom.pocketlibrary.DI;


import c.ponom.pocketlibrary.Database.Repository;

public class RepositoryModule {
    private Repository repository;

    RepositoryModule() {
    repository = new Repository();
    }

    public Repository getRepository() {
        return repository;
    }
}
