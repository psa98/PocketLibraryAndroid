package c.ponom.pocketlibrary.ui.Fragments;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
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
import c.ponom.pocketlibrary.data.RoomEntities.Author;
import c.ponom.pocketlibrary.data.RoomEntities.SubChapter;
import c.ponom.pocketlibrary.R;
import c.ponom.pocketlibrary.ui.Adapters.AuthorsListAdapter;
import c.ponom.pocketlibrary.ui.SingleActivity;
import c.ponom.pocketlibrary.ui.ViewModels.AuthorsListViewModel;

public class AuthorsListFragment extends BaseFragment {

    private static SubChapter currentSubChapter;


    public  static AuthorsListFragment newInstance(SubChapter subChapter, Context context) {

            currentSubChapter = subChapter;

           return new AuthorsListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.authors_list_in_subchapter, container, false);
        final AuthorsListAdapter authorsListAdapter =new AuthorsListAdapter();

        activityUiOptions.setNewTitle(currentSubChapter.subChapterName,"");
        activityUiOptions.setBackButtonVisibility(true);
        AuthorsListViewModel mViewModel = ViewModelProviders.of(this).get(AuthorsListViewModel.class);
        mViewModel.initViewModel(currentSubChapter);
        mViewModel.getAuthorsListForSubChapter().observe(this, new Observer<List<Author>>() {
            @Override
            public void onChanged(@Nullable List<Author> authors) {
                authorsListAdapter.submitList(authors);
            }
        });
       RecyclerView recyclerView =  view.findViewById(R.id.recyclerViewAuthorsInChapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(authorsListAdapter);
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(mDividerItemDecoration);
        return view;
    }

    /* а это было зачем?
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(LoadedListViewModel.class);

    }
    */
}
