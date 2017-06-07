package com.example.aleix.myapplication;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
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

import com.example.aleix.myapplication.Entity.Eetakemon;

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

        Log.d(tag, "Eetakedex - onCreate() ");
        actbttn = (Button) findViewById(R.id.listar);

        actbttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

                // Create a call instance for looking up Retrofit contributors.
                Call<List<Eetakemon>> call1 = acta.ListarTusEetakemons();
                Log.d(tag, "CALL: ***********DATOS**************************");


                // Fetch and print a list of the contributors to the library.
                call1.enqueue(new Callback<List<Eetakemon>>(){
                    @Override
                    public void onResponse(Call<List<Eetakemon>> call, Response<List<Eetakemon>> response) {

                        Log.d(tag, "CALL:onResponse ***********DATOS**************************");
                        List<Eetakemon> e = (List<Eetakemon>) response.body();

                        final Context context = TusEetakemonsActivity.this;

                        // Create adapter
                        EetakemonAdapter adapter = new EetakemonAdapter(TusEetakemonsActivity.this, e);

                        Log.d(tag, "Adapter creado");
                        // Create list view
                        lv = (ListView) findViewById(R.id.listV);
                        lv.setAdapter(adapter);


                        for (Eetakemon etk: e) {
                            Log.d(tag, "Mostrar Eetakemon correctamente:" + etk);

                        }
                    }

                    @Override
                    public void onFailure(Call<List<Eetakemon>> call, Throwable t) {

                    }

                });
            }
        });
    }
}

