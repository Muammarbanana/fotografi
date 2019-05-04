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

import java.lang.reflect.Array;
import java.util.ArrayList;

public class HalamanUtamaAdapter extends RecyclerView.Adapter<HalamanUtamaAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private ArrayList<String> array = new ArrayList<>();
    private SessionHandler session;
    private Context context;
    private AlertDialog.Builder builder;

    HalamanUtamaAdapter(Context context, ArrayList<String> array){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.array = array;
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
        holder.hargatotalku.setText(String.valueOf(array.get(position)));
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
        return array.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView hargatotalku;
        public Button tombolstatus;
        public Button status;

        public ViewHolder(View itemView) {
            super(itemView);
            hargatotalku = itemView.findViewById(R.id.harga_total);
            tombolstatus = itemView.findViewById(R.id.buttonproses);
            status = itemView.findViewById(R.id.status);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
