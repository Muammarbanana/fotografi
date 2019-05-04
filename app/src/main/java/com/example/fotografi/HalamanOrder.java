package com.example.fotografi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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
import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HalamanOrder extends AppCompatActivity {

    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_LOKASI = "lokasi";
    private static final String KEY_SESI = "sesi";
    private static final String KEY_TANGGAL = "tanggal";
    private static final String KEY_BIAYA = "biaya";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_EMPTY = "";
    private List<String> datefull = new ArrayList<String>();
    private List<String> date30 = new ArrayList<String>();
    private List<String> date29 = new ArrayList<String>();
    private Spinner sBulan;
    private Spinner sTanggal;
    private Spinner sLokasi;
    private RadioGroup rg;
    private RadioButton rb;
    private String user_id;
    private String lokasi;
    private String sesi;
    private Date tanggal;
    private String biaya;
    private String selectedradio;
    private String strdate;
    private String hari;
    private TextView total_nominal_order;
    private SimpleDateFormat dateFormat;
    private String pesan_url = "https://fotografidb.herokuapp.com/pesan.php";
    private SessionHandler session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionHandler(getApplicationContext());
        setContentView(R.layout.activity_halaman_order);

        for (int i = 1; i < 32; i++) {
            datefull.add(String.valueOf(i));
            date30.add(String.valueOf(i));
            date29.add(String.valueOf(i));
        }
        date30.remove(date30.size()-1);
        date29.remove(date29.size()-1);
        date29.remove(date29.size()-1);

        sTanggal = findViewById(R.id.spinner_tanggal);
        sBulan = findViewById(R.id.spinner_bulan);
        sLokasi = findViewById(R.id.spinner_tempat);

        sBulan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String b = sBulan.getSelectedItem().toString();
                ArrayAdapter<String> adapter30 = new ArrayAdapter<>(HalamanOrder.this,
                        android.R.layout.simple_spinner_item, date30);
                ArrayAdapter<String> adapterfull = new ArrayAdapter<>(HalamanOrder.this,
                        android.R.layout.simple_spinner_item, datefull);
                ArrayAdapter<String> adapter29 = new ArrayAdapter<>(HalamanOrder.this,
                        android.R.layout.simple_spinner_item, date29);
                if(b.equals("Februari")){
                    sTanggal.setAdapter(adapter29);
                }else if(b.equals("Januari") || b.equals("Maret") || b.equals("Mei") || b.equals("Juli")
                        || b.equals("Agustus") || b.equals("Oktober") || b.equals("Desember")){
                    sTanggal.setAdapter(adapterfull);
                }else{
                    sTanggal.setAdapter(adapter30);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sTanggal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                hari = sTanggal.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        rg = (RadioGroup) findViewById(R.id.rgSesi);
        total_nominal_order = (TextView) findViewById(R.id.total_nominal_order);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.rbSesi1:
                        rb = findViewById(checkedId);
                        selectedradio = rb.getText().toString().trim();
                        total_nominal_order.setText("Rp. 500.000");
                        break;
                    case R.id.rbSesi2:
                        rb = findViewById(checkedId);
                        selectedradio = rb.getText().toString().trim();
                        total_nominal_order.setText("Rp. 900.000");
                        break;
                    case R.id.rbSesi3:
                        rb = findViewById(checkedId);
                        selectedradio = rb.getText().toString().trim();
                        total_nominal_order.setText("Rp. 1.300.000");
                        break;
                }
            }
        });
    }

    public void orderRun(View view){
        lokasi = sLokasi.getSelectedItem().toString().trim();
        DecimalFormat formatter = new DecimalFormat("00");
        strdate = formatter.format(Integer.valueOf(hari))+formatter.format(sBulan.getSelectedItemPosition()+1)+ String.valueOf(Calendar.getInstance().get(Calendar.YEAR));

        dateFormat = new SimpleDateFormat("ddMMyyyy");
        try {
            tanggal = dateFormat.parse(strdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sesi = selectedradio;
        biaya = total_nominal_order.getText().toString().trim();
        user_id = session.getUserDetails().getUser_id();
        tambahPesanan();
    }

    public void tambahPesanan(){
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_LOKASI, lokasi);
            request.put(KEY_SESI, sesi);
            request.put(KEY_TANGGAL, tanggal);
            request.put(KEY_BIAYA, biaya);
            request.put(KEY_USER_ID, user_id);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, pesan_url, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //Check if user got registered successfully
                            if (response.getInt(KEY_STATUS) == 0) {
                                //Set the user session
                                Toast.makeText(getApplicationContext(),"Tambah pesanan berhasil",Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                Toast.makeText(getApplicationContext(),
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

                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);
    }
}
