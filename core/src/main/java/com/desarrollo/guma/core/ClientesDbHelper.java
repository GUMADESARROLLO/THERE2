package com.desarrollo.guma.core;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by luis.perez on 25/11/2016.
 */

public class ClientesDbHelper extends SQLiteOpenHelper
{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "there.db";

    private static ClientesDbHelper cliInstance;

    private ClientesDbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public static synchronized ClientesDbHelper getInstance(Context context)
    {
        if (cliInstance==null)
        {
            cliInstance = new ClientesDbHelper(context.getApplicationContext());
        }
        return cliInstance;
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(DatabaseContract.Clientes.SQL_CREATE_CLIENTES_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(DatabaseContract.Clientes.SQL_DELETE_CLIENTES);
        onCreate(db);
    }
}

