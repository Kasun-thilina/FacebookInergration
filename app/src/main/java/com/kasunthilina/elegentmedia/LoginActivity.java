package com.kasunthilina.elegentmedia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.login.widget.LoginButton;

import de.hdodenhof.circleimageview.CircleImageView;

public class LoginActivity extends AppCompatActivity {

    private LoginButton loginButton;
    private CircleImageView imgUserProfile;
    private TextView txtUserName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton=findViewById(R.id.login_button);
        imgUserProfile=findViewById(R.id.imgUserProfile);
        txtUserName=findViewById(R.id.txtUserName);
    }
}
