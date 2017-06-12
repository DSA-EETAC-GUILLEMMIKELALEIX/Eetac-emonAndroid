package com.example.aleix.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.session.MediaSession;
import android.util.Log;

/**
 * Created by aleix on 12/06/2017.
 */

public class TokenSaver {
    private final static String SHARED_PREF_NAME = "net.rouk1.SHARED_PREF_NAME";
    private final static String TOKEN_KEY = "net.rouk1.TOKEN_KEY";
    private static TokenSaver instance;
    static String tag = "MAPACT";

    public static String getToken(Context c) {
        SharedPreferences prefs = c.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(TOKEN_KEY, "");
    }

    public static void setToken(Context c, String token) {
        SharedPreferences prefs = c.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        Log.d(tag, "Token: " + token);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(TOKEN_KEY, token);
        editor.apply();
    }

}