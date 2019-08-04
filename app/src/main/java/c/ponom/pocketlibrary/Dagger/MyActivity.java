package c.ponom.pocketlibrary.Dagger;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import javax.inject.Inject;

import c.ponom.pocketlibrary.Dagger.ActivityModule;
import c.ponom.pocketlibrary.Dagger.DaggerActivityComponent;
import c.ponom.pocketlibrary.Dagger.DataManager;
import c.ponom.pocketlibrary.Dagger.MyApplication;


public class MyActivity extends AppCompatActivity {

    @Inject
    DataManager mDataManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

       DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .applicationComponent(MyApplication.get(this).getComponent())
                .build().inject(this);
        _DaggerTestClass daggerTestClass=new _DaggerTestClass();
        daggerTestClass.run();

    }
}
