package com.android.akademik_admin.fragment;

import android.app.ProgressDialog;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.akademik_admin.R;
import com.android.akademik_admin.adapter.adapter_absen;
import com.android.akademik_admin.adapter.adapter_daftar_kuliah;
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
import java.util.List;

public class absen extends Fragment implements Spinner.OnItemSelectedListener{
    private Spinner spin_nid;
    private JSONArray result;
    private ArrayList<String> nid;
    RequestQueue RequestQueue;
    List<Item> datanya;
    RecyclerView list_absen;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adp_daftar;
    TextView cek_data;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_absen, container, false);
        nid = new ArrayList<String>();
        RequestQueue = Volley.newRequestQueue(getActivity());
        list_absen = (RecyclerView) v.findViewById(R.id.list_absen);
        list_absen.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        list_absen.setLayoutManager(layoutManager);
        datanya = new ArrayList<Item>();

        spin_nid = (Spinner) v.findViewById(R.id.spin_nid);
        spin_nid.setOnItemSelectedListener(this);
        getData_spiner();

        cek_data = (TextView) v.findViewById(R.id.cek_data);
        return v;
    }

    public void getData() {
        final ProgressDialog loading = ProgressDialog.show(getActivity(),"Loading Data", "Please wait...",false,false);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, config.NID_CARI
                + spin_nid.getSelectedItem().toString(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loading.dismiss();
                        //Log.d("hasilnya ", response.toString());
                        try {

                            JSONArray data_trans = response.getJSONArray("list_absen");

                            for (int a = 0; a < data_trans.length(); a++) {
                                Item data = new Item();
                                JSONObject json = data_trans.getJSONObject(a);
                                data.setNid(json.getString(config.NID));
                                data.setTanggal(json.getString(config.TANGGAL));
                                data.setKeterangan(json.getString("keterangan"));
                                datanya.add(data);
                                cek_data.setText("");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            System.out.println("ini kesalahannya " + e.getMessage());
                        }
                        adp_daftar.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        Log.d("ini kesalahannya",error.toString());
                    }
                });

        RequestQueue.add(jsonRequest);
    }

    private void getData_spiner(){
        final ProgressDialog loading = ProgressDialog.show(getActivity(),"Loading Data", "Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(config.NID_ABSEN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        JSONObject j = null;
                        try {
                            j = new JSONObject(response);

                            result = j.getJSONArray("list_absen");

                            getnid(result);
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
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
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
                loading.dismiss();
                Toast.makeText(getActivity(),"silahkan coba lagi",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getnid(JSONArray j){
        for(int i=0;i<j.length();i++){
            try {
                JSONObject json = j.getJSONObject(i);
                nid.add(json.getString(config.NID));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        spin_nid.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, nid));
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        getData();
        adp_daftar = new adapter_absen(datanya, getActivity());
        list_absen.setAdapter(adp_daftar);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
