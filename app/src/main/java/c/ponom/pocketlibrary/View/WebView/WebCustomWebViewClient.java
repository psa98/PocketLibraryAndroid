package c.ponom.pocketlibrary.View.WebView;

import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebCustomWebViewClient extends WebViewClient {
    public WebCustomWebViewClient() {
        super();
    }

    @Override
    public void onLoadResource(WebView view, String url) {
        super.onLoadResource(view, url);

    }


    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        return super.shouldInterceptRequest(view, request);
    }
}
