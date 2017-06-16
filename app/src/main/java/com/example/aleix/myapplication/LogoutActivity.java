package com.example.aleix.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

public class LogoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);
        final String tag = "MAPACT";

        Button No = (Button) findViewById(R.id.no);

        No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogoutActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });
        Button Si = (Button) findViewById(R.id.si);

        Si.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               TokenSaver.setToken(LogoutActivity.this, "0");
                Log.d(tag, TokenSaver.getToken(LogoutActivity.this));
                Intent intent = new Intent(LogoutActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
