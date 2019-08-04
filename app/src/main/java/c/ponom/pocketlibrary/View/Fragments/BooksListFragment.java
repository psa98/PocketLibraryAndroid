package c.ponom.pocketlibrary.View.Fragments;

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

import c.ponom.pocketlibrary.Database.RoomEntities.Author;
import c.ponom.pocketlibrary.Database.RoomEntities.Book;
import c.ponom.pocketlibrary.R;
import c.ponom.pocketlibrary.View.Adapters.BookAdapter;
import c.ponom.pocketlibrary.View.SingleActivity;
import c.ponom.pocketlibrary.View.ViewModels.BooksListViewModel;

public class BooksListFragment extends Fragment {

    private static Author currentAuthor;


    public  static BooksListFragment newInstance(Author author, Context context) {
        currentAuthor = author;
         return new BooksListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.book_list, container, false);
        final BookAdapter bookAdapter=new BookAdapter();
        String currentTitle = String.format("%s, %s", currentAuthor.subChapterName, currentAuthor.authorName);
        ((SingleActivity) getContext()).setNewTitle(currentAuthor.subChapterName,currentAuthor.authorName);
        ((SingleActivity) getContext()).setBackVisibility(true);
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
