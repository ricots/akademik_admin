package com.android.akademik_admin.fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.akademik_admin.R;
import com.android.akademik_admin.activity.daftar_ujian;
import com.android.akademik_admin.koneksi.config;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ujian extends Fragment {
    EditText ket,tgl;
    Button btn_ujian;
    Spinner spin_jenis_ujian,spin_thn_ajaran;
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    FloatingActionButton float_ujian;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_ujian, container, false);
        ket = (EditText) v.findViewById(R.id.ket_ujian);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        tgl = (EditText) v.findViewById(R.id.tgl_ujian);
        tgl.setInputType(InputType.TYPE_NULL);
        setDateTimeField();
        btn_ujian = (Button) v.findViewById(R.id.btn_ujian);
        float_ujian = (FloatingActionButton) v.findViewById(R.id.list_ujian);

        String list_ujian[]={"pilih jenis ujian","uts","uas"};
        spin_jenis_ujian = (Spinner) v.findViewById(R.id.spin_jenis_ujian);
        ArrayAdapter<String> AdapterList = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item,list_ujian);
        spin_jenis_ujian.setAdapter(AdapterList);

        String list_thn[]={"pilih tahun ajaran","2017/2018","2018/2019","2019/2020","2020/2021"};
        spin_thn_ajaran = (Spinner) v.findViewById(R.id.spin_thn_ajaran);
        ArrayAdapter<String> AdapterList_thn = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item,list_thn);
        spin_thn_ajaran.setAdapter(AdapterList_thn);

        btn_ujian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog PD = ProgressDialog.show(getActivity(),"Loading Data", "Please wait...",false,false);
                StringRequest postRequest = new StringRequest(Request.Method.POST, config.SIMPAN_UJIAN,
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
                        params.put("tgl_ujian", tgl.getText().toString());
                        params.put("jenis_ujian", spin_jenis_ujian.getSelectedItem().toString());
                        params.put("thn_ajaran", spin_thn_ajaran.getSelectedItem().toString());
                        params.put("keterangan", ket.getText().toString());
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                requestQueue.add(postRequest);
            }
        });

        tgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toDatePickerDialog.show();
            }
        });

        float_ujian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(),daftar_ujian.class);
                startActivity(in);
            }
        });
        return v;
    }

    private void setDateTimeField() {
        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                tgl.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        toDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                tgl.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }
}
