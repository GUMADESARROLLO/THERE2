package com.desarrollo.guma.there2;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
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
import com.desarrollo.guma.core.Usuario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private static final String EXTRA_COD = "com.desarrollo.guma.there2.cod";
    private static final String EXTRA_NAME = "com.desarrollo.guma.there2.name";

    private static final String EXTRA_DIR = "com.desarrollo.guma.there2.DIR";
    private static final String EXTRA_DRAWABLE = "com.desarrollo.guma.there2.drawable";

    ListView lst;
    SpecialAdapter adapter;
    List<HashMap<String, String>> fillMaps;

    public static void createInstance(Activity activity, Cliente title) {
        Intent intent = getLaunchIntent(activity, title);
        activity.startActivity(intent);

    }
    private void loadData(){
        String[] from = new String[] {"cmp1", "cmp2", "cmp3","cmp4","cmp5"};
        int[] to = new int[] { R.id.cl1, R.id.cl2, R.id.cl3,R.id.cl4,R.id.cl5};

        fillMaps = new ArrayList<HashMap<String, String>>();


        for (int i=0; i < 10; i++){
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("cmp1", String.valueOf(i));
            map.put("cmp2", "0");
            map.put("cmp3", "0");
            map.put("cmp4", "0");
            map.put("cmp5", "0");
            fillMaps.add(map);

        }



        adapter = new SpecialAdapter(DetailActivity.this, fillMaps, R.layout.tabla_facturas_puntos, from, to);
        lst.setAdapter(adapter);
    }

    public static Intent getLaunchIntent(Context context, Cliente girl) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(EXTRA_COD, girl.getCod());
        intent.putExtra(EXTRA_NAME, girl.getName());
        intent.putExtra(EXTRA_DIR, girl.getDir());

        intent.putExtra(EXTRA_DRAWABLE, girl.getIdDrawable());
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        final Usuario tmp = new Usuario();
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

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
    private void loadImageParallax(int id) {
        ImageView image = (ImageView) findViewById(R.id.image_paralax);
        Glide.with(this)
                .load(id)
                .centerCrop()
                .into(image);
    }
    private void showSnackBar(String msg) {
        Snackbar
                .make(findViewById(R.id.coordinator), msg, Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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