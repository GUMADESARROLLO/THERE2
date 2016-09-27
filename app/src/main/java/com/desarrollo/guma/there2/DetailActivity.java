package com.desarrollo.guma.there2;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

public class DetailActivity extends AppCompatActivity {
    private static TextView codcls,dircls;

    private static final String EXTRA_COD = "com.desarrollo.guma.there2.cod";
    private static final String EXTRA_NAME = "com.desarrollo.guma.there2.name";
    private static final String EXTRA_DIR = "com.desarrollo.guma.there2.DIR";
    private static final String EXTRA_DRAWABLE = "com.desarrollo.guma.there2.drawable";

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

        setToolbar();
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        codcls = (TextView) findViewById(R.id.codCliente);
        dircls = (TextView) findViewById(R.id.dirCliente);

        Intent i = getIntent();
        String name = i.getStringExtra(EXTRA_NAME);
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
                        showSnackBar("Opción de Chatear");
                    }
                }        );


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