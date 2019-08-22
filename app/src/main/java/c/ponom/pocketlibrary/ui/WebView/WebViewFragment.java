package c.ponom.pocketlibrary.ui.WebView;

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

import c.ponom.pocketlibrary.DI.DIclass;
import c.ponom.pocketlibrary.data.RoomEntities.Book;
import c.ponom.pocketlibrary.R;
import c.ponom.pocketlibrary.ui.Fragments.BaseFragment;
import c.ponom.pocketlibrary.ui.SingleActivity;

public class WebViewFragment extends BaseFragment {

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

        activityUiOptions.setNewTitle(currentBook.authorName,currentBook.bookName);
        activityUiOptions.setBackButtonVisibility(true);

        webView =  view.findViewById(R.id.webViewMain);
                webView.setWebViewClient(new WebCustomWebViewClient());
        String stringToRead = DIclass.getRepository().loadSavedFile( currentFileName);


        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        webView.loadDataWithBaseURL(currentBook.url+"_with-big-pictures.html",
                stringToRead, "text/html", null, null);
        return view;
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
