package c.ponom.pocketlibrary.View.Fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;

import c.ponom.pocketlibrary.Database.RoomEntities.SubChapter;
import c.ponom.pocketlibrary.R;
import c.ponom.pocketlibrary.View.Adapters.SubChapterAdapter;
import c.ponom.pocketlibrary.View.MainActivity;
import c.ponom.pocketlibrary.View.ViewModels.SubChaptersViewModel;


public class SubChaptersFragment extends Fragment {

    private SubChaptersViewModel viewModel;


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

        viewModel = ViewModelProviders.of(this).get(SubChaptersViewModel.class);
        View view = inflater.inflate(R.layout.subchapter_list, container, false);
        ((MainActivity) getContext()).setNewTitle("","");
        ((MainActivity) getContext()).setBackVisibility(false);


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
