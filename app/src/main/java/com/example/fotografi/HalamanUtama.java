package com.example.fotografi;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fotografi.login_register.SessionHandler;

public class HalamanUtama extends Fragment {
    private SessionHandler session;
    private int[] hargatotal = new int[1];

    HalamanUtamaAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
        session = new SessionHandler(getActivity().getApplicationContext());
        View rootView = inflater.inflate(R.layout.activity_halaman_utama, container, false);

        for (int i = 0; i < 1; i++) {
            hargatotal[i] = 12000;
        }

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.rvutama);
        int numberOfColumns = 1;
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));
        adapter = new HalamanUtamaAdapter (getActivity(),hargatotal);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

}
