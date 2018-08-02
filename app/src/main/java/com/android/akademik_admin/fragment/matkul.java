package com.android.akademik_admin.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.akademik_admin.R;
import com.android.akademik_admin.activity.add_matkul;
import com.android.akademik_admin.adapter.adapter_daftar_kuliah;
import com.android.akademik_admin.koneksi.config;
import com.android.akademik_admin.oop.Item;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class matkul extends Fragment {
    String id_kirim,item_spiner;
    RecyclerView list_daftar;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adp_daftar;
    RequestQueue requestQueue;
    List<Item> datanya;
    FloatingActionButton add;
    Spinner spin_semester;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_matkul, container, false);
        setHasOptionsMenu(true);
        list_daftar = (RecyclerView) v.findViewById(R.id.list_daftar);
        list_daftar.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        list_daftar.setLayoutManager(layoutManager);

        datanya = new ArrayList<Item>();
        requestQueue = Volley.newRequestQueue(getActivity());
        getData();
        adp_daftar = new adapter_daftar_kuliah(datanya, getActivity());
        list_daftar.setAdapter(adp_daftar);

        add = (FloatingActionButton) v.findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(),add_matkul.class);
                startActivity(in);
            }
        });

        String list[]={"pilih semester","semester 1","semester 2",
                "semester 3","semester 4","semester 5","semester 6","semester 7","semester 8"};
        spin_semester = (Spinner) v.findViewById(R.id.spin_semester);
        ArrayAdapter<String> AdapterList = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item,list);
        spin_semester.setAdapter(AdapterList);

        spin_semester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                datanya.clear();
                getData_semester();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return v;
    }

    public void getData() {
        final ProgressDialog loading = ProgressDialog.show(getActivity(),"Loading Data", "Please wait...",false,false);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, config.JADWAL_KULIAH,
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
                                data.setId_matkul(json.getString(config.KEY_ID_MATKUL));
                                data.setNama_matkul(json.getString(config.MATA_KULIAH));
                                data.setJam_mulai(json.getString(config.MULAI));
                                data.setJam_selesai(json.getString(config.BERAKHIR));
                                data.setDosen(json.getString(config.DOSEN));
                                data.setNid(json.getString(config.NID));
                                data.setHari(json.getString(config.HARI));
                                data.setSemester(json.getString(config.SEMESTER));
                                datanya.add(data);
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

        requestQueue.add(jsonRequest);
    }

    public void getData_semester() {
        if (spin_semester.getSelectedItemPosition() == 0){
            item_spiner = "pilih semester";
            getData();
        }else  if (spin_semester.getSelectedItemPosition() == 1){
            item_spiner = "1";
        }else  if (spin_semester.getSelectedItemPosition() == 2){
            item_spiner = "2";
        }else  if (spin_semester.getSelectedItemPosition() == 3){
            item_spiner = "3";
        }else  if (spin_semester.getSelectedItemPosition() == 4){
            item_spiner = "4";
        }else  if (spin_semester.getSelectedItemPosition() == 5){
            item_spiner = "5";
        }else  if (spin_semester.getSelectedItemPosition() == 6){
            item_spiner = "6";
        }else  if (spin_semester.getSelectedItemPosition() == 7){
            item_spiner = "7";
        }else  if (spin_semester.getSelectedItemPosition() == 8){
            item_spiner = "8";
        }
        final ProgressDialog loading = ProgressDialog.show(getActivity(),"Loading Data", "Please wait...",false,false);
//        Toast.makeText(getActivity(),"url " + config.KULIAH_SEMESTER  + item_spiner +
//                "&id_prodi=" + id_kirim,Toast.LENGTH_LONG).show();
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, config.JADWAL  + item_spiner,
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
                                data.setId_matkul(json.getString(config.KEY_ID_MATKUL));
                                data.setNama_matkul(json.getString(config.MATA_KULIAH));
                                data.setJam_mulai(json.getString(config.MULAI));
                                data.setJam_selesai(json.getString(config.BERAKHIR));
                                data.setDosen(json.getString(config.DOSEN));
                                data.setNid(json.getString(config.NID));
                                data.setHari(json.getString(config.HARI));
                                data.setSemester(json.getString(config.SEMESTER));
                                datanya.add(data);
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

        requestQueue.add(jsonRequest);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //menu.clear();
        inflater.inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.action_settings).setVisible(false);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.load:
                datanya.clear();
                getData();
            default:
                break;
        }

        return false;
    }

}
