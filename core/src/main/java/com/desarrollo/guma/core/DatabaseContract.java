package com.desarrollo.guma.core;

import android.provider.BaseColumns;
import android.provider.ContactsContract;

/**
 * Created by luis.perez on 25/11/2016.
 */

public class DatabaseContract
{
    public DatabaseContract(){}
    public static abstract class Clientes implements BaseColumns
    {
        //BaseColumns nso entrega las constantes \_ID y \_COUNT
        public static final String TABLE_NAME = "CLIENTES";
        public static final String COLUMN_NAME_CLIENTES_CLIENTE = "CLIENTE";
        public static final String COLUMN_NAME_CLIENTES_NOMBRE = "NOMBRE";
        public static final String COLUMN_NAME_CLIENTES_DIRECCION = "DIRECCION";
        public static final String COLUMN_NAME_CLIENTES_TELEFONO1 = "TEELFONO1";
        public static final String COLUMN_NAME_CLIENTES_ESTADO = "ESTADO";

        public static final String TEXT_TYPE = " TEXT";
        public static final String INTEGER_TYPE = " INTEGER";
        public static final String COMMA_SEP = ",";

        public static final String SQL_CREATE_CLIENTES_TABLE =
                "CREATE TABLE "+ Clientes.TABLE_NAME +" (" +
                Clientes._ID + " INTEGER PRIMARY KEY, " +
                Clientes.COLUMN_NAME_CLIENTES_CLIENTE + TEXT_TYPE + COMMA_SEP +
                Clientes.COLUMN_NAME_CLIENTES_NOMBRE + TEXT_TYPE + COMMA_SEP +
                Clientes.COLUMN_NAME_CLIENTES_DIRECCION + TEXT_TYPE + COMMA_SEP +
                Clientes.COLUMN_NAME_CLIENTES_TELEFONO1 + TEXT_TYPE + COMMA_SEP +
                Clientes.COLUMN_NAME_CLIENTES_ESTADO + TEXT_TYPE + " )";

        public static final String SQL_DELETE_CLIENTES =
                "DROP TABLE IF EXISTS " + Clientes.TABLE_NAME;
    }
}
