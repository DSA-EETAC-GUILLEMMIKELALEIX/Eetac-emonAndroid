package com.example.aleix.myapplication.Entity;


import com.google.android.gms.maps.model.LatLng;

public class Capturar {
    private Eetakemon eetakemon;
    public Location LatLong;

    public Capturar(){}

    public Capturar(Eetakemon eetakemon, Location location)
    {
        this.eetakemon  = eetakemon;
        this.LatLong= location;
    }

    public Eetakemon getEetakemon() {
        return eetakemon;
    }

    public void setEetakemon(Eetakemon eetakemon) {
        this.eetakemon = eetakemon;
    }

    public Location getLatLong() {
        return LatLong;
    }

    public void setLatLong(Location latLong) {
        LatLong = latLong;
    }
}