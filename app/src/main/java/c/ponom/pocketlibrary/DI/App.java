package c.ponom.pocketlibrary.DI;

import android.app.Activity;
import android.app.Application;
import android.app.Application.*;
import android.content.Context;
import android.os.Bundle;

import c.ponom.pocketlibrary.View.SingleActivity;

public  class App extends Application {
static Application thisApplication;
static ApplicationComponent applicationComponent;




    @Override
    public void onCreate() {
        super.onCreate();
        thisApplication = this;
    }

    public static ApplicationComponent getApplicationComponent(){
        return ApplicationComponent.getInstance(thisApplication);
    }



}
