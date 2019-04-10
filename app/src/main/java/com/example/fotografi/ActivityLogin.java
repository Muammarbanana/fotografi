package com.example.fotografi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ActivityLogin extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    public void buatakun(View view) {
        Intent i = new Intent(getApplicationContext(),register.class);
        startActivity(i);
    }


    public void tohalamanutama(View view) {
        Intent i = new Intent(getApplicationContext(),HalamanUtama.class);
        startActivity(i);
    }
}
