package c.ponom.pocketlibrary.ui.Fragments;

import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {

    protected ActivityUiOptions activityUiOptions;


    //Интерфейс связи с активностью реализован тут, что бы не дублировать код
    /*
     вот это в принципе тоже можно вынести в базовый фрагмент, убрать в реализациях

       @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


     */


    public interface ActivityUiOptions {

        void setNewTitle(String newTitle, String newSubTitle);
        void setBackButtonVisibility(boolean setting);
        void hideProgressDialog();
        void showProgressDialog();
    }

    public void setActivityUiOptionsCallback (ActivityUiOptions callback) {
        this.activityUiOptions = callback;
    }

}
