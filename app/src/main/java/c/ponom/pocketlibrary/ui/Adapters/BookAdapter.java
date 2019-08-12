package c.ponom.pocketlibrary.ui.Adapters;

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


import c.ponom.pocketlibrary.DI.DIclass;
import c.ponom.pocketlibrary.data.Repository;
import c.ponom.pocketlibrary.data.RoomEntities.Book;
import c.ponom.pocketlibrary.R;
import c.ponom.pocketlibrary.Utils.NameHashes;
import c.ponom.pocketlibrary.ui.SingleActivity;


public class BookAdapter extends ListAdapter<Book, BookAdapter.ViewHolder> {

    private static Repository repository;
    private Bitmap icon, icon_done;





    private View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
        Book book= (Book) view.getTag();
        view.setClickable(false);
        view.setBackgroundColor(0xffeeeeee);
        //это чтобы убрать возможность двойного клика
        loadBookAndShow(book,view.getContext());
        }
    };


    private View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {

        @Override
        public boolean onLongClick(View view) {
        Book book= (Book) view.getTag();
        // todo тут будет вызываться удаление файла, обнуление полей записи размер и путь - после вызова контекстного меню
        return true;
        }
    };


    public BookAdapter() {
        super(BookAdapter.DIFF_CALLBACK);

        repository = DIclass.getRepository();
    }

    public static void loadBookAndShow(final Book bookToRead, final Context context) {
        //todo - переместить все вызываемое  во вьюмодель. А загрузку - в репозитарий
        String urlToRead=bookToRead.url;
        if (!urlToRead.endsWith(".txt"))return;

        //если книжка скачана ранее - вызываем запрос на чтение
        if (bookToRead.uriToFile.length()>0){
            ((SingleActivity) context).launchWebView(bookToRead.uriToFile,bookToRead);
        return;
        }
        //иначе скачиваем файл на диск и передаем его новый адрес в репозиторий и в вызов фрагмента
        final String  finalUrlToRead=urlToRead.concat("_with-big-pictures.html");

        // todo - нестандартные ссылки пока не поддерживаются
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
                    String filePath =saveFile(context,fileUUid, newFileString);
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



    private static String saveFile(Context context,String fileUriUUid, String newFile) {
        File file = new File(context.getExternalFilesDir(null), fileUriUUid);
        try(FileOutputStream outputStream = new FileOutputStream(file)) {
                        outputStream.write(newFile.getBytes());
         } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            return "";
        }
        return file.getAbsolutePath();
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
        //viewHolder.loadButton.setVisibility
          //      (item.uriToFile.isEmpty()? View.VISIBLE:View.INVISIBLE);
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
                icon= BitmapFactory.
                        decodeResource(view.getContext().
                                getResources(),android.R.drawable.checkbox_off_background);
                icon_done= BitmapFactory.
                        decodeResource(view.getContext().
                                getResources(),android.R.drawable.checkbox_on_background);


                url.setVisibility(View.GONE);
                //временно убрано

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

