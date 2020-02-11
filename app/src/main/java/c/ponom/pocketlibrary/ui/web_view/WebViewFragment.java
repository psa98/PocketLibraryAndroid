package c.ponom.pocketlibrary.ui.web_view;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import c.ponom.pocketlibrary.di.DIСlass;
import c.ponom.pocketlibrary.data.room_entities.Book;
import c.ponom.pocketlibrary.R;
import c.ponom.pocketlibrary.ui.SingleActivity;
import c.ponom.pocketlibrary.ui.fragments.BaseFragment;

public class WebViewFragment extends BaseFragment {

    private static Book currentBook;
    private WebView webView;
    private static String currentFileName;


    public static WebViewFragment newInstance(Book book, String currentFile, Context context) {
        currentBook = book;
        currentFileName = currentFile;

        // todo теоретически можно использовать custom chrome tabs, которые очень быстрые -
        //  но они не работают с локальной строкой - надо поднимать локальный сервер

        return new WebViewFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        // TODO: надо найти способ попроще прятать пункт меню.
        //
        //  использовать фактически глобальную переменную не годится. Возможно проще будет
        // кроме вызов onDestroy не гарантируется
        //  фрагменту завести свой тулбар, чем менять параметры глобального.
        ((SingleActivity)getActivity()).hideSendButton();
        ((SingleActivity)getActivity()).onPrepareOptionsMenu(((SingleActivity)getActivity()).toolbar.getMenu());
        ((SingleActivity) getActivity()).currentBookUrl="";

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.web_content_scrolling, container, false);

        activityUiOptions.setNewTitle(currentBook.authorName, currentBook.bookName);
        activityUiOptions.setBackButtonVisibility(true);

        webView = view.findViewById(R.id.webViewMain);
        webView.setWebViewClient(new WebCustomWebViewClient());
        String stringToRead = DIСlass.getRepository().loadSavedFile(currentFileName);
        ((SingleActivity)getActivity()).showSendButton();

        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        webView.loadDataWithBaseURL(currentBook.url + "_with-big-pictures.html",
                stringToRead, "text/html", null, null);

        ((SingleActivity)getActivity()).onPrepareOptionsMenu(((SingleActivity)getActivity()).toolbar.getMenu());
        ((SingleActivity) getActivity()).currentBookUrl=currentBook.url;
        ((SingleActivity)getActivity()).showSendButton();

        return view;
    }


}