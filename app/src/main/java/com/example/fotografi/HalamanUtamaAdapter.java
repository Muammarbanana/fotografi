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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.fotografi.login_register.MySingleton;
import com.example.fotografi.login_register.SessionHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HalamanUtamaAdapter extends RecyclerView.Adapter<HalamanUtamaAdapter.ViewHolder> {

    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    private LayoutInflater mInflater;
    private ArrayList<JSONObject> data = new ArrayList<>();
    private SessionHandler session;
    private Context context;
    private AlertDialog.Builder builder2;
    private String updatestatus_url = "https://fotografidb.herokuapp.com/updatestatus.php";

    HalamanUtamaAdapter(Context context, ArrayList<JSONObject> data){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.data = data;
        session = new SessionHandler(context);
        builder2 = new AlertDialog.Builder(context);
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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
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

        builder2.setCancelable(true);
        builder2.setTitle("Konfirmasi");
        builder2.setMessage("Yakin ingin melanjutkan?");
        builder2.setPositiveButton("Lanjut",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            String statusnya = String.valueOf(data.get(position).getString("status"));
                            String id_user = session.getUserDetails().getUser_id();
                            String pesanan_id = String.valueOf(data.get(position).getString("id_pesanan"));
                            updateStatus(id_user,nextstatus(statusnya),pesanan_id);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        builder2.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        if(session.getUserDetails().getType().equals("fotografer")){
            holder.tombolstatus.setOnClickListener(new View.OnClickListener() {

                AlertDialog dialog = builder2.create();


                @Override
                public void onClick(View v) {
                    dialog.show();
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
        public TextView fotografer;
        public Button tombolstatus;
        public Button status;

        public ViewHolder(View itemView) {
            super(itemView);
            hargatotalku = itemView.findViewById(R.id.harga_total);
            tombolstatus = itemView.findViewById(R.id.buttonproses);
            lokasi = itemView.findViewById(R.id.harga2);
            sesi = itemView.findViewById(R.id.harga1);
            biaya = itemView.findViewById(R.id.ratingvalue);
            tanggal = itemView.findViewById(R.id.tanggal_mulai);
            status = itemView.findViewById(R.id.status);
            fotografer = itemView.findViewById(R.id.nama_fotografer);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }

    public String nextstatus(String status){
        if(status.equals("Searching")){
            status = "Transfer DP";
        }else if(status.equals("Transfer DP")){
            status = "Stand-By";
        }else if(status.equals("Stand-By")){
            status = "In-Session";
        }else if(status.equals("In-Session")){
            status = "Finishing";
        }else if(status.equals("Finishing")){
            status = "Transfer Full";
        }else{
            status = "Completed";
        }
        return status;
    }

    public void updateStatus(String user_id_2, String status, String id_pesanan){
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put("user_id_2",user_id_2);
            request.put("status", status);
            request.put("id_pesanan",id_pesanan);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, updatestatus_url, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //Check if user got registered successfully
                            if (response.getInt(KEY_STATUS) == 0) {
                                //Set the user session
                                Toast.makeText(context,"Update berhasil",Toast.LENGTH_SHORT).show();

                            }else{
                                Toast.makeText(context,
                                        response.getString(KEY_MESSAGE), Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //Display error message whenever an error occurs

                        Toast.makeText(context,
                                error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(context).addToRequestQueue(jsArrayRequest);
    }
}
