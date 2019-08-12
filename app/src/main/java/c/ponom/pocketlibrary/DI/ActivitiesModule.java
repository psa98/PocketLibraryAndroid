package c.ponom.pocketlibrary.DI;



import c.ponom.pocketlibrary.ui.SingleActivity;

public class ActivitiesModule {


private static  SingleActivity singleActivity;
        // SecondActivity secondActivity;
        // зарезервировано для хранения активностей во время когда ссылки на них действительны, а так же объектов,
        // существование которых тесно связано с ЖЦ активности - фрагментов к примеру.




        public void injectSingleActivity(SingleActivity activity){
            singleActivity=activity;

        }

        public void removeSingleActivity(){
            singleActivity=null;
        }
        // вызывается в onDestroy. В принципе, можно вызывать в onStop, а инжектировть в onStart|onResume,
        // это будет более fail fast  в плане отладки



        SingleActivity getSingleActivity() {
        return singleActivity;
        }
}
