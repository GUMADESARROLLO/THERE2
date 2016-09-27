package com.desarrollo.guma.there2;



public class Cliente {

    private String name;
    private String cod;
    private String dir;
    private int idDrawable;

    public Cliente(String name, String Codigo, String Direccion) {
        this.cod  = Codigo;
        this.name = name;
        this.dir  = Direccion;
        this.idDrawable = R.drawable.fondo;
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