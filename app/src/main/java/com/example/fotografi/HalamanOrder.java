package com.example.fotografi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HalamanOrder extends AppCompatActivity {

    private List<String> datefull = new ArrayList<String>();
    private List<String> date30 = new ArrayList<String>();
    private List<String> date29 = new ArrayList<String>();
    private Spinner sBulan;
    private Spinner sTanggal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    }
}
