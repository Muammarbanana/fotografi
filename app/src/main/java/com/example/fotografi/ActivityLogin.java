package com.example.fotografi;

import android.content.Intent;
import android.se.omapi.Session;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ActivityLogin extends AppCompatActivity {
    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_FULL_NAME = "full_name";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_EMPTY = "";
    private EditText etusername;
    private EditText etpassword;
    private String username;
    private String password;
    private ProgressBar progressBar;
    private String login_url = "http://10.145.2.210/member/login.php";
    private SessionHandler session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionHandler(getApplicationContext());

        if(session.isLoggedIn()){
            tohalamanutama();
        }
        setContentView(R.layout.activity_login);

        etusername = findViewById(R.id.editText_username);
        etpassword = findViewById(R.id.editText_password);

    }

    public void buatakun(View view) {
        Intent i = new Intent(getApplicationContext(),register.class);
        startActivity(i);
    }


    public void tohalamanutama() {
        Intent i = new Intent(getApplicationContext(),HalamanUtama.class);
        startActivity(i);
    }

    public void login_run(View view) {
        username = etusername.getText().toString().toLowerCase().trim();
        password = etpassword.getText().toString().trim();
        if (validateInputs()) {
            login();
        }
    }

    public void login(){
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_USERNAME, username);
            request.put(KEY_PASSWORD, password);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, login_url, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //Check if user got logged in successfully

                            if (response.getInt(KEY_STATUS) == 0) {
                                session.loginUser(username,response.getString(KEY_FULL_NAME));
                                Toast.makeText(getApplicationContext(),"Login berhasil",Toast.LENGTH_SHORT).show();
                                tohalamanutama();

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

    private boolean validateInputs() {
        if(KEY_EMPTY.equals(username)){
            etusername.setError("Username cannot be empty");
            etusername.requestFocus();
            return false;
        }
        if(KEY_EMPTY.equals(password)){
            etpassword.setError("Password cannot be empty");
            etpassword.requestFocus();
            return false;
        }
        return true;
    }
}
