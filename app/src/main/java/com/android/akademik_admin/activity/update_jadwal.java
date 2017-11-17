package com.android.akademik_admin.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.akademik_admin.R;
import com.android.akademik_admin.koneksi.config;
import com.android.akademik_admin.oop.Item;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class update_jadwal extends AppCompatActivity {
    String id_matkul;
    RequestQueue requestQueue;
    EditText nama_matkul,semester,hari_matkul,ruang,jam_mulai,jam_selesai,prodi;
    Spinner spin_dosen;
    private JSONArray result;
    private ArrayList<String> dosen;
    TextView nidnya,idprodinya;
    Button update_dosen;
    ProgressDialog PD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_jadwal);
        Toolbar toolbar1 = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("UPDATE DATA");

        requestQueue = Volley.newRequestQueue(this);

        PD = new ProgressDialog(this);
        PD.setMessage("silahkan tunggu.....");
        PD.setCancelable(false);

        final Bundle bundle = getIntent().getExtras();
        id_matkul = bundle.getString("idmatkul");
        Toast.makeText(this,id_matkul,Toast.LENGTH_LONG).show();

        nama_matkul = (EditText) findViewById(R.id.nama_matkul);
        semester = (EditText) findViewById(R.id.semester_kuliah);
        hari_matkul = (EditText) findViewById(R.id.hari_matkul);
        ruang = (EditText) findViewById(R.id.ruang);
        jam_mulai = (EditText) findViewById(R.id.update_jam_mulai);
        jam_selesai = (EditText) findViewById(R.id.update_jam_selesai);
        prodi = (EditText) findViewById(R.id.prodi);
        nidnya = (TextView) findViewById(R.id.nidnya);
        idprodinya = (TextView) findViewById(R.id.idprodinya);
        getData();

        dosen = new ArrayList<String>();
        spin_dosen = (Spinner) findViewById(R.id.spin_dosen);
        spin_dosen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nidnya.setText(get_nid(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        getData_dosen();

        update_dosen = (Button) findViewById(R.id.update_dosen);
        update_dosen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_data();
            }
        });
    }

    public void getData() {
        final ProgressDialog loading = ProgressDialog.show(update_jadwal.this,"Loading Data", "Please wait...",false,false);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, config.DETAIL_JADWAL + id_matkul,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loading.dismiss();
                        //Log.d("hasilnya ", response.toString());
                        try {

                            JSONArray data_trans = response.getJSONArray("list_jadwal");

                            for (int a = 0; a < data_trans.length(); a++) {
                                Item data = new Item();
                                JSONObject json = data_trans.getJSONObject(a);
                                String matkul = json.getString(config.MATA_KULIAH);
                                String mulai = json.getString(config.MULAI);
                                String selsai = json.getString(config.BERAKHIR);
                                String hari = json.getString(config.HARI);
                                String sems = json.getString(config.SEMESTER);
                                String ruangnya = json.getString("ruang");
                                String prodinya = json.getString("nama_prodi");
                                String idprodi = json.getString("id_prodi");
                                nama_matkul.setText(matkul);
                                jam_mulai.setText(mulai);
                                jam_selesai.setText(selsai);
                                hari_matkul.setText(hari);
                                semester.setText(sems);
                                ruang.setText(ruangnya);
                                prodi.setText(prodinya);
                                idprodinya.setText(idprodi);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            System.out.println("ini kesalahannya " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        Log.d("ini kesalahannya",error.toString());
                    }
                });

        requestQueue.add(jsonRequest);
    }

    private void getData_dosen(){
        StringRequest stringRequest = new StringRequest(config.SPINER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            //Parsing the fetched Json String to JSON Object
                            j = new JSONObject(response);

                            //Storing the Array of JSON String to our JSON Array
                            result = j.getJSONArray("list_dosen");

                            //Calling method getStudents to get the students from the JSON Array
                            getdosen(result);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        //Adding request to the queue
        requestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {
                Toast.makeText(getApplicationContext(),"silahkan coba lagi",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getdosen(JSONArray j){
        //Traversing through all the items in the json array
        for(int i=0;i<j.length();i++) {
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                dosen.add(json.getString("nama_dosen"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
            spin_dosen.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.spiner_item, dosen));
        }

    private String get_nid(int position){
        String nid="";
        try {
            //Getting object of given index
            JSONObject json = result.getJSONObject(position);

            //Fetching name from that object
            nid = json.getString(config.NID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return nid;
    }

    public void update_data(){
        PD.show();

        StringRequest postRequest = new StringRequest(Request.Method.POST, config.UPDATE_PENGAJAR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        PD.dismiss();
                        Toast.makeText(update_jadwal.this,response.toString(),Toast.LENGTH_LONG).show();
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
                params.put("nama_matkul", nama_matkul.getText().toString());
                params.put("semester", semester.getText().toString());
                params.put("hari", hari_matkul.getText().toString());
                params.put("jam_mulai", jam_mulai.getText().toString());
                params.put("jam_selesai", jam_selesai.getText().toString());
                params.put("ruang", ruang.getText().toString());
                params.put("nid", nidnya.getText().toString());
                params.put("id_prodi", idprodinya.getText().toString());
                params.put("id_matkul", id_matkul);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(update_jadwal.this);
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

        if(item.getItemId() == R.id.load){
            getData();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.action_settings).setVisible(false);
        return true;
    }
}
