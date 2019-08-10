package c.ponom.pocketlibrary.View.WebView;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
import c.ponom.pocketlibrary.View.SingleActivity;

public class WebViewFragment extends Fragment {

    private static Book currentBook;
    private WebView webView;
    private static String currentFileName;
    int x,y;
   
    public float progress;



    public  static WebViewFragment newInstance(Book book,String currentFile, Context context) {
        currentBook = book;
        currentFileName=currentFile;

         // todo теоретически можно использовать custom chrome tabs, которые очень быстрые -
        //  но они не работают с локальной строкой - надо поднимать локальный сервер

        return new WebViewFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (savedInstanceState!=null)
            progress=savedInstanceState.getFloat("progress");

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.web_content_scrolling, container, false);
        ((SingleActivity) getContext()).setNewTitle(currentBook.authorName,currentBook.bookName);
        ((SingleActivity) getContext()).setBackButtonVisibility(true);
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





    // Calculate the % of scroll progress in the actual web page content
    private float calculateProgression(WebView content) {
        float positionTopView = content.getTop();
        float contentHeight = content.getContentHeight();
        float currentScrollPosition = content.getScrollY();
        return  (currentScrollPosition - positionTopView) / contentHeight;

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        float mProgress = calculateProgression(webView);
        outState.putFloat("progress",mProgress);
        super.onSaveInstanceState(outState);


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

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
