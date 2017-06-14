package com.example.aleix.myapplication.Entity;


public class Capturar {
    private Eetakemon eetakemon;
    public Location latLong;

    public Capturar(){}

    public Capturar(Eetakemon eetakemon, Location location)
    {
        this.eetakemon  = eetakemon;
        this.latLong = location;
    }

    public Eetakemon getEetakemon() {
        return eetakemon;
    }

    public void setEetakemon(Eetakemon eetakemon) {
        this.eetakemon = eetakemon;
    }

    public Location getLatLong() {
        return latLong;
    }

    public void setLatLong(Location latLong) {
        this.latLong = latLong;
    }
}