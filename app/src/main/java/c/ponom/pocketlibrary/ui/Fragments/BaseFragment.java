package c.ponom.pocketlibrary.ui.Fragments;

import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {

    ActivityUiOptions activityUiOptions;


    // This interface can be implemented by the Activity, parent Fragment,
    // or a separate test implementation.
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
