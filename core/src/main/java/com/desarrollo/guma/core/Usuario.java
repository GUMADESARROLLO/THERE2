package com.desarrollo.guma.core;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

    public Cursor InfoUsuario(String basedir, Context context)
    {
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        Cursor res=null;
        try
        {
            myDbHelper = new SQLiteHelper(basedir, context);
            myDataBase = myDbHelper.getReadableDatabase();
            String Query = "SELECT * FROM Usuarios";
            res = myDataBase.rawQuery(Query ,null);
        }
        catch (Exception e) { e.printStackTrace(); }
        return res;
    }

    public static String encriptarEnMD5(String stringAEncriptar)
    {
        final char[] CONSTS_HEX = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
        try
        {
            MessageDigest msgd = MessageDigest.getInstance("MD5");
            byte[] bytes = msgd.digest(stringAEncriptar.getBytes());
            StringBuilder strbCadenaMD5 = new StringBuilder(2 * bytes.length);
            for (int i = 0; i < bytes.length; i++)
            {
                int bajo = (int)(bytes[i] & 0x0f);
                int alto = (int)((bytes[i] & 0xf0) >> 4);
                strbCadenaMD5.append(CONSTS_HEX[alto]);
                strbCadenaMD5.append(CONSTS_HEX[bajo]);
            }
            return strbCadenaMD5.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }    }

    public static boolean leerDB(String Usuario, String PASSWORD,String basedir, Context context)
    {
        boolean Correcto=false;
        SQLiteDatabase myDataBase = null;
        SQLiteHelper myDbHelper = null;
        try
        {
            String xxx = "";
            xxx = encriptarEnMD5(PASSWORD);
            myDbHelper = new SQLiteHelper(basedir, context);
            myDataBase = myDbHelper.getReadableDatabase();
            Cursor cursor = myDataBase.rawQuery("select * from Usuarios where Credencial='"+Usuario+"' and PASSWORD='"+xxx+"' ", null);
            if(cursor.getCount() > 0)
            {
                Correcto = true;
                cursor.moveToFirst();

                while(!cursor.isAfterLast())
                {

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
        return Correcto;
    }
}
