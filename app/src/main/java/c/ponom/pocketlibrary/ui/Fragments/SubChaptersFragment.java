package c.ponom.pocketlibrary.ui.Fragments;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;

import c.ponom.pocketlibrary.DI.DIclass;
import c.ponom.pocketlibrary.data.RoomEntities.SubChapter;
import c.ponom.pocketlibrary.R;
import c.ponom.pocketlibrary.ui.Adapters.SubChapterAdapter;
import c.ponom.pocketlibrary.ui.SingleActivity;
import c.ponom.pocketlibrary.ui.ViewModels.SubChaptersViewModel;


public class SubChaptersFragment extends BaseFragment {


    public static SubChaptersFragment newInstance() {
        return new SubChaptersFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        SubChaptersViewModel viewModel = ViewModelProviders.of(this).get(SubChaptersViewModel.class);
        View view = inflater.inflate(R.layout.subchapter_list, container, false);
        activityUiOptions.setNewTitle("","");
        activityUiOptions.setBackButtonVisibility(false);

        final SubChapterAdapter subChapterAdapter=new SubChapterAdapter();
        viewModel.getAllSubChapters().observe(this, new Observer<List<SubChapter>>() {
                    @Override
                    public void onChanged(@Nullable List<SubChapter> subChapters) {
                     subChapterAdapter.submitList(subChapters);
                    }
                });

        RecyclerView recyclerView =  view.findViewById(R.id.recyclerViewMain);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(mDividerItemDecoration);

        recyclerView.setAdapter(subChapterAdapter);
       return view;

    }


}