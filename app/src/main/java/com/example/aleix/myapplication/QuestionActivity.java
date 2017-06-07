package com.example.aleix.myapplication;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.example.aleix.myapplication.Entity.Eetakemon;
import com.example.aleix.myapplication.Entity.Question;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuestionActivity extends AppCompatActivity {

    final String tag = "MAPACT";

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

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8081")                //poner esta para atacar a la api nuestra 10.0.2.2
                .addConverterFactory(GsonConverterFactory.create());
        //
        Retrofit retrofit =
                builder
                        .client(
                                httpClient.build()
                        )
                        .build();

        // Create an instance of our GitHub API interface.
        Service acta = retrofit.create(Service.class);
        Eetakemon e = new Eetakemon();

        // Create a call instance for looking up Retrofit contributors.
        Call<Question> call1 = acta.Pregunta(e);
        Log.d(tag, "CALL: ***********DATOS**************************");


        // Fetch and print a list of the contributors to the library.
        call1.enqueue(new Callback<Question>() {
            @Override
            public void onResponse(Call<Question> call, Response<Question> response) {

                Log.d(tag, "CALL:onResponse ***********DATOS**************************");


            }

            @Override
            public void onFailure(Call<Question> call, Throwable t) {

            }
        });
    }
}
