package com.desarrollo.guma.core;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Usuario {
    private String Cred;
    private String nombre;
    private String Contrasenna;

    public Usuario() {
        super();
    }

    public Usuario(String Credencial,String nombre, String contrasenna) {
        this.nombre = nombre;
        Contrasenna = contrasenna;
        Cred = Credencial;
    }

    public String getCred() {
        return Cred;
    }

    public void setCred(String cred) {
        Cred = cred;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
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
                int i=0;
                while(!cursor.isAfterLast()) {
                    /*Clientes tmp = new Clientes();
                    tmp.setIdVendedor(cursor.getString(cursor.getColumnIndex("IdVendedor")));
                    tmp.setNameVendedor(cursor.getString(cursor.getColumnIndex("NombreUsuario")));*/
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
        return Correcto;
    }
}
