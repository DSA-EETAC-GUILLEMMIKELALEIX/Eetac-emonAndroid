package com.example.aleix.myapplication.Entity;

import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;

/**
 * Created by usuario on 14/06/2017.
 */

public class JWTUtils {

    public static int decoded(String JWTEncoded) throws Exception {
        int id=0;
        try {
            String[] split = JWTEncoded.split("\\.");
            Log.d("JWT_DECODED", "Header: " + getJson(split[0]));
            Log.d("JWT_DECODED", "Body: " + getJson(split[1]));
            String cuerpo  = getJson(split[1]);
            String [] separated = cuerpo.split(",");
            String idString = separated[1];
            String [] separated2 = idString.split(":");
            String idString2 = separated2[1];
            idString2 = idString2.replace("\"","");
            id = Integer.parseInt(idString2);

        } catch (UnsupportedEncodingException e) {
            //Error
        }
        return id;
    }

    private static String getJson(String strEncoded) throws UnsupportedEncodingException{
        byte[] decodedBytes = Base64.decode(strEncoded, Base64.URL_SAFE);
        return new String(decodedBytes, "UTF-8");
    }
}
