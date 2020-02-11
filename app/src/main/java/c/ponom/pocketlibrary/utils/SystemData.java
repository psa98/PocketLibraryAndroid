package c.ponom.pocketlibrary.utils;

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




    }
