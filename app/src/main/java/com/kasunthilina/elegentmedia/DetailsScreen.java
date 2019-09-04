package com.kasunthilina.elegentmedia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class DetailsScreen extends AppCompatActivity {

     ImageView imgDetailed;
     TextView tvDetailedHeading,tvDetailedDescription;
     String longitude,latitude,address,descripton,heading,image;
     Button btnMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_screen);
        longitude=getIntent().getStringExtra("longitude");
        latitude= getIntent().getStringExtra("latitude");
        address=getIntent().getStringExtra("address");
        descripton=getIntent().getStringExtra("descripton");
        heading= getIntent().getStringExtra("heading");
        image=getIntent().getStringExtra("image");
        imgDetailed=findViewById(R.id.imgDetail);
        tvDetailedHeading=findViewById(R.id.tvDetailedHeading);
        tvDetailedDescription=findViewById(R.id.tvDetailedDescription);
        btnMap=findViewById(R.id.btnMaps);
        RequestOptions options = new RequestOptions().centerCrop().timeout(7500);
        Glide.with(DetailsScreen.this).load(image).placeholder(R.drawable.giphy).apply(options).into(imgDetailed);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tvDetailedHeading.setText(heading);
        tvDetailedDescription.setText(descripton);

        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MapsActivity.class);
                intent.putExtra("longitude", longitude);
                intent.putExtra("latitude",latitude);
                intent.putExtra("address", address);
                startActivity(intent);
            }
        });
    }
}
