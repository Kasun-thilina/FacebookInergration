package com.kasunthilina.elegentmedia;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private LoginButton loginButton;
    private CircleImageView imgUserProfile;
    private TextView txtUserName;

    private CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
       /* try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.kasunthilina.elegentmedia",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }*/
        loginButton=findViewById(R.id.login_button);
        imgUserProfile=findViewById(R.id.imgUserProfile);
        txtUserName=findViewById(R.id.tvUserName);
        checkLoginStatus();
        //Handling the callback from loginbutton with facebook app
        callbackManager=CallbackManager.Factory.create();
        //defining what to get from the facebook api
        loginButton.setReadPermissions(Arrays.asList("email","public_profile"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
              final Intent mainActivityIntent=new Intent(getBaseContext(),MainActivity.class);
             // mainActivityIntent.putExtra("username",username);
             // mainActivityIntent.putExtra("profileURL",imageURL);
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        startActivity(mainActivityIntent);
                        finish();
                    }
                },  1000);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    //Passing the login Results to callbackManager
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    //AccessToken Tracker
    AccessTokenTracker tokenTracker=new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if (currentAccessToken==null){
                txtUserName.setText("");
                imgUserProfile.setImageResource(0);
                Toast.makeText(getApplicationContext(),"User Logged Out",Toast.LENGTH_LONG).show();
            }
            else
            {
                loadProfile(currentAccessToken);
            }
        }
    };

    private void loadProfile(AccessToken accessToken){
        SharedPreferences sharedpreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedpreferences.edit();
        //Request to the faceebook graph API to read data from platform
        GraphRequest request=GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    String fName=object.getString("first_name");
                    String lName=object.getString("last_name");
                    String email=object.getString("email");
                    String id=object.getString("id");
                    Log.d(TAG, "id: "+id );
                    String imageURL="http://graph.facebook.com/"+id+"/picture?type=normal";

                    String username=fName+" "+lName;

                    /**Saving the values in SharedPreferences for easy login*/
                    editor.putString("username",username);
                    editor.putString("imageURL",imageURL);
                    editor.commit();

                    txtUserName.setText("Name:"+username);
                    //getting the image using glide library
                    RequestOptions requestOptions=new RequestOptions();
                   // requestOptions.dontAnimate();
                    Glide.with(LoginActivity.this).load(imageURL).into(imgUserProfile);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        Bundle parameters=new Bundle();
        parameters.putString("fields","first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void checkLoginStatus(){
        if (AccessToken.getCurrentAccessToken()!=null){
            loadProfile(AccessToken.getCurrentAccessToken());
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
        }
    }
}
