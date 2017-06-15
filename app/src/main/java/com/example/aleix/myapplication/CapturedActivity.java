package com.example.aleix.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.example.aleix.myapplication.R;

public class CapturedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captured);

        Bundle bundle = getIntent().getExtras();
        //  final String stuff1 = bundle.getString("nameExtra");
        final String stuff2 = bundle.getString("nameEetak");

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imageView);
        Glide.with(this).load(R.raw.pokeball).into(imageViewTarget);
    }
}
