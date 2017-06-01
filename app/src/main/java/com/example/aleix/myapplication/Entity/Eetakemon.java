package com.example.aleix.myapplication.Entity;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aleix on 22/05/2017.
 */

// Clase que define y permite crear objetos Eetakemon
public class Eetakemon implements Serializable{
    private int id;
    private String nombre;
    private int nivel;
    private String tipo;
    private String foto;

    public Eetakemon(){}

    public Eetakemon(String nombre, String tipo, /*Image foto,*/ int level)
    {
        this.nombre = nombre;
        this.tipo = tipo;
        this.foto = "foto";
        this.nivel=level;
    }

    public Eetakemon(String nombre, int level)
    {
        this.nombre = nombre;
        this.nivel=level;
        this.tipo="tipo";
    }

    public Eetakemon(String tipo)
    {
        this.tipo=tipo;
    }

    public int getId() {
        return id;
    }

    //Obtener nombre
    public String getNombre() {
        return nombre;
    }
    //Obtener Tipo
    public String getTipo() {
        return tipo;
    }
    //Obtener foto

    //Obtener Nivel
    public int getNivel() {
        return nivel;
    }

    //definir Id
    public void setId(int id) {
        this.id = id;
    }
    //definir Nombre
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    //definir Tipo
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    //definir Foto

    //definir Nivel
    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Override
    public String toString() {
        return "Eetakemon [Id = "+id+", nombre=" + nombre + ", tipo=" + tipo + /*", foto=" + foto +*/ ", nivel=" + nivel+"]";
    }

    private static String loadJsonFromAsset(String filename, Context context) {
        String json = null;

        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }
        catch (java.io.IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return json;
    }

}