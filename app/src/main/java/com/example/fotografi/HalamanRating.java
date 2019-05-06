package com.example.fotografi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.fotografi.login_register.MySingleton;

import org.json.JSONException;
import org.json.JSONObject;

public class HalamanRating extends AppCompatActivity {

    SeekBar seekbar;
    TextView nilai;
    private TextView nama_fotografer;
    private Button kirim;
    private String rate;
    private String komentar;
    private EditText et_komentar;
    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_EMPTY = "";
    private String updateulasan_url = "https://fotografidb.herokuapp.com/updateulasan.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_rating);

        String full_name = getIntent().getExtras().getString("full_name");
        final String id_pesanan = getIntent().getExtras().getString("id_pesanan");

        nama_fotografer = findViewById(R.id.nama_fotografer);
        kirim = findViewById(R.id.kirim);
        et_komentar = findViewById(R.id.et_komentar);

        nama_fotografer.setText(full_name);

        seekbar = findViewById(R.id.bar_rating);
        nilai = findViewById(R.id.nilai);

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String value = String.valueOf(seekbar.getProgress());
                nilai.setText(value);
                rate = value;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                komentar = et_komentar.getText().toString().trim();
                validateInputs();
                updateUlasan(rate,komentar,id_pesanan);
                finish();
            }
        });
    }

    public void updateUlasan(String rating, String komentar, String id_pesanan){
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put("rating",rating);
            request.put("komentar", komentar);
            request.put("id_pesanan",id_pesanan);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, updateulasan_url, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //Check if user got registered successfully
                            if (response.getInt(KEY_STATUS) == 0) {
                                //Set the user session
                                Toast.makeText(getApplicationContext(),"Update berhasil",Toast.LENGTH_SHORT).show();

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
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsArrayRequest);
    }

    private boolean validateInputs(){
        if (KEY_EMPTY.equals(komentar)) {
            et_komentar.setError("Komentar tidak boleh kosong");
            et_komentar.requestFocus();
            return false;
        }
        return true;
    }

    public void tohalamanutama(View view) {
        finish();
    }
    
}
