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



    synchronized public   ActivitiesModule getActivitiesModule(){
        if (activitiesModule!=null) return activitiesModule; else
        { activitiesModule= new ActivitiesModule();
            return activitiesModule;
        }
    }

    synchronized SharedPrefModule getSharedPrefModule(){
        if (sharedPrefModule!=null) return sharedPrefModule; else {

            sharedPrefModule = new SharedPrefModule(application);
            return sharedPrefModule;}
    }




    synchronized    RepositoryModule getRepositoryModule(){
        if (repositoryModule!=null) return repositoryModule; else
        { repositoryModule= new RepositoryModule();
            return repositoryModule;
        }
    }

    synchronized DatabaseModule getDatabaseModule(){
        if (databaseModule!=null) return databaseModule;else {

            databaseModule = new DatabaseModule(application);
            return databaseModule;}
    }





}
