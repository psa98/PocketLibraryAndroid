package c.ponom.pocketlibrary.View.Adapters;

import android.graphics.Typeface;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import c.ponom.pocketlibrary.Database.RoomEntities.Author;
import c.ponom.pocketlibrary.R;
import c.ponom.pocketlibrary.View.MainActivity;


public class AuthorsListAdapter extends ListAdapter<Author, AuthorsListAdapter.AuthorUserViewHolder> {


    private View.OnClickListener onClickListener = new View.OnClickListener() {

        //слушатель один - и он всегда берет данные из тега
        @Override
        public void onClick(View view) {
            Author author= (Author) view.getTag();
            if ( !author.url.contains(".txt")) {
                ((MainActivity) view.getContext()).showBooksForSubChapterAuthor(author);
            }else
            // - отдельные "авторы" на самом деле представляют собой ссылку на файл, а не на каталог.
            //todo Такие потом будем  открывать прямо отсюда
             {

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
                    // User properties may have changed if reloaded from the DB, but ID is fixed
                    return oldItem.subChapterName.equals(newItem.subChapterName)&&
                            oldItem.url.equals(newItem.url)&&
                            (oldItem.isLoaded==newItem.isLoaded);
                }
                @Override
                public boolean areContentsTheSame(
                        @NonNull Author oldItem, @NonNull Author newItem) {
                    // NOTE: if you use equals, your object must properly override Object#equals()
                    // Incorrectly returning false here will result in too many animations.
                    return oldItem.subChapterName.equals(newItem.subChapterName)&&
                            oldItem.url.equals(newItem.url)&&
                            (oldItem.isLoaded==newItem.isLoaded)

                    ;
                }
            };


}






