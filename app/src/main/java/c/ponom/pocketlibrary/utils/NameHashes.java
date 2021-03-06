package c.ponom.pocketlibrary.utils;

import android.util.Base64;
import android.util.Log;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static java.lang.System.exit;

public class NameHashes {



    public synchronized static String shortNameDigest (String stringToHash){
        return nameDigest(stringToHash).substring(0,20);
    }

    public synchronized static String nameDigest (String stringToHash){

        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            //обработка не требуется - все устройства должны поддерживать этот формат, но если вдруг - программа неработоспособна
            Log.wtf("","What the Terrible Failure?!");
            e.printStackTrace();
            exit(1);
        }
        byte[] hash;
        if (digest != null) {
            hash = digest.digest((stringToHash).getBytes(StandardCharsets.UTF_8));
        return bytesToBase64String(hash);}
         else return null;
    }




    private static String bytesToBase64String(byte[] encoded) {
        return Base64.encodeToString(encoded,Base64.URL_SAFE+Base64.NO_WRAP);
    }


    public static  byte[] base64StringToBytes(String base64String) {
        return Base64.decode(base64String,Base64.URL_SAFE+Base64.NO_WRAP);


    }

}
