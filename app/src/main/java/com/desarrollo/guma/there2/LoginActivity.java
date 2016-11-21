package com.desarrollo.guma.there2;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.desarrollo.guma.core.Usuario;

import java.io.File;

public class LoginActivity extends AppCompatActivity
{
    final Usuario tmp = new Usuario();
    private boolean checked;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

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
                        if (tmp.leerDB(txtUsurio.getText().toString(),txtPass.getText().toString(),
                            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator,
                            LoginActivity.this)
                           )
                        {
                            checked = !checked;
                            editor.putBoolean("pref", checked);
                            editor.apply();

                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                            finish();
                        }
                      else
                        {
                            showSnackBar();
                            //startActivity(new Intent(LoginActivity.this,MainActivity.class));
                            //finish();
                        }
                    }
                }
            );
        if (checked==true){
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            finish();
        }
    }
    private void showSnackBar() {Toast.makeText(this, "Usuario Incorrcto", Toast.LENGTH_SHORT).show(); }
}
