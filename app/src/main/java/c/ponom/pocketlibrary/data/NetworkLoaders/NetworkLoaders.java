package c.ponom.pocketlibrary.data.NetworkLoaders;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import c.ponom.pocketlibrary.DI.DIclass;
import c.ponom.pocketlibrary.data.RoomEntities.Author;
import c.ponom.pocketlibrary.data.RoomEntities.SubChapter;

public class NetworkLoaders {




    public static void loadChapterList(){
        final Context context = DIclass.getSingleActivity();
        //todo - теоретически сюда можно и контекст приложения передать, и так надежнее будет,
        // если вызвать обновление в момент повторота скажем, но пока оставим - что бы убедиться что DI для активности работает

        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://lib.ru/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        HTMLCustomParsers.parseMainPage(response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
        queue.add(stringRequest);
    }



    public static void loadAuthorsList(final SubChapter subChapter){
        //todo - определиться  с проверкой на наличие как связи на момент вызова, так и скачанного и распарсенного ранее
        final Context context = DIclass.getSingleActivity();

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, subChapter.url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        HTMLCustomParsers.parseSubChapter(response,subChapter);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
        queue.add(stringRequest);
    }



    public static void loadBooksList (final Author author){
        //todo - определиться ч с проверкой на наличие как связи на момент вызова, так и скачанного и распарсенного ранее
        final Context context = DIclass.getSingleActivity();

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, author.url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        HTMLCustomParsers.parseAuthorInSubChapter(response, author,author.url);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
        queue.add(stringRequest);
    }




}
