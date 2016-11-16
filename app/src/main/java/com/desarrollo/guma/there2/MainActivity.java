package com.desarrollo.guma.there2;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.CollapsingToolbarLayout;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.SearchView;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.desarrollo.guma.core.SQLiteHelper;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener,SearchView.OnCloseListener
{
    private MenuItem searchItem;
    private android.widget.SearchView searchView;
    private SearchManager searchManager;
    private RecyclerView recycler;
    private LinearLayoutManager lManager;
    private CollapsingToolbarLayout collapser;
    private SimpleAdapter adaptador;

    SQLiteHelper myDB;

    SpecialAdapter adapter2;
    ProgressDialog pdialog;
    List<HashMap<String, String>> fillMaps;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolbar();
        setTitle("Clientes");
        searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        adaptador = new SimpleAdapter(this, objClientes.List(MainActivity.this));
        recycler = (RecyclerView) findViewById(R.id.reciclador);
        recycler.setHasFixedSize(true);
        lManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lManager);
        recycler.setAdapter(adaptador);
        try
        {
            myDB = new SQLiteHelper(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator, this);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        CallCargaDatos(this);
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
                            Inserted = myDB.insertDatos(jsonArray.getJSONObject(i).getString("CLIENTE"),jsonArray.getJSONObject(i).getString("FACTURAS"));
                        }
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                    //pdialog.dismiss();

                    if (Inserted)
                    {
                        //fillMaps.clear();
                        //adapter2.notifyDataSetChanged();
                        pdialog.dismiss();
                        //loadData();
                        Toast.makeText(context, "Actualización completada",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        //adapter2.notifyDataSetChanged();
                        pdialog.dismiss();
                        Error404("Error de Actualizacion de datos PUNTOS");
                    }

                }
                else
                {
                    adapter2.notifyDataSetChanged();
                    Error404("Error de Actualización de Datos de Puntos de Facturas.");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error)
            {
                Context context = getApplicationContext();
                CharSequence text = "Hello toast!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });
    }
    public void Error404(String TipoError)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(TipoError)
                .setNegativeButton("OK",null)
                .create()
                .show();
    }
    private void setToolbar()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        searchItem = menu.findItem(R.id.action_search);
        searchView = (android.widget.SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);
        searchView.requestFocus();
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.action_out:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onClose() { return false; }
    @Override
    public boolean onQueryTextSubmit(String query) { return false; }
    @Override
    public boolean onQueryTextChange(String newText)
    {
        recycler.setAdapter(new SimpleAdapter(this,adaptador.getFilter(newText)));
        return false;
    }
}