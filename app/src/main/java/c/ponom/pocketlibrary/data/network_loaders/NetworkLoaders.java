package c.ponom.pocketlibrary.data.network_loaders;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import c.ponom.pocketlibrary.di.DIСlass;
import c.ponom.pocketlibrary.data.room_entities.Author;
import c.ponom.pocketlibrary.data.room_entities.SubChapter;

public class NetworkLoaders {




    public static void loadChapterList(){
        final Context context = DIСlass.getAppContextAnywhere();

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
                //todo сделать обработку отсутствия связи отдельно - не выводить длинный тост в этом случае,
                // а сделать информативный типа "связи нет - можете читать загруженное ранее"

            }
        });
        queue.add(stringRequest);
    }



    public static void loadAuthorsList(final SubChapter subChapter){
        //todo - определиться  с проверкой на наличие как связи на момент вызова, так и скачанного и распарсенного ранее
        final Context context = DIСlass.getAppContextAnywhere();

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

                //todo сделать обработку отсутствия связи отдельно - не выводить длинный тост в этом случае,
                // а сделать информативный типа "связи нет - можете читать загруженное ранее"

            }
        });
        queue.add(stringRequest);
    }



    public static void loadBooksList (final Author author){
        //todo - определиться ч с проверкой на наличие как связи на момент вызова, так и скачанного и распарсенного ранее
        final Context context = DIСlass.getAppContextAnywhere();

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

                //todo сделать обработку отсутствия связи отдельно - не выводить длинный тост в этом случае,
                // а сделать информативный типа "связи нет - можете читать загруженное ранее"

            }
        });
        queue.add(stringRequest);
    }

}
