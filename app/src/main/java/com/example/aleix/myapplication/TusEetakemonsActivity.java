package com.example.aleix.myapplication;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aleix.myapplication.Entity.Captured;
import com.example.aleix.myapplication.Entity.Eetakemon;
import com.example.aleix.myapplication.Entity.Relation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TusEetakemonsActivity extends AppCompatActivity {

    private Button actbttn;
    final String tag = "MAPACT";
    public String name;
    private List<String> l;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eetakedex);

        Button volver = (Button) findViewById(R.id.volver);

        Log.d(tag, "Eetakedex - onCreate() ");

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://147.83.7.158:8081")
                .addConverterFactory(GsonConverterFactory.create(gson));
//
        Retrofit retrofit =
                builder
                        .client(
                                httpClient.build()
                        )
                        .build();

        // Create an instance of our GitHub API interface.
        Service acta = retrofit.create(Service.class);
        String token = "Bearer " + TokenSaver.getToken(this);


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final int  data = sharedPreferences.getInt("id", 0) ;

        // Create a call instance for looking up Retrofit contributors.
        Call<List<Captured>> call1 = acta.ListarTusEetakemons(token, data);
        Log.d(tag, "CALL: ***********DATOS**************************");


        // Fetch and print a list of the contributors to the library.
        call1.enqueue(new Callback<List<Captured>>(){
            @Override
            public void onResponse(Call<List<Captured>> call, Response<List<Captured>> response) {

                Log.d(tag, "Response: " + response.code());

                final Context context = TusEetakemonsActivity.this;

                if(response.code()==200) {
                    List<Captured> e = (List<Captured>) response.body();
                    // Create adapter
                    RelationAdapter adapter = new RelationAdapter(TusEetakemonsActivity.this, e);

                    Log.d(tag, "Adapter creado");
                    // Create list view
                    lv = (ListView) findViewById(R.id.listV);
                    lv.setAdapter(adapter);


                    for (Captured etk : e) {
                        Log.d(tag, "Mostrar Eetakemon correctamente:" + etk);

                    }
                }else if(response.code()==202){
                    Toast.makeText(context, "NO HAY EETAC-EMONS: lista vacia", Toast.LENGTH_SHORT).show();
                    Log.d(tag, "ERROR lista vacia");
                }
                else if(response.code()==401){
                    Toast.makeText(context, "Token expirado: Vuelve a loguearte", Toast.LENGTH_SHORT).show();
                    Log.d(tag, "ERROR token");
                }
            }

            @Override
            public void onFailure(Call<List<Captured>> call, Throwable t) {
                Log.d(tag, "ERROR 2");
                Toast.makeText(TusEetakemonsActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
            }

        });

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TusEetakemonsActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });
    }
}

