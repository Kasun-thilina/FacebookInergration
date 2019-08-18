package com.kasunthilina.elegentmedia;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private TextView etUserName;
    private static final String TAG = "MainActivity";
    private final String URL="https://dl.dropboxusercontent.com/s/6nt7fkdt7ck0lue/hotels.json";
    private JsonArrayRequest request;
    private RequestQueue requestQueue ;
    private List<Data> listData;
    private RecyclerView recyclerView;
    final URL JSONURL=new URL("https://dl.dropboxusercontent.com/s/6nt7fkdt7ck0lue/hotels.json");

    private Button btnLogOut;
    public MainActivity() throws MalformedURLException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etUserName=findViewById(R.id.tvUserNameMain);
        recyclerView=findViewById(R.id.recyclerView);
        listData=new ArrayList<>();

        SharedPreferences prefs = this.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String username = prefs.getString("username", null);
        etUserName.setText(username);
        btnLogOut=findViewById(R.id.btnLogOut);
        parseJSON();
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FacebookSdk.sdkInitialize(getApplicationContext());
                LoginManager.getInstance().logOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
    }

    private void parseJSON() {
        requestQueue= Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Process the JSON
                        try{
                            // Get the JSON array
                            JSONArray array = response.getJSONArray("data");
                            Log.d(TAG, "JSON ResponseBody :" + array);
                            // Loop through the array elements
                            for(int i=0;i<array.length();i++){
                                // Get current json object
                                JSONObject jsonObject = array.getJSONObject(i);
                                //getting the image sub node
                                JSONObject image = jsonObject.getJSONObject("image");
                                Data data=new Data();
                                data.setTitle(jsonObject.getString("title"));
                                data.setDescription(jsonObject.getString("description"));
                                data.setAddress(jsonObject.getString("address"));
                                data.setPostcode(jsonObject.getString("postcode"));
                                data.setPhoneNumber(jsonObject.getString("phoneNumber"));
                                data.setLatitude(jsonObject.getString("latitude"));
                                data.setLongitude(jsonObject.getString("longitude"));

                                data.setImage(image.getString("medium"));
                                String temp=image.getString("medium");
                                Log.d(TAG, "###img: "+temp );
                                listData.add(data);
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                        setupRecyclerView(listData);
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        // Do something when error occurred
                    }
                }
        );

        // Add JsonObjectRequest to the RequestQueue
        requestQueue.add(jsonObjectRequest);
    }

    private void setupRecyclerView(List<Data> listData) {

        RecyclerViewAdapter myAdapter=new RecyclerViewAdapter(this,listData);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);
    }


}
