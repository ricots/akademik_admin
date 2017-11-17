package com.android.akademik_admin.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.akademik_admin.MainActivity;
import com.android.akademik_admin.R;
import com.android.akademik_admin.koneksi.config;

public class login extends AppCompatActivity {
    ProgressDialog PD;
    SharedPreferences sp;
    SharedPreferences.Editor spe;
    Button btnlogin,forget;
    TextView regis;
    private boolean loggedIn = false;
    EditText user,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Login Admin");
        getSupportActionBar().getThemedContext();
        toolbar.setTitleTextColor(0xFFFFFFFF);
        user = (EditText) findViewById(R.id.user);
        pass = (EditText) findViewById(R.id.pass);

        btnlogin = (Button) findViewById(R.id.login);

        sp = this.getSharedPreferences("isi data", 0);
        spe = sp.edit();
        PD = new ProgressDialog(this);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.getText().toString().equalsIgnoreCase("admin") && (pass.getText().toString().equalsIgnoreCase("admin"))){
                    sp = login.this.getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                    //Creating editor to store values to shared preferences
                    spe = sp.edit();

                    //Adding values to editor
                    spe.putBoolean(config.LOGGEDIN_SHARED_PREF, true);
                    spe.putString(config.EMAIL_SHARED_PREF, user.getText().toString());

                    spe.commit();
                    gotohome();
                }else {
                    Toast.makeText(getApplicationContext(),"login gagal",Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        //In onresume fetching value from sharedpreference
        SharedPreferences sharedPreferences = getSharedPreferences(config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        //Fetching the boolean value form sharedpreferences
        loggedIn = sharedPreferences.getBoolean(config.LOGGEDIN_SHARED_PREF, false);

        //If we will get true
        if(loggedIn){
            //We will start the Profile Activity
            Intent intent = new Intent(login.this, MainActivity.class);
            startActivity(intent);
        }
    }


    private void gotohome() {
        Intent intent = new Intent(login.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void showDialog() {
        if (!PD.isShowing())
            PD.show();
    }

    private void hideDialog() {
        if (PD.isShowing())
            PD.dismiss();
    }

}
