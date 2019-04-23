package com.example.fotografi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.fotografi.login_register.MySingleton;
import com.example.fotografi.login_register.SessionHandler;

import org.json.JSONException;
import org.json.JSONObject;

public class HalamanRegister extends AppCompatActivity {
    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_FULL_NAME = "full_name";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_TYPE = "type";
    private static final String KEY_EMPTY = "";
    private EditText etusername;
    private EditText etpassword;
    private EditText etfullname;
    private EditText etemail;
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String register_url = "https://fotografidb.herokuapp.com/register.php";
    private SessionHandler session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionHandler(getApplicationContext());
        setContentView(R.layout.activity_register);

        etusername = findViewById(R.id.et_username);
        etfullname = findViewById(R.id.et_nama);
        etpassword = findViewById(R.id.et_password);
        etemail = findViewById(R.id.editText3);
    }

    public void tologinpage() {
        finish();
    }

    public void tologinpage2(View view) {
        finish();
    }

    public void registerRun(View view){
        username = etusername.getText().toString().toLowerCase().trim();
        password = etpassword.getText().toString().trim();
        fullName = etfullname.getText().toString().trim();
        email = etemail.getText().toString().trim();
        if (validateInputs()) {
            registerUser();
        }
    }

    public void registerUser(){
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_USERNAME, username);
            request.put(KEY_PASSWORD, password);
            request.put(KEY_FULL_NAME, fullName);
            request.put(KEY_EMAIL, email);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, register_url, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //Check if user got registered successfully
                            if (response.getInt(KEY_STATUS) == 0) {
                                //Set the user session
                                String type = null;
                                session.loginUser(username,fullName,type,email);
                                tologinpage();

                            }else if(response.getInt(KEY_STATUS) == 1){
                                //Display error message if username is already existsing
                                etusername.setError("Username already taken!");
                                etusername.requestFocus();

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
        if (KEY_EMPTY.equals(fullName)) {
            etfullname.setError("Full Name cannot be empty");
            etfullname.requestFocus();
            return false;

        }
        if (KEY_EMPTY.equals(username)) {
            etusername.setError("Username cannot be empty");
            etusername.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(password)) {
            etpassword.setError("Password cannot be empty");
            etpassword.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(email)){
            etemail.setError("E-mail cannot be empty");
            etemail.requestFocus();
            return false;
        }

        return true;
    }
}
