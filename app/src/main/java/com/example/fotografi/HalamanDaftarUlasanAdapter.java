package com.example.fotografi;

import android.content.Context;
import android.content.Intent;
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

public class HalamanDaftarUlasanAdapter extends RecyclerView.Adapter<HalamanDaftarUlasanAdapter.ViewHolder> {

    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    private LayoutInflater mInflater;
    private ArrayList<JSONObject> data = new ArrayList<>();
    private SessionHandler session;
    private Context context;

    HalamanDaftarUlasanAdapter(Context context, ArrayList<JSONObject> data){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.data = data;
        session = new SessionHandler(context);
    }
    public HalamanDaftarUlasanAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_ulasan, parent, false);
        return new HalamanDaftarUlasanAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HalamanDaftarUlasanAdapter.ViewHolder holder, final int position) {
        try {
            holder.sesi.setText(String.valueOf(data.get(position).getString("sesi")));
            holder.lokasi.setText(String.valueOf(data.get(position).getString("lokasi")));
            holder.tanggal.setText(String.valueOf(data.get(position).getString("tanggal")));
            if(String.valueOf(data.get(position).getString("full_name")).equals("")){
                holder.fotografer.setText("Fotografer belum tersedia");
            }else{
                holder.fotografer.setText(String.valueOf(data.get(position).getString("full_name")));
            }
            if(String.valueOf(data.get(position).getString("rating")).equals("null")){
                holder.rating.setText("Belum tersedia");
            }else{
                holder.rating.setText(String.valueOf(data.get(position).getString("rating")));
            }
            if(String.valueOf(data.get(position).getString("komentar")).equals("null")){
                holder.komentar.setText("Belum tersedia");
            }else{
                holder.tombolulasan.setVisibility(View.GONE);
                holder.komentar.setText(String.valueOf(data.get(position).getString("komentar")));
            }
            if(session.getUserDetails().getType().equals("fotografer")){
                holder.tombolulasan.setVisibility(View.GONE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        holder.tombolulasan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),HalamanRating.class);
                try {
                    intent.putExtra("id_pesanan",String.valueOf(data.get(position).getString("id_pesanan")));
                    intent.putExtra("full_name",String.valueOf(data.get(position).getString("full_name")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView komentar;
        public TextView lokasi;
        public TextView sesi;
        public TextView tanggal;
        public TextView rating;
        public TextView fotografer;
        public Button tombolulasan;

        public ViewHolder(View itemView) {
            super(itemView);
            komentar = itemView.findViewById(R.id.valuekomentar);
            tombolulasan = itemView.findViewById(R.id.buttonproses);
            lokasi = itemView.findViewById(R.id.harga2);
            sesi = itemView.findViewById(R.id.harga1);
            rating = itemView.findViewById(R.id.ratingvalue);
            tanggal = itemView.findViewById(R.id.tanggal_mulai);
            fotografer = itemView.findViewById(R.id.nama_fotografer);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
