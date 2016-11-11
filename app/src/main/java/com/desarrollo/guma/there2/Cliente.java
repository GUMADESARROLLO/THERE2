package com.desarrollo.guma.there2;



public class Cliente {

    private String cod,name,dir;
    private int idDrawable;

    public Cliente(String name, String Codigo, String Direccion) {
        this.cod  = Codigo;
        this.name = name;
        this.dir  = Direccion;
        this.idDrawable = R.drawable.logouma;
    }

    public String getName() {
        return name;
    }
    public String getCod() {
        return cod;
    }
    public String getDir() {
        return dir;
    }
    public int getIdDrawable() {
        return idDrawable;
    }



}