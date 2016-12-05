package com.desarrollo.guma.there2;

import android.database.Cursor;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.desarrollo.guma.core.Usuario;
import com.desarrollo.guma.core.Clientes;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Activity_ListadoClientes extends AppCompatActivity
{
    final Usuario tmp = new Usuario();
    final Clientes cli = new Clientes();
    SpecialAdapter adapter;
    ArrayAdapter<String> lvClientesAdapter;
    ListView lvClientes;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__listado_clientes);
        setTitle("Información de CLIENTES");
        lvClientes = (ListView)findViewById(R.id.LvListadoClientes);
        LoadDataClientes();
    }
    private void LoadDataClientes()
    {
        String[] From = new String[]{"CLIENTE",",NOMBRE","DIRECCION","TELEFONO1","ESTADO"};
        int[] to = new int[]{R.id.LblCodigo, R.id.LblNombre, R.id.LblDireccion, R.id.LblTelefono1, R.id.LblEstado};
        List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();
        Cursor res = cli.CursorListadoClientes(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator,
                Activity_ListadoClientes.this);
        if (res.getCount()==0)
        {}
        else
        {
            if (res.moveToFirst())
            {
                do
                {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("CLIENTE",res.getString(0).toString());
                    map.put("NOMBRE",res.getString(1).toString());
                    map.put("DIRECCION",res.getString(2).toString());
                    map.put("TELEFONO1",res.getString(3).toString());
                    map.put("ESTADO",res.getString(4).toString());
                    fillMaps.add(map);
                } while(res.moveToNext());
            }
        }
        adapter = new SpecialAdapter(this,fillMaps,R.layout.listitem_clientes,From,to);
        lvClientes.setAdapter(adapter);
    }
}
