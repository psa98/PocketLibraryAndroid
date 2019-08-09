package c.ponom.pocketlibrary.DI;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import c.ponom.pocketlibrary.View.SingleActivity;

public class ActivitiesModule {


static  SingleActivity singleActivity;
        // todo SecondActivity secondActivity;
        // зарезервировано для хранения активностей во время когда ссылки на них действительны, а так же объектов,
        // существование которых тесно связано с ЖЦ активности




        public void  registerSingleActivity(SingleActivity activity){
            singleActivity=activity;

        }

        public void removeSingleActivity(){
            singleActivity=null;
        }


        public SingleActivity getSingleActivity() {
        return singleActivity;
        }
}
