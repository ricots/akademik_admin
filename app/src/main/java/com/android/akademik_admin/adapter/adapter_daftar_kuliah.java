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

public class adapter_daftar_kuliah extends RecyclerView.Adapter<adapter_daftar_kuliah.ViewHolder> {
    private ImageLoader imageLoader;
    private Context context;
    List<Item> dftr;

    public adapter_daftar_kuliah(List<Item> dftr, Context context){
        super();
        //Getting all the superheroes
        this.dftr = dftr;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_daftar_kuliah, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Item daftar =  dftr.get(position);
        holder.id_matkul.setText(daftar.getId_matkul());
        holder.matkul.setText(daftar.getNama_matkul());
        holder.jam_mulai.setText(daftar.getJam_mulai());
        holder.jam_selesai.setText(daftar.getJam_selesai());
        holder.dosen.setText(daftar.getDosen());
        holder.id_nid.setText(daftar.getNid());
        holder.hari.setText(daftar.getHari());
        holder.semester.setText(daftar.getSemester());

        if ((holder.id_matkul.getText().toString().equals("null") && (holder.matkul.getText().toString().equals("null") &&
                (holder.jam_mulai.getText().toString().equals("null") && (holder.jam_selesai.getText().toString().equals("null") &&
                        (holder.dosen.getText().toString().equals("null") && (holder.id_nid.getText().toString().equals("null") &&
                                (holder.hari.getText().toString().equals("null") && (holder.semester.getText().toString().equals("null")))))))))){
            holder.id_matkul.setText("");
            holder.matkul.setText("");
            holder.jam_mulai.setText("");
            holder.jam_selesai.setText("");
            holder.dosen.setText("");
            holder.id_nid.setText("");
            holder.hari.setText("");
            holder.semester.setText("");
        }
        holder.item = daftar;
    }

    @Override
    public int getItemCount() {
        return dftr.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        final TextView id_matkul,matkul,jam_mulai,jam_selesai,dosen,id_nid,hari,semester;
        Item item;

        public ViewHolder(View itemView) {
            super(itemView);
            semester = (TextView) itemView.findViewById(R.id.semester);
            id_nid = (TextView) itemView.findViewById(R.id.id_nid);
            hari = (TextView) itemView.findViewById(R.id.hari);
            id_matkul = (TextView) itemView.findViewById(R.id.id_matkul);
            matkul = (TextView) itemView.findViewById(R.id.matkul);
            jam_mulai = (TextView) itemView.findViewById(R.id.jam_mulai);
            jam_selesai = (TextView) itemView.findViewById(R.id.jam_selesai);
            dosen = (TextView) itemView.findViewById(R.id.dosen);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent detail = new Intent(context,update_jadwal.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("idmatkul",id_matkul.getText().toString());
                    detail.putExtras(bundle);
                    v.getContext().startActivity(detail);
                }
            });
        }
    }

}