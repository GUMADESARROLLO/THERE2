package com.desarrollo.guma.core;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by marangelo.php on 10/09/2016.
 */
public class Clientes
{
    private String Nombre,Dir,cod;
    public Clientes(String nombre,String Codigo,String Direccion)
    {
        Dir = Direccion;
        cod = Codigo;
        Nombre = nombre;
    }
    public Clientes() { super(); }

    public String getNombre() { return Nombre; }
    public void setNombre(String nombre) { Nombre = nombre; }

    public String getCod() { return cod; }
    public void setCod(String cod) { this.cod = cod; }

    public String getDir() { return Dir; }
    public void setDir(String dir) { Dir = dir; }

    public static void ExecuteSQL(String basedir, Context context, String SQL){
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {
            myDbHelper = new SQLiteHelper(basedir, context);
            myDataBase = myDbHelper.getWritableDatabase();
            myDataBase.execSQL(SQL);

        }
        catch (Exception e) { e.printStackTrace(); }
        finally
        {
            if(myDataBase != null) { myDataBase.close(); }
            if(myDbHelper != null) { myDbHelper.close(); }
        }
    }

    public static List<Clientes> getCliente(String basedir, Context context)
    {
        List<Clientes> lista = new ArrayList<>();
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {
            myDbHelper = new SQLiteHelper(basedir, context);
            myDataBase = myDbHelper.getReadableDatabase();
            Cursor cursor = myDataBase.rawQuery("SELECT * FROM CLIENTES", null);
            if(cursor.getCount() > 0)
            {
                cursor.moveToFirst();
                while(!cursor.isAfterLast())
                {
                    Clientes tmp = new Clientes();
                    tmp.setNombre(cursor.getString(cursor.getColumnIndex("NOMBRE")));
                    tmp.setCod(cursor.getString(cursor.getColumnIndex("CLIENTE")));
                    tmp.setDir(cursor.getString(cursor.getColumnIndex("DIRECCION")));
                    lista.add(tmp);
                    cursor.moveToNext();
                }
            }
        }
        catch (Exception e) { e.printStackTrace(); }
        finally
        {
            if(myDataBase != null) { myDataBase.close(); }
            if(myDbHelper != null) { myDbHelper.close(); }
        }
        return lista;
    }
    public static  List<HashMap<String, String>> getFacturas(String basedir, Context context,String CLIENTE)
    {
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();
        try
        {
            myDbHelper = new SQLiteHelper(basedir, context);
            myDataBase = myDbHelper.getReadableDatabase();
            Cursor cursor = myDataBase.rawQuery("SELECT * FROM DETALLE_FACTURA_PUNTOS WHERE CLIENTE ='"+CLIENTE+"'", null);
            if(cursor.getCount() > 0){
                cursor.moveToFirst();
                while(!cursor.isAfterLast())
                {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("FACTURA",  cursor.getString(cursor.getColumnIndex("FACTURA")));
                    map.put("FECHA",  cursor.getString(cursor.getColumnIndex("FECHA")));
                    map.put("ACUMULADO",  cursor.getString(cursor.getColumnIndex("ACUMULADO")));
                    map.put("DISPONIBLE",  cursor.getString(cursor.getColumnIndex("DISPONIBLE")));
                    map.put("VENCE",  cursor.getString(cursor.getColumnIndex("FECHA")));
                    fillMaps.add(map);
                    cursor.moveToNext();
                }
            }else {
                Log.d("getFACTURA", "getFacturas: No ENCONTRO NADA" );
            }
        }
        catch (Exception e) { e.printStackTrace(); }
        finally
        {
            if(myDataBase != null) { myDataBase.close(); }
            if(myDbHelper != null) { myDbHelper.close(); }
        }
        return fillMaps;
    }
}