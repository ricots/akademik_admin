package com.android.akademik_admin.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.akademik_admin.R;
import com.android.akademik_admin.activity.update_jadwal;
import com.android.akademik_admin.oop.Item;
import com.android.volley.toolbox.ImageLoader;

import java.util.List;

public class adapter_absen extends RecyclerView.Adapter<adapter_absen.ViewHolder> {
    private ImageLoader imageLoader;
    private Context context;
    List<Item> dftr;

    public adapter_absen(List<Item> dftr, Context context){
        super();
        //Getting all the superheroes
        this.dftr = dftr;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_absen, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Item daftar =  dftr.get(position);
        holder.nid.setText(daftar.getNid());
        holder.tgl.setText(daftar.getTanggal());
        holder.ket.setText(daftar.getKeterangan());
        holder.item = daftar;
    }

    @Override
    public int getItemCount() {
        return dftr.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        final TextView nid,tgl,ket;
        Item item;

        public ViewHolder(View itemView) {
            super(itemView);
            nid = (TextView) itemView.findViewById(R.id.nid);
            tgl = (TextView) itemView.findViewById(R.id.tgl);
            ket = (TextView) itemView.findViewById(R.id.ket);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*Intent detail = new Intent(context,update_jadwal.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("idmatkul",id_matkul.getText().toString());
                    detail.putExtras(bundle);
                    v.getContext().startActivity(detail);*/
                }
            });
        }
    }

}