package com.example.fotografi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class HalamanUtama extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_utama);

    }

    public void toratingpage(View view) {
        Intent i = new Intent(getApplicationContext(),HalamanRating.class);
        startActivity(i);
    }
}
