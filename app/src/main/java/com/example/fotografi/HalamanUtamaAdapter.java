package com.example.fotografi;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.fotografi.login_register.SessionHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class HalamanUtamaAdapter extends RecyclerView.Adapter<HalamanUtamaAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private ArrayList<JSONObject> data = new ArrayList<>();
    private SessionHandler session;
    private Context context;
    private AlertDialog.Builder builder;

    HalamanUtamaAdapter(Context context, ArrayList<JSONObject> data){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.data = data;
        session = new SessionHandler(context);
        AlertDialog.Builder builder2 = new AlertDialog.Builder(context);
        builder2.setCancelable(true);
        builder2.setTitle("Konfirmasi");
        builder2.setMessage("Yakin ingin melanjutkan?");
        builder2.setPositiveButton("Lanjut",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder2.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        this.builder = builder2;
    }

    @Override
    public HalamanUtamaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(session.getUserDetails().getType().equals("fotografer")) {
            View view = mInflater.inflate(R.layout.item_pesanan_fotografer, parent, false);
            return new ViewHolder(view);
        }else{
            View view = mInflater.inflate(R.layout.item_pesanan_kustomer, parent, false);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        try {
            holder.hargatotalku.setText(String.valueOf(data.get(position).getString("biaya")));
            holder.status.setText(String.valueOf(data.get(position).getString("status")));
            holder.sesi.setText(String.valueOf(data.get(position).getString("sesi")));
            holder.biaya.setText(String.valueOf(data.get(position).getString("biaya")));
            holder.lokasi.setText(String.valueOf(data.get(position).getString("lokasi")));
            holder.tanggal.setText(String.valueOf(data.get(position).getString("tanggal")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(session.getUserDetails().getType().equals("fotografer")){
            holder.tombolstatus.setOnClickListener(new View.OnClickListener() {
                int i = 1;
                AlertDialog dialog = builder.create();


                @Override
                public void onClick(View v) {
                    switch (i) {
                        case 1:
                            dialog.show();
                            holder.status.setText("Transfer DP");
                            i = i + 1;
                            break;
                        case 2:
                            holder.status.setText("Stand By");
                            i = i + 1;
                            break;
                        case 3:
                            holder.status.setText("In-Session");
                            i = i + 1;
                            break;
                        case 4:
                            holder.status.setText("Finishing");
                            i = i + 1;
                            break;
                        case 5:
                            holder.status.setText("Transfer Full");
                            i = i + 1;
                            break;
                        case 6:
                            holder.status.setText("Completed");
                            break;
                    }
                }
            });
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
        public Button tombolstatus;
        public Button status;

        public ViewHolder(View itemView) {
            super(itemView);
            hargatotalku = itemView.findViewById(R.id.harga_total);
            tombolstatus = itemView.findViewById(R.id.buttonproses);
            lokasi = itemView.findViewById(R.id.harga1);
            sesi = itemView.findViewById(R.id.harga2);
            biaya = itemView.findViewById(R.id.harga4);
            tanggal = itemView.findViewById(R.id.tanggal_mulai);
            status = itemView.findViewById(R.id.status);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
