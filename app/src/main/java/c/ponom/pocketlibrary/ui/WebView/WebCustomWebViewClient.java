package c.ponom.pocketlibrary.ui.WebView;

import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import c.ponom.pocketlibrary.DI.DIclass;
import c.ponom.pocketlibrary.data.DaoDatabase;
import c.ponom.pocketlibrary.data.Repository;
import c.ponom.pocketlibrary.ui.SingleActivity;

public class WebCustomWebViewClient extends WebViewClient {
    private Repository repository;


    WebCustomWebViewClient() {
        super();
 //       repository= DIclass.getRepository();


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
    public void onPageCommitVisible(final WebView view, String url) {
        ((SingleActivity) view.getContext()).hideProgressDialog();
        super.onPageCommitVisible(view, url);
        final float  progressToRestore =0.5f;

        // надо в каком - то классе-контейнере хранить прогресс. Вероятно в репозитарии
        /*
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
        ((SingleActivity) view.getContext()).hideProgressDialog();
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
