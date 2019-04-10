package com.example.fotografi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class HalamanUtama extends AppCompatActivity {

    HalamanUtamaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_utama);

        int[] hargatotal = new int[2];

        // data untuk recyclerview
        for (int i = 0; i < 2; i++) {
            hargatotal[i] = 120000;
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvutama);
        int numberOfColumns = 1;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        adapter = new HalamanUtamaAdapter (this,hargatotal);
        recyclerView.setAdapter(adapter);


    }

    public void toratingpage(View view) {
        Intent i = new Intent(getApplicationContext(),HalamanRating.class);
        startActivity(i);
    }


}
