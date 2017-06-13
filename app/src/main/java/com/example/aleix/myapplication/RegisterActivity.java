package com.example.aleix.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aleix.myapplication.Entity.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {

    String tag = "Login";
    EditText mName, mEmail, mPass;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Set up the login form.
        mName =(EditText) findViewById(R.id.nombre);
        mEmail=(EditText)findViewById(R.id.email);
        mPass = (EditText) findViewById(R.id.password);
        register = (Button) findViewById(R.id.Registrarse);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = mName.getText().toString();
                String email = mEmail.getText().toString();
                String pass = mPass.getText().toString();

                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
                Retrofit.Builder builder = new Retrofit.Builder()
                        .baseUrl("http://10.192.230.97:8081")                //poner esta para atacar a la api nuestra 10.0.2.2
                        .addConverterFactory(GsonConverterFactory.create(gson));

                Retrofit retrofit =
                        builder
                                .client(
                                        httpClient.build()
                                )
                                .build();

                // Create an instance of our GitHub API interface.
                Service register = retrofit.create(Service.class);
                User usuario = new User();
                usuario.setNombre(name);
                usuario.setContrasena(pass);
                usuario.setEmail(email);
                Log.d(tag, "Registar: " + name + ", " + email);

                // Create a call instance for looking up Retrofit contributors.
                Call<String> call = register.Register(usuario);
                System.out.println("***********DATOS**************************");


                // Fetch and print a list of the contributors to the library.
                call.enqueue(new Callback<String>() {

                    //***************Comprobacion de que recoge los datos**********
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.code()==201) {
                            Toast.makeText(RegisterActivity.this, "REGISTRADO", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                            Log.d(tag, "Logueado correctamente");
                        }
                        else if(response.code()==202){
                            Toast.makeText(RegisterActivity.this, "ERROR 202: Usuario ya utilizado", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(RegisterActivity.this, "ERROR 202", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(RegisterActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                        Log.d(tag, "ERROR al Registrar");
                    }
                });
            }
        });
    }
}