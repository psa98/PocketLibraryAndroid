package c.ponom.pocketlibrary.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import c.ponom.pocketlibrary.R;
import c.ponom.pocketlibrary.data.room_entities.Author;
import c.ponom.pocketlibrary.data.room_entities.SubChapter;
import c.ponom.pocketlibrary.ui.adapters.AuthorsListAdapter;
import c.ponom.pocketlibrary.ui.view_models.AuthorsListViewModel;

public class InfoFragment extends BaseFragment {

    private static SubChapter currentSubChapter;


    public  static InfoFragment newInstance() {



           return new InfoFragment();
    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.info_fragment, container, false);

        return view;
    }


}
