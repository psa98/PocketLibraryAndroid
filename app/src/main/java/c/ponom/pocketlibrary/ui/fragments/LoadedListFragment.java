package c.ponom.pocketlibrary.ui.fragments;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


import c.ponom.pocketlibrary.data.room_entities.Book;
import c.ponom.pocketlibrary.R;
import c.ponom.pocketlibrary.ui.adapters.BookAdapter;
import c.ponom.pocketlibrary.ui.view_models.LoadedListViewModel;

public class LoadedListFragment extends BaseFragment {


    public  static LoadedListFragment newInstance() {

           return new LoadedListFragment();
    }


    //todo добавить возможность удаления загруженной книги по длинному нажатию или листанию влево

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.book_list_loaded, container, false);
        final BookAdapter loadedListAdapter =new BookAdapter();
        activityUiOptions.setNewTitle("Book shelf","");
        activityUiOptions.setBackButtonVisibility(true);
        LoadedListViewModel mViewModel = ViewModelProviders.of(this).get(LoadedListViewModel.class);
        final TextView sizeInKb=view.findViewById(R.id.totalSize);
        mViewModel.initViewModel();
        mViewModel.getListOfLoadedBooks().observe(getViewLifecycleOwner(), new Observer<List<Book>>() {
            @Override
            public void onChanged(@Nullable List<Book> books) {
                loadedListAdapter.submitList(books);
            }
        });
        mViewModel.getTotalLoadedKb().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer size) {
                sizeInKb.setText((size==null||size==0)?"":"Total: "+size+" Kb");
            }
        });


        RecyclerView recyclerView =  view.findViewById(R.id.recyclerViewBooksOfAuthor);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(loadedListAdapter);
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(mDividerItemDecoration);
        return view;
    }


}
