package c.ponom.pocketlibrary.View.WebView;

import android.app.Application;
import android.graphics.Bitmap;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import c.ponom.pocketlibrary.Database.Repository;
import c.ponom.pocketlibrary.View.MainActivity;

public class WebCustomWebViewClient extends WebViewClient {
    private Repository repository;
    WebCustomWebViewClient() {
        super();
    }

    @Override
    public void onLoadResource(WebView view, String url) {
        super.onLoadResource(view, url);
        repository=Repository.getRepository((Application) view.getContext().getApplicationContext());

    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        ((MainActivity) view.getContext()).showProgressDialog();

    }

    @Override
    public void onPageCommitVisible(final WebView view, String url) {
        ((MainActivity) view.getContext()).hideProgressDialog();
        super.onPageCommitVisible(view, url);
        final float  progressToRestore =0.5f;

        // надо в каком - то классе-контейнере хранить прогресс. Вероятно в репозитарии
        /* todo - протестировать - а вообще умеет textView показывать дикого размера строки? короткий html умеет точно
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                float webviewsize = view.getContentHeight() - view.getTop();
                float positionInWV = webviewsize * progressToRestore;
                int positionY = Math.round(view.getTop() + positionInWV);
                view.scrollTo(0, positionY);
            }
            // Delay the scrollTo to make it work
        }, 500);
);*/
    }

    @Override
    public void onPageFinished(final WebView view, String url) {
        super.onPageFinished(view, url);
        ((MainActivity) view.getContext()).hideProgressDialog();
        final float  progressToRestore =0.5f;

        /* надо в каком - то классе-контейнере хранить прогресс. Вероятно в репозитарии

                    view.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            float webviewsize = view.getContentHeight() - view.getTop();
                            float positionInWV = webviewsize * progressToRestore;
                            int positionY = Math.round(view.getTop() + positionInWV);
                            view.scrollTo(0, positionY);
                        }
                        // Delay the scrollTo to make it work
                    }, 1000);
        */
        }





}
