package com.example.aleix.myapplication;

import com.example.aleix.myapplication.Entity.Capturar;
import com.example.aleix.myapplication.Entity.Eetakemon;
import com.example.aleix.myapplication.Entity.User;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by aleix on 23/05/2017.
 */

interface Service {

    /* HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();



    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8081/EetakemonGo/")
            .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient)
            .build();*/

    @POST("/EetakemonGo/Eetakemon/Tipo")
    Call<Eetakemon> Eetak(@Body Eetakemon eetak);

    @POST("/EetakemonGo/User/LoginApp")
    Call<User> Login(@Body User usuario);

    @POST("/EetakemonGo/User/RegisterApp")
    Call<User> Register(@Body User usuario);

    @GET("/EetakemonGo/Eetakemon/ListApp")
    Call<List<Eetakemon>> Listar();

    @GET("/EetakemonGo/Eetakemon/AllApp")
    Call<List<Eetakemon>> ListarTusEetakemons();

    @GET("/EetakemonGo/Eetakemon/ListMapa")
    Call<List<Capturar>> Eetakname();

}
