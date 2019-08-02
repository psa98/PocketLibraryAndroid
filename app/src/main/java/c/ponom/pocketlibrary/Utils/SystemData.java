package c.ponom.pocketlibrary.Utils;

import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import java.util.Collection;

public class SystemData {

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity;
        connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        /* todo - реализовать интерфейс с коллбэком для проверки связи и вывода в строке состояния
        connectivity.addDefaultNetworkActiveListener(new ConnectivityManager.OnNetworkActiveListener() {
            @Override
            public void onNetworkActive() {

            }
        });
        */


        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (NetworkInfo networkInfo : info) {
                    if (networkInfo.isConnected())
                        return true;

                }
            }
        }
        return false;
    }


    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }



    protected static int dpToPixel(Context context, float dp) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();

        try {
            return (int) (dp * (metrics.densityDpi / 160f));
        } catch (NoSuchFieldError ignored) {
            return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics);
        }
    }


    public static <T> boolean isCollectionNotNullOrEmpty(Collection<T> collection) {
            return collection != null && !collection.isEmpty();
        }
    }
