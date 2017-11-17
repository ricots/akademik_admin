package com.android.akademik_admin.fragment;

import android.app.ProgressDialog;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

public class add_dosen extends Fragment {
    EditText input_nid,input_nama;
    Button simpan_dosen;
    ProgressDialog PD;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_add_dosen, container, false);
        PD = new ProgressDialog(getActivity());
        PD.setMessage("silahkan tunggu.....");
        PD.setCancelable(false);

        input_nid = (EditText) v.findViewById(R.id.input_nid);
        input_nama = (EditText) v.findViewById(R.id.input_nama);
        simpan_dosen = (Button) v.findViewById(R.id.simpan_dosen);
        simpan_dosen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpan_dosen();
            }
        });
        return v;
    }

    public void simpan_dosen(){
        PD.show();

        StringRequest postRequest = new StringRequest(Request.Method.POST, config.SIMPAN_DOSEN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        PD.dismiss();
                        Toast.makeText(getActivity(),response.toString(),Toast.LENGTH_LONG).show();
                        Log.d("laporan ",response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                PD.dismiss();
                Toast.makeText(getActivity(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("nid", input_nid.getText().toString());
                params.put("nama_dosen", input_nama.getText().toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(postRequest);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //menu.clear();
        inflater.inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.action_settings).setVisible(false);
        menu.findItem(R.id.load).setVisible(false);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        }

        return false;
    }
}
