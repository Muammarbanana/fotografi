package com.example.fotografi;

import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.fotografi.login_register.SessionHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HalamanKompletAdapter extends RecyclerView.Adapter<HalamanKompletAdapter.ViewHolder> {

    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    private LayoutInflater mInflater;
    private ArrayList<JSONObject> data = new ArrayList<>();
    private SessionHandler session;
    private Context context;

    HalamanKompletAdapter(Context context, ArrayList<JSONObject> data){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.data = data;
        session = new SessionHandler(context);
    }
    public HalamanKompletAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_pesanan_kustomer, parent, false);
        return new HalamanKompletAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HalamanKompletAdapter.ViewHolder holder, final int position) {
        try {
            holder.hargatotalku.setText(String.valueOf(data.get(position).getString("biaya")));
            holder.status.setText(String.valueOf(data.get(position).getString("status")));
            holder.sesi.setText(String.valueOf(data.get(position).getString("sesi")));
            holder.biaya.setText(String.valueOf(data.get(position).getString("biaya")));
            holder.lokasi.setText(String.valueOf(data.get(position).getString("lokasi")));
            holder.tanggal.setText(String.valueOf(data.get(position).getString("tanggal")));
            if(String.valueOf(data.get(position).getString("fullname")).equals("")){
                holder.fotografer.setText("Fotografer belum tersedia");
            }else{
                holder.fotografer.setText(String.valueOf(data.get(position).getString("fullname")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView hargatotalku;
        public TextView lokasi;
        public TextView sesi;
        public TextView tanggal;
        public TextView biaya;
        public TextView fotografer;
        public Button tombolstatus;
        public Button status;

        public ViewHolder(View itemView) {
            super(itemView);
            hargatotalku = itemView.findViewById(R.id.harga_total);
            tombolstatus = itemView.findViewById(R.id.buttonproses);
            lokasi = itemView.findViewById(R.id.harga2);
            sesi = itemView.findViewById(R.id.harga1);
            biaya = itemView.findViewById(R.id.harga4);
            tanggal = itemView.findViewById(R.id.tanggal_mulai);
            status = itemView.findViewById(R.id.status);
            fotografer = itemView.findViewById(R.id.nama_fotografer);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
