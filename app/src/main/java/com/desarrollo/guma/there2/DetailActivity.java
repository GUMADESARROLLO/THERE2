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
import android.icu.text.IDNA;
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
import android.util.Log;
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
    private static final String EXTRA_DIR = "com.desarrollo.guma.there2.DIR";
    private static final String EXTRA_DRAWABLE = "com.desarrollo.guma.there2.drawable";
    ListView lst;
    TextView txtDisp,txtAcumu;
    public static void createInstance(Activity activity, Cliente title)
    {
        Intent intent = getLaunchIntent(activity, title);
        activity.startActivity(intent);

    }
    private void loadData(String CD)
    {
        String[] from = new String[] {"FACTURA", "FECHA", "ACUMULADO","DISPONIBLE","VENCE"};
        int[] to = new int[] { R.id.cl1, R.id.cl2, R.id.cl3,R.id.cl4,R.id.cl5};
        List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();
        fillMaps = Clientes.getFacturas(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator, this,CD);

        int ACUMULADOR=0,DISPONIBLE=0;

        for (int i=0; i<fillMaps.size(); i++){
            ACUMULADOR += Integer.parseInt(fillMaps.get(i).get("ACUMULADO"));
            DISPONIBLE += Integer.parseInt(fillMaps.get(i).get("DISPONIBLE"));
        }
        txtDisp.setText( "DISPONIBLE: " + String.valueOf(DISPONIBLE) + " PTS" );
        txtAcumu.setText( "ACUMULADO: " + String.valueOf(ACUMULADOR) + " PTS" );

        SpecialAdapter adapter = new SpecialAdapter(DetailActivity.this, fillMaps, R.layout.tabla_facturas_puntos, from, to);

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

        setToolbar();
        if (getSupportActionBar() != null)
           getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        final String name = i.getStringExtra(EXTRA_NAME);
        final String CODE = i.getStringExtra(EXTRA_COD);
        int idDrawable = i.getIntExtra(EXTRA_DRAWABLE, -1);
        lst = (ListView) findViewById(R.id.listview_DRecibo);
        CollapsingToolbarLayout collapser = (CollapsingToolbarLayout) findViewById(R.id.collapser);
        collapser.setTitle(name);
        loadImageParallax(R.drawable.portada);
        txtAcumu = (TextView) findViewById(R.id.txtAcumulado);
        txtDisp = (TextView) findViewById(R.id.txtDisponible);
        loadData(CODE);

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