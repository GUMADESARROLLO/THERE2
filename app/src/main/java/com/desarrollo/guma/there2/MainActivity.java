package com.desarrollo.guma.there2;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.SearchView;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import com.desarrollo.guma.core.Clientes;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;

import java.io.File;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener,SearchView.OnCloseListener
{
    private static final String TAG = "MainActivity";
    private MenuItem searchItem;
    private android.widget.SearchView searchView;
    private SearchManager searchManager;
    private RecyclerView recycler;
    private LinearLayoutManager lManager;
    private SimpleAdapter adaptador;


    SpecialAdapter adapter2;
    ProgressDialog pdialog;

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

        getClientes(this);

    }
    public void getClientes(final Context cxnt){
        AsyncHttpClient servicio = new AsyncHttpClient();
        RequestParams parametros = new RequestParams();
        parametros.put("V",11);
        servicio.post(ClssURL.getURL_CLENTES(), parametros, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode==200){

                    try{

                        JSONArray jsonArray = new JSONArray(new String(responseBody));

                        //Clientes.ExecuteSQL(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator, cxnt,"DELETE FROM CLIENTES;");
                        Clientes.ExecuteSQL(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator, cxnt,jsonArray.getJSONObject(0).getString("CLIENTES"));


                        //Clientes.ExecuteSQL(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator, cxnt,"DELETE FROM DETALLE_FACTURA_PUNTOS;");
                        Clientes.ExecuteSQL(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator, cxnt,jsonArray.getJSONObject(0).getString("FACTURAS"));


                        pdialog.dismiss();
                        Toast.makeText(cxnt, "Todo Bien", Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }else{
                    pdialog.dismiss();
                    adapter2.notifyDataSetChanged();
                    Error404("Error de Actualización de Datos de Puntos de Facturas.");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Error404("onFailure");
            }
        });
    }

    public void Error404(String TipoError){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(TipoError)
                .setNegativeButton("OK",null)
                .create()
                .show();
    }
    private void setToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
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
    public boolean onOptionsItemSelected(MenuItem item){
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