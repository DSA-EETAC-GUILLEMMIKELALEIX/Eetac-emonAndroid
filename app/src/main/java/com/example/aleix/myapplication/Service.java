package com.example.aleix.myapplication;

import com.example.aleix.myapplication.Entity.Capturar;
import com.example.aleix.myapplication.Entity.Captured;
import com.example.aleix.myapplication.Entity.Eetakemon;
import com.example.aleix.myapplication.Entity.Question;
import com.example.aleix.myapplication.Entity.Relation;
import com.example.aleix.myapplication.Entity.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

interface Service {

    @POST("/EetakemonGo/Eetakemon/Tipo")
    Call<Eetakemon> Eetak(@Body Eetakemon eetak);

    @POST("/EetakemonGo/User/Login")
    Call<String> Login(@Body User usuario);

    @POST("/EetakemonGo/User/Register")
    Call<String> Register(@Body User usuario);

    @GET("/EetakemonGo/Eetakemon/all")
    Call<List<Eetakemon>> Listar(@Header("Authorization") String authHeader);

    @GET("/EetakemonGo/Relation/Captured/{id}")
    Call<List<Captured>> ListarTusEetakemons(@Header("Authorization") String authHeader, @Path("id") int id);

    @GET("/EetakemonGo/Eetakemon/ListMapa")
    Call<List<Capturar>> Eetakname();

    @POST("/EetakemonGo/Eetakemon/Question")
    Call<Question> Pregunta(@Body Eetakemon eetak);

    @POST("/EetakemonGo/Relation/Capturado")
    Call<Relation> Capturado(@Body Relation capt);


}
