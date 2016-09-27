package com.desarrollo.guma.there2;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.desarrollo.guma.core.Clientes;
import com.desarrollo.guma.core.Usuario;

public class DetailActivity extends AppCompatActivity {
    private static TextView codcls,dircls,latitud,longitud;


    private static final String EXTRA_COD = "com.desarrollo.guma.there2.cod";
    private static final String EXTRA_NAME = "com.desarrollo.guma.there2.name";

    private static final String EXTRA_DIR = "com.desarrollo.guma.there2.DIR";
    private static final String EXTRA_DRAWABLE = "com.desarrollo.guma.there2.drawable";
    AlertDialog alert = null;
    LocationManager locationManager;
    Location location;
    LocationListener locationListener;

    public static void createInstance(Activity activity, Cliente title) {
        Intent intent = getLaunchIntent(activity, title);
        activity.startActivity(intent);
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

        codcls = (TextView) findViewById(R.id.codCliente);
        dircls = (TextView) findViewById(R.id.dirCliente);
        latitud = (TextView) findViewById(R.id.campo_lati);
        longitud = (TextView) findViewById(R.id.campo_long);

        Intent i = getIntent();
        final String name = i.getStringExtra(EXTRA_NAME);
        codcls.setText(i.getStringExtra(EXTRA_COD));
        dircls.setText(i.getStringExtra(EXTRA_DIR));
        int idDrawable = i.getIntExtra(EXTRA_DRAWABLE, -1);



        CollapsingToolbarLayout collapser = (CollapsingToolbarLayout) findViewById(R.id.collapser);
        collapser.setTitle(name);
        loadImageParallax(idDrawable);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showSnackBar("CODVENDEDOR: " + tmp.getCred() + "NOMBRE VENDEDOR: " + tmp.getNombre() + "CLIENTE: " + codcls.getText() + " NOMBRE: " + name + "LATI: " + latitud.getText() + "longi: "+ longitud.getText());

                    }
                }        );


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if ( !locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            AlertNoGps();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            } else {
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
        } else {
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }

        MostrarLocalizacion(location);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                MostrarLocalizacion(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);


    }
    public void MostrarLocalizacion(Location loc){
        if (loc!=null){
            latitud.setText(String.valueOf(loc.getLatitude()));
            longitud.setText(String.valueOf(loc.getLongitude()));
        }else{

            showSnackBar("El Dispositivo no sea Triangulado");
        }



    }
    private void AlertNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("El sistema GPS esta desactivado, ¿Desea activarlo?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        finish();
                    }
                });
        alert = builder.create();
        alert.show();
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