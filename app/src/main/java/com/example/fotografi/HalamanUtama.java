package com.example.fotografi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.fotografi.login_register.MySingleton;
import com.example.fotografi.login_register.SessionHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class HalamanUtama extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private SessionHandler session;
    private ArrayList<JSONObject> array = new ArrayList<>();
    private String pesanan_url = "https://fotografidb.herokuapp.com/getpesanan.php";
    private String pesanankustomer_url = "https://fotografidb.herokuapp.com/getpesanankustomer.php";
    private SharedPreferences mInfopesanan;
    private SharedPreferences.Editor mInfopesananedit;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView no_order;

    HalamanUtamaAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
        session = new SessionHandler(getActivity().getApplicationContext());
        mInfopesanan = getActivity().getSharedPreferences("info_pesanan", getActivity().getApplicationContext().MODE_PRIVATE);
        View rootView = inflater.inflate(R.layout.activity_halaman_utama, container, false);

        mInfopesananedit = mInfopesanan.edit();
        swipeRefreshLayout = rootView.findViewById(R.id.swiperefresh1);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rvutama);
        no_order = rootView.findViewById(R.id.no_order);
        int numberOfColumns = 1;
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));

        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                loadData();
            }
        });

        return rootView;
    }

    public void loadData(){
        swipeRefreshLayout.setRefreshing(true);
        if(session.getUserDetails().getType().equals("fotografer")){
            JsonArrayRequest jsArrayRequest = new JsonArrayRequest
                    (pesanan_url, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            for(int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    array.add(jsonObject);
                                    //Toast.makeText(getActivity().getApplicationContext(),String.valueOf(array.size()),Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            adapter = new HalamanUtamaAdapter (getActivity(),array);
                            recyclerView.setAdapter(adapter);
                            swipeRefreshLayout.setRefreshing(false);
                            if(array.size()==0){
                                no_order.setVisibility(View.VISIBLE);
                            }else{
                                no_order.setVisibility(View.INVISIBLE);
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {

                            //Display error message whenever an error occurs
                            Toast.makeText(getActivity().getApplicationContext(),
                                    error.getMessage(), Toast.LENGTH_SHORT).show();
                            swipeRefreshLayout.setRefreshing(false);

                        }
                    });

            // Access the RequestQueue through your singleton class.
            MySingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(jsArrayRequest);
        }else{
            JSONObject rqst = new JSONObject();
            try {
                rqst.put("user_id", session.getUserDetails().getUser_id());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JSONArray request = new JSONArray();
            //Populate the request parameters
            request.put(rqst);

            JsonArrayRequest jsArrayRequest = new JsonArrayRequest(Request.Method.POST, pesanankustomer_url, request, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    for(int i = 0; i < response.length(); i++) {

                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            array.add(jsonObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    mInfopesananedit.putInt("arraysize",array.size());
                    mInfopesananedit.apply();
                    adapter = new HalamanUtamaAdapter (getActivity(),array);
                    recyclerView.setAdapter(adapter);
                    swipeRefreshLayout.setRefreshing(false);
                    if(array.size()==0){
                        no_order.setVisibility(View.VISIBLE);
                    }else{
                        no_order.setVisibility(View.INVISIBLE);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //Display error message whenever an error occurs
                    Toast.makeText(getActivity().getApplicationContext(),
                            error.getMessage(), Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            });
            // Access the RequestQueue through your singleton class.
            MySingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(jsArrayRequest);
        }
        array.clear();
    }

    @Override
    public void onRefresh() {
        loadData();
    }
}
