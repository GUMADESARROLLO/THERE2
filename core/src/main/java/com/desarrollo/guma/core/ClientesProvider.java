package com.desarrollo.guma.core;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.IllegalFormatException;

/**
 * Created by luis.perez on 25/11/2016.
 */

public class ClientesProvider extends ContentProvider
{
    public static final int CLIENTES_LIST = 1;
    public static final int CLIENTES_ID = 2;
    private static final UriMatcher sUriMatcher;
    static
    {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(ClientesContract.AUTHORITY,"clientes",CLIENTES_LIST);
        sUriMatcher.addURI(ClientesContract.AUTHORITY,"clientes/#",CLIENTES_ID);
    }

    //private ClientesDbHelper mDbHelper;
    private ClientesDbHelper  mDbHelper;

    public ClientesProvider(){}

    @Override
    public boolean onCreate()
    {
        mDbHelper = ClientesDbHelper.getInstance(getContext());
        return true;
    }

    //@Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
    {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor;
        switch (sUriMatcher.match(uri))
        {
            case CLIENTES_LIST:
                if (sortOrder == null || TextUtils.isEmpty(sortOrder))
                    sortOrder = ClientesContract.ClientesColumns.DEFAULT_SORT_ORDER;
                break;
            case CLIENTES_ID:
                if (selection == null)
                    selection = "";
                selection = selection + " _ID = " + uri.getLastPathSegment();
                break;
            default:
                throw new IllegalArgumentException("unsupported URI: " + uri);
        }
        cursor= db.query(DatabaseContract.Clientes.TABLE_NAME,projection, selection, selectionArgs, null, null, sortOrder);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri)
    {
        switch (sUriMatcher.match(uri))
        {
            case CLIENTES_LIST:
                return ClientesContract.URI_TYPE_CLIENTE_DIR;
            case CLIENTES_ID:
                return ClientesContract.URI_TYPE_CLIENTE_ITEM;
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values)
    {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.insert(DatabaseContract.Clientes.TABLE_NAME, null, values);
        //notificar del cambio a los observadores
        getContext().getContentResolver().notifyChange(uri, null);
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs)
    {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int rows = 0;
        switch (sUriMatcher.match(uri))
        {
            case CLIENTES_LIST:
                rows = db.delete(DatabaseContract.Clientes.TABLE_NAME, null, null);
                break;
            case CLIENTES_ID:
                rows = db.delete(DatabaseContract.Clientes.TABLE_NAME, selection,selectionArgs);
                break;
        }
        return rows;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs)
    {
        return 0;
    }
}
