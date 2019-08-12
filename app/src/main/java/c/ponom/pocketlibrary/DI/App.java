package c.ponom.pocketlibrary.DI;

import android.app.Application;

public  class App extends Application {
static Application thisApplication;



    @Override
    public void onCreate() {
        super.onCreate();
        thisApplication = this;
    }

    public static ApplicationComponent getApplicationComponent(){
        return ApplicationComponent.getInstance(thisApplication);
    }



}
