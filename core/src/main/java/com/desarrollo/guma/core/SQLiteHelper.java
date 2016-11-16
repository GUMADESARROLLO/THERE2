package com.desarrollo.guma.core;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author gerzon
 */
public final class SQLiteHelper extends SQLiteOpenHelper
{
    
    public static final String DATABASE = "there.db";
    private static final int flag = SQLiteDatabase.NO_LOCALIZED_COLLATORS;//database version
    //private static final int flag = 1;
    private final String path;
    public SQLiteHelper(String path, Context context) throws IOException
    {
        super(context, path + DATABASE, null, flag);
        this.path = path;
        if (!checkDataBase())
        {
            InputStream myInput = context.getAssets().open(DATABASE);
            OutputStream myOutput = new FileOutputStream(new File(path, DATABASE));
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) { myOutput.write(buffer, 0, length); }
            myOutput.flush();
            myOutput.close();
            myInput.close();
        }
    }
    private boolean checkDataBase()
        {
        SQLiteDatabase checkDB;
        try
        {
            Log.d("OKOKOKOKOKO",path.concat(DATABASE));
            checkDB = SQLiteDatabase.openDatabase(path.concat(DATABASE), null, flag);
        }
        catch (Exception e) { checkDB = null; }
        boolean ok = checkDB != null;
        if (ok) { checkDB.close(); }
        return ok;
    }
    public boolean insertDatos(String CLIENTE, String DATOS)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String Facturas[]= DATOS.split("xxx");

        ContentValues contentValues = new ContentValues();
        contentValues.put("CLIENTE", CLIENTE);
        contentValues.put("FACTURA", Facturas[0]);
        contentValues.put("FECHA", Facturas[1]);
        contentValues.put("ACUMULADO", Facturas[2]);
        contentValues.put("DISPONIBLE", Facturas[3]);
        contentValues.put("VENCE", Facturas[4]);
        long result = db.insert("DETALLE_FACTURA_PUNTOS",null,contentValues);
        if (result == -1) { return false; }
        else { return  true; }
    }
    public Cursor GetFacturasCliente(String Id)//Obtener los Datos del Cliente Deseado
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "SELECT * FROM DETALLE_FACTURA_PUNTOS WHERE CLIENTE= '"+Id.trim()+"'  "; //+ '"+Id.trim()+"';
        Cursor res = db.rawQuery(Query, null);
        return res;
    }
    @Override
    public void onCreate(SQLiteDatabase db) { }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }
}

