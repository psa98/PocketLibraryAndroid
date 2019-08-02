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
import android.widget.TextView;

import java.util.List;


import c.ponom.pocketlibrary.Database.RoomEntities.Book;
import c.ponom.pocketlibrary.R;
import c.ponom.pocketlibrary.View.Adapters.BookAdapter;
import c.ponom.pocketlibrary.View.MainActivity;
import c.ponom.pocketlibrary.View.ViewModels.LoadedListViewModel;

public class LoadedListFragment extends Fragment {

    private LoadedListViewModel mViewModel;


    public  static LoadedListFragment newInstance() {

           return new LoadedListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
    //todo добавить возможность удаления книги по длинному нажатию или листанию влево

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.book_list_loaded, container, false);
        //binding =DataBindingUtil.inflate(inflater,R.layout.book_list_loaded,container,false);
        final BookAdapter loadedListAdapter =new BookAdapter();
        MainActivity mainActivity= ((MainActivity) view.getContext());
        mainActivity.setNewTitle("Book shelf","");
        mainActivity.setBackVisibility(true);
        mViewModel = ViewModelProviders.of(this).get(LoadedListViewModel.class);
        final TextView sizeInKb=view.findViewById(R.id.totalSize);
        mViewModel.initViewModel();
        mViewModel.getListOfLoadedBooks().observe(this, new Observer<List<Book>>() {
            @Override
            public void onChanged(@Nullable List<Book> books) {
                loadedListAdapter.submitList(books);
            }
        });
        mViewModel.getTotalLoadedKb().observe(this, new Observer<Integer>() {
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
