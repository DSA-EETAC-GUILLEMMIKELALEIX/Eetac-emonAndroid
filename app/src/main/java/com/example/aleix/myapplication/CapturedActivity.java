package com.example.aleix.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.example.aleix.myapplication.R;

public class CapturedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captured);

        Bundle bundle = getIntent().getExtras();
        final String stuff2 = bundle.getString("nameEetak");

        TextView textView = (TextView) findViewById(R.id.textView3);
        textView.setText(stuff2.toUpperCase() +  " CAPTURADO!");

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imageView);
        Glide.with(this).load(R.raw.pokeball).into(imageViewTarget);

        Button continuar = (Button) findViewById(R.id.button2);
        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CapturedActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });

    }
}
