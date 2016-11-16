package com.desarrollo.guma.there2;
import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.desarrollo.guma.core.Clientes;
import com.desarrollo.guma.core.SQLiteHelper;
import com.desarrollo.guma.core.Usuario;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.desarrollo.guma.core.*;

public class DetailActivity extends AppCompatActivity
{
    private static final String EXTRA_COD = "com.desarrollo.guma.there2.cod";
    private static final String EXTRA_NAME = "com.desarrollo.guma.there2.name";

    SQLiteHelper myDB;

    SpecialAdapter adapter2;
    ProgressDialog pdialog;

    private static final String EXTRA_DIR = "com.desarrollo.guma.there2.DIR";
    private static final String EXTRA_DRAWABLE = "com.desarrollo.guma.there2.drawable";
    ListView lst;
    SpecialAdapter adapter;
    List<HashMap<String, String>> fillMaps;
    public static void createInstance(Activity activity, Cliente title)
    {
        Intent intent = getLaunchIntent(activity, title);
        activity.startActivity(intent);
    }
    public void Error404(String TipoError)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(TipoError)
                .setNegativeButton("OK",null)
                .create()
                .show();
    }

    public void CallCargaDatos(final Context context)
    {
        AsyncHttpClient Cnx = new AsyncHttpClient();
        RequestParams parametros = new RequestParams();
        pdialog = ProgressDialog.show(this,"","Procesando. Por Favor Espere ...",true);
        Cnx.post(ClssURL.getURL_Asyn(), parametros, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody)
            {
                boolean Inserted = false;
                if (statusCode==200)
                {
                    SQLiteDatabase db = myDB.getWritableDatabase();
                    db.execSQL("DELETE FROM DETALLE_FACTURA_PUNTOS");
                    try
                    {
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        for (int i = 0 ; i<jsonArray.length();i++)
                        {
                            Inserted = myDB.insertDatos(jsonArray.getJSONObject(i).getString("CLIENTE"),jsonArray.getJSONObject(i).getString("DATOS"));
                        }
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                    /*
                    if (Inserted)
                    {
                        fillMaps.clear();
                        adapter2.notifyDataSetChanged();
                        pdialog.dismiss();
                        loadData();
                        Toast.makeText(context, "Actualización completada",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        adapter2.notifyDataSetChanged();
                        pdialog.dismiss();
                        Error404("Error de Actualizacion de datos PUNTOS");
                    }
                    */
                }
                else
                {
                    adapter2.notifyDataSetChanged();
                    Error404("Error de Actualización de Datos de Puntos de Facturas.");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void loadData()
    {
        //String[] from = new String[] {"cmp1", "cmp2", "cmp3","cmp4","cmp5"};
        String[] from = new String[] {"FACTURA", "FECHA", "ACUMULADO","DISPONIBLE","VENCE"};
        int[] to = new int[] { R.id.cl1, R.id.cl2, R.id.cl3,R.id.cl4,R.id.cl5};
        fillMaps = new ArrayList<HashMap<String, String>>();
        Cursor res = myDB.GetFacturasCliente("001-040983-0013X");
        if (res.getCount()==0)
        {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("FACTURA", "");
            map.put("FECHA", "0");
            map.put("ACUMULADO", "0");
            map.put("DISPONIBLE", "0");
            map.put("VENCE", "0");
            fillMaps.add(map);
        }
        else
        {
            if (res.moveToFirst())
            {
                do
                {
                    //for (int i=0; i < 10; i++)
                    //{
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("FACTURA",  res.getString(0));
                        map.put("FECHA",  res.getString(1));
                        map.put("ACUMULADO",  res.getString(2));
                        map.put("DISPONIBLE",  res.getString(3));
                        map.put("VENCE",  res.getString(4));
                        fillMaps.add(map);
                    //}
                } while(res.moveToNext());
            }
        }
        adapter = new SpecialAdapter(DetailActivity.this, fillMaps, R.layout.tabla_facturas_puntos, from, to);
        lst.setAdapter(adapter);
    }
    public static Intent getLaunchIntent(Context context, Cliente girl)
    {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(EXTRA_COD, girl.getCod());
        intent.putExtra(EXTRA_NAME, girl.getName());
        intent.putExtra(EXTRA_DIR, girl.getDir());
        intent.putExtra(EXTRA_DRAWABLE, girl.getIdDrawable());
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        final Usuario tmp = new Usuario();

        //myDB = new SQLiteHelper(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator, this);
        try
        {
            myDB = new SQLiteHelper(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator, this);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        //Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator, cntx
        //CallCargaDatos(this);
        setToolbar();
        if (getSupportActionBar() != null)
           getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        final String name = i.getStringExtra(EXTRA_NAME);
        int idDrawable = i.getIntExtra(EXTRA_DRAWABLE, -1);
        //int idDrawable =  R.drawable.welcome;
        lst = (ListView) findViewById(R.id.listview_DRecibo);
        CollapsingToolbarLayout collapser = (CollapsingToolbarLayout) findViewById(R.id.collapser);
        collapser.setTitle(name);
        loadImageParallax(idDrawable);
        loadData();
       // FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    }
    private void setToolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
    private void loadImageParallax(int id)
    {
        ImageView image = (ImageView) findViewById(R.id.image_paralax);
        Glide.with(this)
                .load(id)
                .centerCrop()
                .into(image);
    }
    private void showSnackBar(String msg)
    {
        Snackbar
                .make(findViewById(R.id.coordinator), msg, Snackbar.LENGTH_LONG)
                .show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == 16908332){
            finish();
        }
        switch (id) {
            case R.id.action_settings:
                showSnackBar("Se abren los ajustes");
                return true;
            case R.id.action_add:
                showSnackBar("Añadir a contactos");
                return true;
            case R.id.action_favorite:
                showSnackBar("Añadir a favoritos");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}