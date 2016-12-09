package com.desarrollo.guma.there2;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.desarrollo.guma.core.Clientes;
import com.desarrollo.guma.core.Usuario;

//import com.loopj.android.http.*;
//import com.loopj.android.http.AsyncHttpClient;
//import com.loopj.android.http.AsyncHttpResponseHandler;
//import com.loopj.android.http.RequestParams;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;

public class LoginActivity extends AppCompatActivity
{
    final Usuario tmp = new Usuario();
    private boolean checked;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    SpecialAdapter adapter2;
    ProgressDialog pdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_uma);
        final TextView txtUsurio = (TextView) findViewById(R.id.edtAgente);
        final TextView txtPass = (TextView) findViewById(R.id.edtPass);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        checked = preferences.getBoolean("pref", false);

        findViewById(R.id.btnOK).setOnClickListener
            (new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        if (tmp.leerDB(txtUsurio.getText().toString(),txtPass.getText().toString(),ClssURL.getDIR_DB(),LoginActivity.this)){
                            checked = !checked;
                            editor.putBoolean("pref", checked);
                            Cursor res = tmp.InfoUsuario(ClssURL.getDIR_DB(),LoginActivity.this);
                            if (res.getCount()!=0){
                                if (res.moveToFirst()){
                                    editor.putString("usuario",res.getString(0).toString());
                                }
                            }
                            editor.apply();
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                            finish();
                        }else{
                            AsyncHttpClient servicio = new AsyncHttpClient();
                            RequestParams parametros = new RequestParams();
                            parametros.put("V",txtUsurio.getText().toString());
                            parametros.put("P",txtPass.getText().toString());
                            servicio.post(ClssURL.getURL_VENDEDOR(), parametros, new AsyncHttpResponseHandler() {
                                @Override
                                public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                                    String Qry;
                                    if (statusCode==200)
                                    {
                                        try
                                        {
                                            JSONObject jsonObject = new JSONObject(new String(responseBody));

                                            Qry = "INSERT INTO Usuarios (IdVendedor, NombreUsuario, Credencial, Password, PerfilSupervisor) VALUES("+jsonObject.get("VENDEDOR").toString()+", '"+jsonObject.get("NOMBRE").toString()+"', '"+txtUsurio.getText().toString()+"', '"+txtPass.getText().toString()+"', '"+jsonObject.get("PerfilSupervisor").toString()+"')";
                                            Clientes.ExecuteSQL(ClssURL.getDIR_DB(), LoginActivity.this,"DELETE FROM Usuarios;");
                                            Clientes.ExecuteSQL(ClssURL.getDIR_DB(), LoginActivity.this, Qry);

                                            checked = !checked;
                                            editor.putBoolean("pref", checked);
                                            editor.putString("usuario",jsonObject.get("VENDEDOR").toString());
                                            editor.putString("perfil", jsonObject.get("PerfilSupervisor").toString());
                                            editor.commit();
                                            editor.apply();

                                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                            finish();


                                        }catch (Exception e){

                                            pdialog.dismiss();
                                            adapter2.notifyDataSetChanged();
                                            Error404("Error de Actualización de Datos de Puntos de Facturas.");
                                        }
                                    }
                                    else
                                    {
                                        pdialog.dismiss();
                                        adapter2.notifyDataSetChanged();
                                        Error404("Error de Actualización de Datos de Puntos de Facturas.");
                                    }
                                }

                                @Override
                                public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                                    Error404("onFailure");
                                }
                            });
                        }
                    }
                }
            );
        if (checked==true){
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            finish();
        }
    }
    public void Error404(String TipoError){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(TipoError).setNegativeButton("OK",null).create().show();
    }

}
