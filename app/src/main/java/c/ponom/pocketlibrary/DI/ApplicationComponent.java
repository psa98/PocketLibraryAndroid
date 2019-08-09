package c.ponom.pocketlibrary.DI;

import android.app.Application;

public class ApplicationComponent {

    private  static RepositoryModule repositoryModule;
    private  static SharedPrefModule sharedPrefModule;
    private  static ActivitiesModule activitiesModule;
    private  static DatabaseModule databaseModule;

    private  static ApplicationComponent INSTANCE;
    private  static Application application;


    public  Application getApplication() {
        return application;
    }

    public static ApplicationComponent getInstance(final Application application)
    {
        if (INSTANCE == null) {
            synchronized (ApplicationComponent.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ApplicationComponent(application);
                }
            }
        }
        return INSTANCE;
    }


    public ApplicationComponent(Application currentApplication) {
        application=currentApplication;
    }


    public   RepositoryModule getRepositoryModule(){
        if (repositoryModule!=null) return repositoryModule; else return new RepositoryModule(application);

    }

    public   DatabaseModule getDatabaseModule(){
        if (databaseModule!=null) return databaseModule; else return new DatabaseModule(application);

    }

    public   ActivitiesModule getActivitiesModule(){
        if (activitiesModule!=null) return activitiesModule; else return new ActivitiesModule();
    }

    public   SharedPrefModule getSharedPrefModule(){
        if (sharedPrefModule!=null) return sharedPrefModule; else return new SharedPrefModule(application);
      }


}
