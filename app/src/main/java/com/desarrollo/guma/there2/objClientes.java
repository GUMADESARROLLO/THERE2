package com.desarrollo.guma.there2;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.desarrollo.guma.core.Clientes;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class objClientes
{
    private static final String TAG = "objClientes";

    public static List<Cliente> List(Context cntx)
    {
        List<Cliente> items = new ArrayList<>();

        for(Clientes obj : Clientes.getCliente(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator, cntx))
        {
           //Log.d(TAG, "List: " + obj.getNombre());
            items.add(new Cliente(obj.getNombre(),obj.getCod(),obj.getDir()));
        }
        return new ArrayList<>(items);
    }
}