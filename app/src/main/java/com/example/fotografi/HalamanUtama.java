package com.example.fotografi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.fotografi.login_register.SessionHandler;

public class HalamanUtama extends AppCompatActivity {
    private SessionHandler session;

    HalamanUtamaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionHandler(getApplicationContext());
        setContentView(R.layout.activity_halaman_utama);

        int[] hargatotal = new int[1];

        // data untuk recyclerview
        for (int i = 0; i < 1; i++) {
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

    public void logout(View view){
        session.logoutUser();
        Intent i = new Intent(HalamanUtama.this, HalamanLogin.class);
        startActivity(i);
    }


}
