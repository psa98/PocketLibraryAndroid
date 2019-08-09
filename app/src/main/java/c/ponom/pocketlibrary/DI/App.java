package c.ponom.pocketlibrary.DI;

import android.app.Activity;
import android.app.Application;
import android.app.Application.*;
import android.content.Context;
import android.os.Bundle;

import c.ponom.pocketlibrary.View.SingleActivity;

public  class App extends Application implements ActivityLifecycleCallbacks {
static Application thisApplication;
static ApplicationComponent applicationComponent;


    public Context getThisApplicationContext() {
     return    thisApplication.getApplicationContext();
    }



    @Override
    public void onCreate() {
        super.onCreate();
        thisApplication = this;
    }

    public static ApplicationComponent getApplicationComponent(){
        return ApplicationComponent.getInstance(thisApplication);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    //    if (activity.getClass() == SingleActivity.class) - разобраться когда и как это вызывается
    //    applicationComponent.getActivitiesModule().injectSingleActivity((SingleActivity) activity);
   }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        if (activity.getClass() == SingleActivity.class)
        applicationComponent.getActivitiesModule().removeSingleActivity();
    }

    /* внутри данного компонента хранятся ссылки на создаваемые объекты, которым потребен контекст приложения в целом
    и которые имеют время жизни совпадающее с приложением в целом
    - репозиторий,
    - база данных.
    - объект доступа к настройкам приложения (shared)
    Данный же компонент осуществляет их инициализацию при первом вызове
    */

}
