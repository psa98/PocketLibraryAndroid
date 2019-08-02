package c.ponom.pocketlibrary.View.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import java.util.Objects;



import c.ponom.pocketlibrary.Database.Repository;
import c.ponom.pocketlibrary.Database.RoomEntities.Book;
import c.ponom.pocketlibrary.R;
import c.ponom.pocketlibrary.Utils.NameHashes;
import c.ponom.pocketlibrary.View.MainActivity;


public class BookAdapter extends ListAdapter<Book, BookAdapter.ViewHolder> {

    static Repository repository;

    private View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
        Book book= (Book) view.getTag();
            loadBookAndShow(book,view.getContext());
        }
    };

    public BookAdapter() {
        super(BookAdapter.DIFF_CALLBACK);
        repository = Objects.requireNonNull(Repository.getINSTANCE(),"repository not exist!");
    }

    public static void loadBookAndShow(final Book bookToRead, final Context context) {

        String urlToRead=bookToRead.url;
        if (!urlToRead.endsWith(".txt"))return;

        //если книжка скачана ранее - вызываем запрос на чтение
        if (bookToRead.uriToFile.length()>0){
            ((MainActivity) context).launchWebView(bookToRead.uriToFile,bookToRead);
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
                    //todo -  в репозиторий перенести всю работу с файлами
                    ((MainActivity) context).launchWebView(filePath,bookToRead);

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
        viewHolder.loadButton.setVisibility
                (item.uriToFile.isEmpty()? View.VISIBLE:View.INVISIBLE);

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

                Bitmap icon = BitmapFactory.decodeResource(view.getContext().getResources(),R.drawable.loaded_black);
                loadButton.setImageBitmap(icon);
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

