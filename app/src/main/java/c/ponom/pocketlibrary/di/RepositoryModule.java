package c.ponom.pocketlibrary.di;


import c.ponom.pocketlibrary.data.Repository;

public class RepositoryModule {
    private Repository repository;

    RepositoryModule() {
    repository = new Repository();
    }

    public Repository getRepository() {
        return repository;
    }
}
