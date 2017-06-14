package com.example.aleix.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aleix.myapplication.Entity.Eetakemon;
import com.example.aleix.myapplication.Entity.Question;
import com.example.aleix.myapplication.Entity.Relation;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuestionActivity extends AppCompatActivity {

    final String tag = "MAPACT";
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    boolean bool = false;

    Retrofit retrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        Bundle bundle = getIntent().getExtras();
        //final String stuff1 = bundle.getString("nameExtra");
        final String stuff2 = bundle.getString("objetoExtra");
        final int stuff3 = bundle.getInt("id");

        String[] separated = stuff2.split("-");
        String idstring = separated[0];
        String name = separated[1];
        String tipo = separated[2];

        final int id;

        id = Integer.parseInt(idstring);


        Button volv = (Button) findViewById(R.id.Volver);

        volv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuestionActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        TextView mtv = (TextView) findViewById(R.id.textView2);
        mtv.setText("Eetakemon: " + name.toUpperCase() +  "  Tipo: " + tipo.toUpperCase());

        MediaPlayer mediaPlayer;
        mediaPlayer = MediaPlayer.create(this,R.raw.cancion);
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(100,100);
        mediaPlayer.start();

        final Button Si = (Button) findViewById (R.id.button4);
        final Button No = (Button) findViewById (R.id.button5);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://10.192.230.97:8081")                //poner esta para atacar a la api nuestra 10.0.2.2
                .addConverterFactory(GsonConverterFactory.create());
        //
        retrofit =
                builder
                        .client(
                                httpClient.build()
                        )
                        .build();

        // Create an instance of our GitHub API interface.
        Service acta = retrofit.create(Service.class);
        Eetakemon e = new Eetakemon();
        e.setTipo(stuff2);

        // Create a call instance for looking up Retrofit contributors.
        Call<Question> call1 = acta.Pregunta(e);
        Log.d(tag, "CALL: ***********DATOS**************************");

        final Relation relationEetak = new Relation();

        // Fetch and print a list of the contributors to the library.
        call1.enqueue(new Callback<Question>() {
            @Override
            public void onResponse(Call<Question> call, Response<Question> response) {
                TextView txtview = (TextView) findViewById(R.id.textView);
                Log.d(tag, "CALL:onResponse ***********DATOS**************************");
                final Question quest = response.body();
                Log.d(tag, "AAAAA: "+ quest.getQuestion() + ", "+ quest.getAnswer());
                txtview.setText(quest.getQuestion());

                Si.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(quest.getAnswer()==1){
                            //Eetakemon capturado
                            int level=0;
                            if (stuff2.equals("Inferior")){
                                level=1;
                            }
                            else if (stuff2.equals("Normal")){
                                level=15;
                            }
                            else if (stuff2.equals("Legendario")){
                                level=30;
                            }
                            SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                            String restoredText = prefs.getString("text", null);
                            if (restoredText != null) {
                                int idName = prefs.getInt("id", 0); //0 is the default value.
                            }


                            //relationEetak.setId(1);
                            relationEetak.setIdEetakemon(id);
                            relationEetak.setLevel(level);

                        }
                        if(quest.getAnswer()==0){
                            //Error volver al mapa y eliminar el marker
                            Intent intent = new Intent(QuestionActivity.this, MapsActivity.class);
                            startActivity(intent);
                        }
                        bool = true;
                    }

                });

                No.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(quest.getAnswer()==1){
                            //Error volver al mapa y eliminar el marker
                            Intent intent = new Intent(QuestionActivity.this, MapsActivity.class);
                            startActivity(intent);
                        }
                        if(quest.getAnswer()==0){
                            //Eetakemon capturado
                            int level=0;
                            if (stuff2.equals("Inferior")){
                                level=1;
                            }
                            else if (stuff2.equals("Normal")){
                                level=15;
                            }
                            else if (stuff2.equals("Legendario")){
                                level=30;
                            }

                            //relationEetak.setId(1);
                            relationEetak.setIdEetakemon(id);
                            relationEetak.setLevel(level);
                        }
                        bool = true;
                    }
                });
                if(bool) {
                    pasarRespuesta(relationEetak); //S'ha de provar
                }
            }

            @Override
            public void onFailure(Call<Question> call, Throwable t) {

            }
        });

    }

    final public void pasarRespuesta(Relation relationEetak){
        Service acta1 = retrofit.create(Service.class);

        // Create a call instance for looking up Retrofit contributors.
        Call<Relation> call2 = acta1.Capturado(relationEetak);
        Log.d(tag, "CALL: ***********DATOS**************************");



        // Fetch and print a list of the contributors to the library.
        call2.enqueue(new Callback<Relation>() {
            @Override
            public void onResponse(Call<Relation> call, Response<Relation> response) {

                Toast.makeText(QuestionActivity.this, "CAPTURADO", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(QuestionActivity.this, MapsActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Relation> call, Throwable t) {
                Toast.makeText(QuestionActivity.this, "Error al intentar capturar", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(QuestionActivity.this, MapsActivity.class);
                startActivity(intent);

            }
        });
    }

}
