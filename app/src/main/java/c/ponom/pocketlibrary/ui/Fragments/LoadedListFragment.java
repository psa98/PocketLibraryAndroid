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
import android.widget.TextView;

import java.util.List;


import c.ponom.pocketlibrary.DI.DIclass;
import c.ponom.pocketlibrary.data.RoomEntities.Book;
import c.ponom.pocketlibrary.R;
import c.ponom.pocketlibrary.ui.Adapters.BookAdapter;
import c.ponom.pocketlibrary.ui.SingleActivity;
import c.ponom.pocketlibrary.ui.ViewModels.LoadedListViewModel;

public class LoadedListFragment extends BaseFragment {


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
        activityUiOptions.setNewTitle("Book shelf","");
        activityUiOptions.setBackButtonVisibility(true);
        LoadedListViewModel mViewModel = ViewModelProviders.of(this).get(LoadedListViewModel.class);
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
