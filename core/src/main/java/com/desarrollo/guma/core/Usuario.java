package com.desarrollo.guma.core;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Usuario {
    private String nombre;
    private String Contrasenna;

    public Usuario() {
        super();
    }

    public Usuario(String nombre, String contrasenna) {
        this.nombre = nombre;
        Contrasenna = contrasenna;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasenna() {
        return Contrasenna;
    }

    public void setContrasenna(String contrasenna) {
        Contrasenna = contrasenna;
    }
    public static boolean leerDB(String Usuario, String PASSWORD,String basedir, Context context){
        boolean Correcto=false;
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try {
            myDbHelper = new SQLiteHelper(basedir, context);
            myDataBase = myDbHelper.getReadableDatabase();
            Cursor cursor = myDataBase.rawQuery("select * from Usuarios where IdVendedor='"+Usuario+"' and PASSWORD='"+PASSWORD+"' ", null);
            if(cursor.getCount() > 0) {
                Correcto = true;
                cursor.moveToFirst();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(myDataBase != null) {
                myDataBase.close();
            }
            if(myDbHelper != null) {
                myDbHelper.close();
            }
        }
        return Correcto;
    }
}
