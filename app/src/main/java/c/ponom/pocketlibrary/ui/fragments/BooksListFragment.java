package c.ponom.pocketlibrary.ui.fragments;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import c.ponom.pocketlibrary.data.room_entities.Author;
import c.ponom.pocketlibrary.data.room_entities.Book;
import c.ponom.pocketlibrary.R;
import c.ponom.pocketlibrary.ui.adapters.BookAdapter;
import c.ponom.pocketlibrary.ui.view_models.BooksListViewModel;

public class BooksListFragment extends BaseFragment {

    private static Author currentAuthor;

    public  static BooksListFragment newInstance(Author author, Context context) {
        currentAuthor = author;

         return new BooksListFragment();
    }



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.book_list, container, false);
        final BookAdapter bookAdapter=new BookAdapter();
        activityUiOptions.setNewTitle(currentAuthor.subChapterName,currentAuthor.authorName);
        activityUiOptions.setBackButtonVisibility(true);
        BooksListViewModel mViewModel = ViewModelProviders.of(this).get(BooksListViewModel.class);
        mViewModel.initViewModel(currentAuthor);
        mViewModel.getBookList().observe(this, new Observer<List<Book>>() {
            @Override
            public void onChanged(@Nullable List<Book> books) {
                bookAdapter.submitList(books);
            }
        });

        RecyclerView recyclerView =  view.findViewById(R.id.recyclerViewBooksOfAuthor);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(bookAdapter);
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(mDividerItemDecoration);

        return view;
    }


}
