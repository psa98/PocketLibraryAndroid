package c.ponom.pocketlibrary.ui.adapters;

import android.graphics.Typeface;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import c.ponom.pocketlibrary.data.room_entities.Author;
import c.ponom.pocketlibrary.R;
import c.ponom.pocketlibrary.ui.SingleActivity;


public class AuthorsListAdapter extends ListAdapter<Author, AuthorsListAdapter.AuthorUserViewHolder> {


    private View.OnClickListener onClickListener = new View.OnClickListener() {

        //слушатель один - и он всегда берет данные из тега
        @Override
        public void onClick(View view) {
            Author author= (Author) view.getTag();

            if ( !author.url.contains(".txt")) {
                view.setClickable(false);
                view.setBackgroundColor(0xffeeeeee);
                ((SingleActivity) view.getContext()).showBooksForSubChapterAuthor(author);

            }
        }
    };

     public AuthorsListAdapter() {
        super(AuthorsListAdapter.DIFF_CALLBACK);
    }


    @NonNull
        @Override
        public AuthorUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.authors_in_chapter_item, parent, false);
                view.setOnClickListener(onClickListener);
                return new AuthorUserViewHolder(view);

        }

    @Override
    public void onBindViewHolder(@NonNull AuthorUserViewHolder viewHolder, int i) {

        Author item;
        item =getItem(i);
        viewHolder.size.setText(item.sizeInMb==null?
                "":item.sizeInMb.toString());
        viewHolder.url.setText(item.url);
        viewHolder.author.setText(item.authorName);
        if (item.isLoaded)
            viewHolder.author.setTypeface(Typeface.DEFAULT_BOLD);
        else
            viewHolder.author.setTypeface(Typeface.DEFAULT);
          viewHolder.itemView.setTag(item);
    }



    class AuthorUserViewHolder extends RecyclerView.ViewHolder {

            TextView size;
            TextView url;
            TextView author;

        AuthorUserViewHolder(View view) {
                super(view);
                url=view.findViewById(R.id.urlInAuthors);
                size=view.findViewById(R.id.sizeInAuthors);
                author=view.findViewById(R.id.authorNameInAuthors);
                }
        }


    private static final DiffUtil.ItemCallback<Author> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Author>() {
                @Override
                public boolean areItemsTheSame(
                        @NonNull Author oldItem, @NonNull Author newItem) {
                    return oldItem.subChapterName.equals(newItem.subChapterName)&&
                            oldItem.url.equals(newItem.url)&&
                            (oldItem.isLoaded==newItem.isLoaded);
                }
                @Override
                public boolean areContentsTheSame(
                        @NonNull Author oldItem, @NonNull Author newItem) {
                       return oldItem.subChapterName.equals(newItem.subChapterName)&&
                            oldItem.url.equals(newItem.url)&&
                            (oldItem.isLoaded==newItem.isLoaded)

                    ;
                }
            };


}






