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

import c.ponom.pocketlibrary.data.room_entities.SubChapter;
import c.ponom.pocketlibrary.R;
import c.ponom.pocketlibrary.ui.SingleActivity;


public class SubChapterAdapter extends ListAdapter<SubChapter, SubChapterAdapter.SubChapterUserViewHolder> {

    // данный метод был переделан с обычного адаптера, на ListAdapter



    private View.OnClickListener onClickListener = new View.OnClickListener() {

        //слушатель один - и он всегда берет данные из тега кликнутого вью
        @Override
        public void onClick(View view) {
            view.setClickable(false);
            view.setBackgroundColor(0xffeeeeee);
            SubChapter subChapter = ((SubChapter) view.getTag());
            ((SingleActivity) view.getContext()).showAuthorsListForSubChapter (subChapter);

        }
    };

    public SubChapterAdapter() {
        super(SubChapterAdapter.DIFF_CALLBACK);

    }


    @NonNull
    @Override
    public SubChapterUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sub_chapters_item, parent, false);
        view.setOnClickListener(onClickListener);
        return new SubChapterUserViewHolder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull SubChapterUserViewHolder viewHolder, int i) {
        SubChapter item = getItem(i);
        viewHolder.chapterName.setText(item.subChapterName);
        if (item.isLoaded) viewHolder.chapterName.setTypeface(Typeface.DEFAULT_BOLD);
        else viewHolder.chapterName.setTypeface(Typeface.DEFAULT);

        viewHolder.size.setText(item.sizeInMb==null?
                "":item.sizeInMb.toString());
        viewHolder.url.setText(item.url);
         viewHolder.itemView.setTag(item);

    }








    class SubChapterUserViewHolder extends RecyclerView.ViewHolder {


            TextView chapterName;
            TextView size;
            TextView url;

        SubChapterUserViewHolder(View view) {
                super(view);
                url=view.findViewById(R.id.url);
                chapterName= view.findViewById(R.id.chapterName);
                size=view.findViewById(R.id.size);

            }

        }


    private static final DiffUtil.ItemCallback<SubChapter> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<SubChapter>() {
                @Override
                public boolean areItemsTheSame(
                        @NonNull SubChapter oldItem, @NonNull SubChapter newItem) {

                    return oldItem.subChapterName.equals(newItem.subChapterName);
                }
                @Override
                public boolean areContentsTheSame(
                        @NonNull SubChapter oldItem, @NonNull SubChapter newItem) {

                    return oldItem.subChapterName.equals(newItem.subChapterName)&&
                            oldItem.url.equals(newItem.url);
                }
            };


}

