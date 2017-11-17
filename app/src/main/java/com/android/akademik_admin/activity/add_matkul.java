package com.android.akademik_admin.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.akademik_admin.R;
import com.android.akademik_admin.koneksi.config;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class add_matkul extends AppCompatActivity {
    EditText input_matkul,input_semester;
    Button simpan_matkul;
    ProgressDialog PD;
    Spinner spin_semester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_matkul);
        PD = new ProgressDialog(this);
        PD.setMessage("silahkan tunggu.....");
        PD.setCancelable(false);

        String list[]={"pilih semester","semester 1","semester 2",
                "semester 3","semester 4","semester 5","semester 6","semester 7","semester 8"};
        spin_semester = (Spinner) findViewById(R.id.spin_semester);
        ArrayAdapter<String> AdapterList = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item,list);
        spin_semester.setAdapter(AdapterList);

        Toolbar toolbar1 = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("ADD MATKUL");

        input_matkul = (EditText) findViewById(R.id.input_matkul);
        simpan_matkul = (Button) findViewById(R.id.simpan_matkul);
        simpan_matkul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpan_matkul();
            }
        });
    }

    public void simpan_matkul(){
        PD.show();

        StringRequest postRequest = new StringRequest(Request.Method.POST, config.SIMPAN_MATKUL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        PD.dismiss();
                        Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
                        Log.d("laporan ",response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                PD.dismiss();
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("nama_matkul", input_matkul.getText().toString());
                params.put("semester", spin_semester.getSelectedItem().toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
