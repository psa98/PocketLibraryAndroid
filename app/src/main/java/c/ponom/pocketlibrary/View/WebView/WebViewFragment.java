package c.ponom.pocketlibrary.View.WebView;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

import c.ponom.pocketlibrary.Database.RoomEntities.Book;
import c.ponom.pocketlibrary.R;
import c.ponom.pocketlibrary.View.MainActivity;

public class WebViewFragment extends Fragment {

    private static Book currentBook;
    WebView webView;
    private static String currentFileName;
    int x,y;
    static String baseDir;



    public  static WebViewFragment newInstance(Book book,String currentFile, Context context) {
        currentBook = book;
        currentFileName=currentFile;

         // todo теоретически можно использовать custom chrome tabs, которые очень быстрые -
        //  но они не работают с локальной строкой - надо поднимать локальный сервер
        baseDir= book.url.substring(0,book.url.lastIndexOf("/"));
        Log.e("!!!", "newInstance: "+baseDir);
        return new WebViewFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
   }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.web_content_scrolling, container, false);
        ((MainActivity) getContext()).setNewTitle(currentBook.authorName,currentBook.bookName);
        ((MainActivity) getContext()).setBackVisibility(true);
        webView =  view.findViewById(R.id.webViewMain);
        webView.setWebViewClient(new WebCustomWebViewClient());
        String stringToRead = loadSavedFile(this.getContext(), currentFileName);
        //webView.getSettings().setSupportZoom(true);
        //webView.getSettings().setBuiltInZoomControls(true);
        //webView.loadUrl("file:///"+url);
        //todo - проверить можно ли скормить вебвью файл через file:\\
        // + передать сюда корневой каталог как то или вытащить его из параметра

        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        //webView.loadDataWithBaseURL(null, getHTMLString(), "text/html", null, null);


        //webView.getSettings().setAllowContentAccess(false);
        webView.loadDataWithBaseURL(currentBook.url+"_with-big-pictures.html", stringToRead, "text/html", null, null);
        return view;
    }




    //todo вынести из основного потока (и сохранение может тоже)
    private String loadSavedFile(Context context, String fileUriUUid) {
        File file = new File(fileUriUUid );
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(2048*1024);
        byte[] byteBuff = new byte[512];
        int len;
        Log.e("File", "loadSavedFile: start" );
        try (FileInputStream inputStream = new FileInputStream(file)) {
            while (true) {
                len = inputStream.read(byteBuff);
                if (len <= 0) break;
                byteArrayOutputStream.write(byteBuff, 0, len);

            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }

          return new String(byteArrayOutputStream.toByteArray());
    }




    /* //todo - при просмотре наладить - обработку "назад", запоминание позиции при выходе.
    @Override
    public void onBackPressed() {
        if(webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    } */



}
