package c.ponom.pocketlibrary.DI;

import android.app.Application;

public class ApplicationComponent {

    private  static RepositoryModule repositoryModule;
    private  static SharedPrefModule sharedPrefModule;
    private  static ActivitiesModule activitiesModule;
    private  static DatabaseModule databaseModule;

    private  static ApplicationComponent INSTANCE;
    private  static Application application;


    /* внутри данного компонента хранятся ссылки на создаваемые модули, которым может быть
     потребен контекст приложения в целом  и которые имеют время жизни совпадающее с приложением в целом
    - репозиторий,
    - база данных.
    - объект доступа к настройкам приложения (shared)
    Данный же компонент осуществляет их инициализацию при первом вызове
    */


    public  Application getApplication() {
        return application;
    }

    static ApplicationComponent getInstance(final Application application)
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


    private ApplicationComponent(Application currentApplication) {
        application=currentApplication;
    }


    synchronized RepositoryModule getRepositoryModule(){
        if (repositoryModule!=null) return repositoryModule; else return new RepositoryModule();

    }

    synchronized DatabaseModule getDatabaseModule(){
        if (databaseModule!=null) return databaseModule; else return new DatabaseModule(application);

    }

    synchronized public   ActivitiesModule getActivitiesModule(){
        if (activitiesModule!=null) return activitiesModule; else return new ActivitiesModule();
    }

    synchronized SharedPrefModule getSharedPrefModule(){
        if (sharedPrefModule!=null) return sharedPrefModule; else return new SharedPrefModule(application);
      }


}
