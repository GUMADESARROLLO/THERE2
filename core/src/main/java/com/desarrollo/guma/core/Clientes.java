package com.desarrollo.guma.core;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marangelo.php on 10/09/2016.
 */
public class Clientes {
    private String Nombre,Dir,cod;

    public Clientes(String nombre,String Codigo,String Direccion) {
        Dir = Direccion;
        cod = Codigo;
        Nombre = nombre;
    }


    public Clientes() {
        super();
    }

    public String getNombre() {
        return Nombre;
    }
    public void setNombre(String nombre) {
        Nombre = nombre;
    }
    public String getCod() {return cod;}
    public void setCod(String cod) {
        this.cod = cod;
    }
    public String getDir() {return Dir;}
    public void setDir(String dir) {Dir = dir;}

    public static List<Clientes> getCliente(String basedir, Context context) {
        List<Clientes> lista = new ArrayList<>();
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;

        try {
            myDbHelper = new SQLiteHelper(basedir, context);
            myDataBase = myDbHelper.getReadableDatabase();
            Cursor cursor = myDataBase.rawQuery("SELECT * FROM CLIENTES", null);

            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                while(!cursor.isAfterLast()) {
                    Clientes tmp = new Clientes();
                    tmp.setNombre(cursor.getString(cursor.getColumnIndex("NOMBRE")));
                    tmp.setCod(cursor.getString(cursor.getColumnIndex("CLIENTE")));
                    tmp.setDir(cursor.getString(cursor.getColumnIndex("DIRECCION")));
                    lista.add(tmp);
                    cursor.moveToNext();
                }
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
        return lista;
    }
}
