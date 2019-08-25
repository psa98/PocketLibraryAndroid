package c.ponom.pocketlibrary.ui.web_view;

import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import c.ponom.pocketlibrary.data.Repository;
import c.ponom.pocketlibrary.ui.SingleActivity;

public class WebCustomWebViewClient extends WebViewClient {
    private Repository repository;


    WebCustomWebViewClient() {
        super();

    }


    @Override
    public void onLoadResource(WebView view, String url) {
        super.onLoadResource(view, url);

    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        ((SingleActivity) view.getContext()).showProgressDialog();

    }



    @Override
    public void onPageFinished(final WebView view, String url) {
        super.onPageFinished(view, url);
        ((SingleActivity) view.getContext()).hideProgressDialog();

        }





}
