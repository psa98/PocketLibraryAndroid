package c.ponom.pocketlibrary.ui.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.io.FileOutputStream;


import c.ponom.pocketlibrary.di.DIСlass;
import c.ponom.pocketlibrary.data.Repository;
import c.ponom.pocketlibrary.data.room_entities.Book;
import c.ponom.pocketlibrary.R;
import c.ponom.pocketlibrary.utils.NameHashes;
import c.ponom.pocketlibrary.ui.SingleActivity;


public class BookAdapter extends ListAdapter<Book, BookAdapter.ViewHolder> {

    private static Repository repository;
    private final Bitmap icon = BitmapFactory.
            decodeResource(DIСlass.getAppResources(),android.R.drawable.checkbox_off_background);
    private final Bitmap icon_done = BitmapFactory.
            decodeResource(DIСlass.getAppResources(),android.R.drawable.checkbox_on_background);


    // todo - наладить таки длинный клик с меню и удалением и перекидать методы вызова дествий
    //  в VM слой


    private View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
        Book book= (Book) view.getTag();
        view.setClickable(false);
        view.setBackgroundColor(0xffeeeeee);
        view.setEnabled(false);
        //это чтобы убрать возможность двойного клика

        loadBookAndShow(book,view.getContext());

        view.setClickable(true);
        view.setBackgroundColor(0xffeeeeee);
        view.setEnabled(true);

        }
    };


    private View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {

        @Override
        public boolean onLongClick(View view) {
        //Book book= (Book) view.getTag();
        // todo тут будет вызываться удаление файла,
        //  после вызова контекстного меню. хотя правильнее это сделать на листание влево
        return true;
        }
    };


    public BookAdapter() {
        super(BookAdapter.DIFF_CALLBACK);
        repository = DIСlass.getRepository();
    }


     private static void loadBookAndShow(final Book bookToRead, final Context context) {
        String urlToRead=bookToRead.url;
        if (!urlToRead.endsWith(".txt"))return;

        //если книжка скачана ранее - вызываем запрос на чтение
        if (!bookToRead.uriToFile.isEmpty()){
            ((SingleActivity) context).launchWebView(bookToRead.uriToFile,bookToRead);
        return;
        }


        //иначе скачиваем файл на диск и передаем его новый адрес в репозиторий и в вызов фрагмента
        final String  finalUrlToRead=urlToRead.concat("_with-big-pictures.html");

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, finalUrlToRead,
        new Response.Listener<String>() {
                   @Override
                    public void onResponse(String response) {
                    // вырезаем заголовочный фрагмент
                    int startPosition = response.indexOf("<form action");
                    int endPosition = response.indexOf("</form>")+7;
                    String header = response.substring(0,startPosition);
                    String body = response.substring((endPosition>0)
                            &&endPosition<response.length()?endPosition:0);
                    String newFileString = header+body;
                    String fileUUid = NameHashes.nameDigest(finalUrlToRead)+".html";
                    String filePath =repository.saveFile(fileUUid, newFileString);
                    bookToRead.uriToFile=filePath;
                    bookToRead.sizeInKb=(int)(new File(filePath).length()/1024);
                    repository.updateRecord(bookToRead);
                    ((SingleActivity) context).launchWebView(filePath,bookToRead);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        queue.add(stringRequest);

    }




    @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.book_item_new, parent, false);
                view.setOnClickListener(onClickListener);
                view.setOnLongClickListener(onLongClickListener);
                return new ViewHolder(view);

        }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        Book item;
        item =getItem(i);

       viewHolder.size.setText(item.sizeInKb==0 ?
                "":item.sizeInKb.toString()+ " Kb");
        viewHolder.url.setText(item.url);
        viewHolder.author.setText(item.authorName);
        viewHolder.book.setText(item.bookName);
        viewHolder.itemView.setTag(item);
        viewHolder.loadButton.setImageBitmap(item.uriToFile.isEmpty()? icon:icon_done);

    }




    class ViewHolder extends RecyclerView.ViewHolder {
          TextView size;
            TextView url;
            TextView author;
            TextView book;
            ImageView loadButton;




        ViewHolder(View view) {
                super(view);
                url=view.findViewById(R.id.urlInBooks);
                size=view.findViewById(R.id.sizeInBooks);
                author=view.findViewById(R.id.authorNameInBooks);
                book=view.findViewById(R.id.BooknameInBooks);
                loadButton=view.findViewById(R.id.loadBookIcon);

                url.setVisibility(View.GONE);
                //временно убрано, дабы не засорять ресайклер

            }
        }

    public  static final DiffUtil.ItemCallback<Book> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Book>() {
                @Override
                public boolean areItemsTheSame(
                        @NonNull Book oldItem, @NonNull Book newItem) {
                     return oldItem.subChapterName.equals(newItem.subChapterName)&&
                            oldItem.url.equals(newItem.url)&&
                            oldItem.bookName.equals(newItem.bookName)&&
                            oldItem.uriToFile.equals(newItem.uriToFile)&&
                            oldItem.sizeInKb.equals(newItem.sizeInKb);
                }
                @Override
                public boolean areContentsTheSame(
                        @NonNull Book oldItem, @NonNull Book newItem) {
                      return oldItem.subChapterName.equals(newItem.subChapterName)&&
                            oldItem.url.equals(newItem.url)&&
                            oldItem.bookName.equals(newItem.bookName)&&
                            oldItem.uriToFile.equals(newItem.uriToFile)&&
                            oldItem.sizeInKb.equals(newItem.sizeInKb);
                }
            };

}

