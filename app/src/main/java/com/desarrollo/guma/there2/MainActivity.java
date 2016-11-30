package com.desarrollo.guma.there2;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
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


import com.desarrollo.guma.core.Clientes;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;


import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener,SearchView.OnCloseListener
{
    private MenuItem searchItem;
    private android.widget.SearchView searchView;
    private SearchManager searchManager;
    private RecyclerView recycler;
    private SimpleAdapter adaptador=null;
    private boolean checked;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    String Vendedor;
    ProgressDialog pdialog;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolbar();

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Vendedor = preferences.getString("usuario","11");
        setTitle("LISTADO DE CLIENTE");

        editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        checked = preferences.getBoolean("pref",false);
        searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        setAdaptador();
        getClientes(this,Vendedor);
    }
    public String Datetime(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String Date = (dateFormat.format(cal.getTime()));
        return Date;
    }

    @Override
    protected void onStop() {
        //Error404("onStop [ " + Vendedor + " ]");
        super.onStop();
    }

    @Override
    protected void onRestart() {
        //Error404("onRestart [ " + Vendedor + " ]");
        super.onRestart();
    }

    public void getClientes(final Context cxnt, String User){
        AsyncHttpClient servicio = new AsyncHttpClient();
        RequestParams parametros = new RequestParams();
        parametros.put("V",User);
        servicio.get(ClssURL.getURL_CLENTES(), parametros,new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                pdialog = ProgressDialog.show(cxnt, "","Procesando. Porfavor Espere...", true);
            }

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                if (statusCode==200){

                    Clientes.ExecuteSQL(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator, cxnt,"DELETE FROM CLIENTES;");
                    Clientes.ExecuteSQL(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator, cxnt,"DELETE FROM DETALLE_FACTURA_PUNTOS;");

                    try{
                        JSONArray jsonArray = new JSONArray(new String(responseBody));
                        Clientes.ExecuteSQL(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator, cxnt,jsonArray.getJSONObject(0).getString("CLIENTES"));
                        Clientes.ExecuteSQL(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator, cxnt,jsonArray.getJSONObject(0).getString("FACTURAS"));
                        pdialog.dismiss();
                        showSnackBar("Información Actualizada....");
                        recycler.setAdapter(new SimpleAdapter(cxnt, objClientes.List(MainActivity.this)));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    pdialog.dismiss();
                    Error404("No se Obtuvo respuesta del Servidor.");
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                pdialog.dismiss();
                Error404("Algo Salio Mal");

            }

            @Override
            public void onRetry(int retryNo) {
                pdialog.dismiss();
                //Error404("onRetry");
            }
        });

    }

    public void setAdaptador(){
        recycler = (RecyclerView) findViewById(R.id.reciclador);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adaptador = new SimpleAdapter(this, objClientes.List(MainActivity.this));
        adaptador.notifyDataSetChanged();
        recycler.setAdapter(adaptador);
    }

    public void Error404(String TipoError){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(TipoError).setNegativeButton("OK",null).create().show();
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
        switch (String.valueOf(item.getTitle()))
        {
            case "Salir":
                checked = !checked;
                editor.putBoolean("pref", checked);
                editor.putString("usuario","00");
                editor.commit();
                editor.apply();
                finish();
                break;
            case "Actualizar":
                getClientes(this,Vendedor);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void showSnackBar(String msg) {
        Snackbar
                .make(findViewById(R.id.coordinator), msg, Snackbar.LENGTH_LONG)
                .show();
    }
    @Override
    public boolean onClose() {
        return false; }
    @Override
    public boolean onQueryTextSubmit(String query) { return false; }
    @Override
    public boolean onQueryTextChange(String newText){
        recycler.setAdapter(new SimpleAdapter(this,adaptador.getFilter(newText)));
        return false;
    }
}
