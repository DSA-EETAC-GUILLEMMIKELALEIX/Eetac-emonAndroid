package com.example.aleix.myapplication.Entity;


public class Captured {
    String name;
    int level;

    public Captured(String name, int level) {
        this.name=name;
        this.level=level;
    }

    public Captured(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
