package com.desarrollo.guma.there2;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.desarrollo.guma.core.Usuario;

import java.io.File;


public class LoginActivity extends AppCompatActivity {
    final Usuario tmp = new Usuario();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final TextView txtUsurio = (TextView) findViewById(R.id.edtAgente);
        final TextView txtPass = (TextView) findViewById(R.id.edtPass);
        findViewById(R.id.btnOK).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tmp.leerDB(
                        txtUsurio.getText().toString(),
                        txtPass.getText().toString(),
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator,
                        LoginActivity.this)){
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    finish();
                }else{
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    finish();
                }

            }
        });
    }
}
