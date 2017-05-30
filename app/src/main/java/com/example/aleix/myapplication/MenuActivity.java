package com.example.aleix.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button mvMapa = (Button) findViewById(R.id.vMapa);

        mvMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        Button Eetakedex = (Button) findViewById(R.id.eetakedex);

        Eetakedex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, EetakedexActivity.class);
                startActivity(intent);
            }
        });
        Button TusEetakemons = (Button) findViewById(R.id.tuseetak);

        TusEetakemons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, TusEetakemonsActivity.class);
                startActivity(intent);
            }
        });
    }
}
