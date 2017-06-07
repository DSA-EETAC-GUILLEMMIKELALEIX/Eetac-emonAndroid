package com.example.aleix.myapplication;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class QuestionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        Bundle bundle = getIntent().getExtras();
        String stuff1 = bundle.getString("nameExtra");
        String stuff2 = bundle.getString("tipoExtra");

        TextView mtv = (TextView) findViewById(R.id.textView2);
        mtv.setText("Eetakemon: " + stuff1.toUpperCase() +  "  Tipo: " + stuff2.toUpperCase());

        MediaPlayer mediaPlayer;
        mediaPlayer = MediaPlayer.create(this,R.raw.cancion);
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(100,100);
        mediaPlayer.start();
    }
}
