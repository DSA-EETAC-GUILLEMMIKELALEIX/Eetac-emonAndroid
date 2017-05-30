package com.example.aleix.myapplication;

import android.app.ListActivity;
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

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EetakedexActivity extends AppCompatActivity {

    private Button actbttn;
    final String tag = "MAPACT";
    final ArrayList<Eetakemon> aa = new ArrayList<Eetakemon>();


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
                        .baseUrl("http://10.192.230.97:8081")                //poner esta para atacar a la api nuestra 10.0.2.2
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
                Call<List<Eetakemon>> call1 = acta.Listar();
                Log.d(tag, "CALL: ***********DATOS**************************");


                // Fetch and print a list of the contributors to the library.
                call1.enqueue(new Callback<List<Eetakemon>>(){
                    @Override
                    public void onResponse(Call<List<Eetakemon>> call, Response<List<Eetakemon>> response) {

                        Log.d(tag, "CALL:onResponse ***********DATOS**************************");
                        List<Eetakemon> e = (List<Eetakemon>) response.body();

                        /*setListAdapter( new ArrayAdapter<Eetakemon>(this,R.layout.list, e));
                        ListView lisv = getListView();
                        lisv.setTextFilterEnabled(true);

                        lisv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                            public void onItemClick(AdapterView<?> parent, View view,
                                                    int position, long id){
                                Toast.makeText(getApplicationContext(),((TextView) view).getText(), Toast.LENGTH_SHORT).show();
                            }
                        });*/


                        for (Eetakemon etk: e) {
                            Log.d(tag, "Mostrar Eetakemon correctamente:" + etk);

                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Toast.makeText(EetakedexActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                        Log.d(tag, "ERROR al mostrar");
                    }
                });

                /*@Override
                protected void onListItemClick(ListView l, View v, int position, long id){
                    String selectedValue = (String) getListAdapter().getItem(position);
                    Toast.makeText(this, selectedValue, Toast.LENGTH_SHORT).show();
                }
*/
            }
        });
    }
}
