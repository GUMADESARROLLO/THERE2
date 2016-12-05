package com.desarrollo.guma.there2;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.desarrollo.guma.core.Clientes;

/**
 * Created by luis.perez on 29/11/2016.
 */

public class AllClientesActivity extends ListActivity
{
    //Progress Dialog
    private ProgressDialog pDialog;
    //Creating JSON Parser object
    JSONParser jParser = new JSONParser();

    ArrayList<HashMap<String,String>> clientesList;

    //url para listado de clientes
    private static String url_all_clientes = ClssURL.getURL_CLENTES();

    private static final String TAG_CLIENTE = "CLIENTE";
    private static final String TAG_NOMBRE = "NOMBRE";
    private static final String TAG_DIRECCION = "DIRECCION";
    private static final String TAG_TELEFONO1 = "TELEFONO1";
    private static final String TAG_VENDEDOR = "CLIENTE";

    JSONArray mclientes = null;

    @Override
    public void onCreate(Bundle savedInstaceSate)
    {
        super.onCreate(savedInstaceSate);
        setContentView(R.layout.all_clientes);
        //Hashmap para el ListView
        clientesList = new ArrayList<HashMap<String, String>>();
        //Cargar los Clientes
        new LoadAllClientes().execute();
        ListView lv = getListView();
        lv.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                String pid = ((TextView) view.findViewById(R.id.pid)).getText().toString();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100)
        {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
    }

    class LoadAllClientes extends AsyncTask<String, String, String>
    {
        String Cli, Nom, Dir, Tel, Ven;

        //Antes de comenzar la tarera mostrar un dialgo de progreso
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pDialog = new ProgressDialog(AllClientesActivity.this);
            pDialog.setMessage("Cargando Clientes. Favor espere ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }
        //Conseguir todos los clientes segun la url
        protected String doInBackground(String... args)
        {
            //Parametros
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            //parametros.put("V",User);
            params.add(new BasicNameValuePair("V","11"));
            //Obtener el JSON String de la URL
            //JSONObject json = jParser.makeHttpRequest(url_all_clientes,"GET",params);
            JSONArray jArray = jParser.makeHttpRequest(url_all_clientes, "GET",params);
            Log.d(jArray.toString(),"");
            Clientes.ExecuteSQL(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator, AllClientesActivity.this,"DELETE FROM CLIENTES;");
            Clientes.ExecuteSQL(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator, AllClientesActivity.this,"DELETE FROM DETALLE_FACTURA_PUNTOS;");

            try
            {
                Clientes.ExecuteSQL(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator, AllClientesActivity.this,jArray.getJSONObject(0).getString("CLIENTES"));
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
            try
            {
                Clientes.ExecuteSQL(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator, AllClientesActivity.this,jArray.getJSONObject(0).getString("FACTURAS"));
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

            //Log.d("Clientes", json.toString());
            /*
            try
            {

                String succes = json.getString(TAG_CLIENTE);
                if (succes != "")
                {
                    for (int i = 0; i < mclientes.length(); i++)
                    {
                        JSONObject c = mclientes.getJSONObject(i);
                        Cli = c.getString(TAG_CLIENTE);
                        Nom = c.getString(TAG_NOMBRE);
                        Dir = c.getString(TAG_DIRECCION);
                        Tel = c.getString(TAG_TELEFONO1);
                        Ven = c.getString(TAG_VENDEDOR);

                        HashMap<String, String> map = new HashMap<String, String>();

                        map.put(TAG_CLIENTE, Cli);
                        map.put(TAG_NOMBRE , Nom);
                        map.put(TAG_DIRECCION, Dir);
                        map.put(TAG_TELEFONO1, Tel);
                        map.put(TAG_VENDEDOR, Ven);

                    }
                }
            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }
            return null;
            */
            return null;
        }

        protected void onPostExecute(String file_url)
        {
            pDialog.dismiss();
            runOnUiThread
            (new Runnable()
                {
                    @Override
                    public void run()
                    {
                        ListAdapter adapter = new
                        SimpleAdapter(AllClientesActivity.this,
                                      clientesList,
                                      R.layout.listitem_clientes,
                                      new String[]{TAG_CLIENTE,TAG_NOMBRE},
                                      new int[]{R.id.pid   , R.id.name});
                        setListAdapter(adapter);
                    }
                }
            );
        }
    }

}
