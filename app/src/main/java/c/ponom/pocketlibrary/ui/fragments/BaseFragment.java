package c.ponom.pocketlibrary.ui.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {

    protected ActivityUiOptions activityUiOptions;


    //Интерфейс связи с методами управления элементами ui активности  реализован тут, что бы не дублировать код


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }



    public interface ActivityUiOptions {

        void showSendButton();

        void hideSendButton();

        void setNewTitle(String newTitle, String newSubTitle);
        void setBackButtonVisibility(boolean setting);
        void showProgressBar ();
        void hideProgressBar ();

    }

    public void setActivityUiOptionsCallback (ActivityUiOptions callback) {
        this.activityUiOptions = callback;
    }

}
