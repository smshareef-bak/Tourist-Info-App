package com.smshareef.touristguide.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.smshareef.touristguide.R;
import com.smshareef.touristguide.manager.LoginDataManager;
import com.smshareef.touristguide.manager.OnLoginDataReadyListener;

import static com.smshareef.touristguide.R.id.tv_login_welcome;

public class LoginActivity extends AppCompatActivity implements OnLoginDataReadyListener {

    private EditText mLoginUserIDEt;
    private EditText mLoginPasswordEt;
    private Button mForgotPasswordBt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView mLoginWelcomeTv = (TextView) findViewById(tv_login_welcome);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/NEOTERICc - Regular.ttf");
        mLoginWelcomeTv.setTypeface(typeface);

        mLoginUserIDEt = (EditText) findViewById(R.id.et_login_userid);
        mLoginPasswordEt = (EditText) findViewById(R.id.et_login_password);
        Button mLoginBt = (Button) findViewById(R.id.bt_login);
        mForgotPasswordBt = (Button) findViewById(R.id.bt_forgot_password);

        mLoginBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = mLoginUserIDEt.getText().toString();
                String password = mLoginPasswordEt.getText().toString();
                loadData();
                if(userID.equals(uid)) {
                    if(password.equals(pwd)) {
                        Toast.makeText(LoginActivity.this,"Successfully Logged in...",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        LoginActivity.this.startActivity(intent);
                    } else if(password.equals("")){
                        Toast.makeText(LoginActivity.this,"Please enter password",Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(LoginActivity.this,"You have entered wrong password",Toast.LENGTH_LONG).show();
                    }
                } else if(userID.equals("")){
                    Toast.makeText(LoginActivity.this,"Please enter User ID",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(LoginActivity.this,"You have entered wrong User ID",Toast.LENGTH_LONG).show();
                }
            }
        });

        mForgotPasswordBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
                Toast.makeText(LoginActivity.this,"User ID is " + uid + " and Password is " + pwd,Toast.LENGTH_LONG).show();
            }
        });
    }

    String uid, pwd;
    private void loadData() {
        LoginDataManager loginDataManager = new LoginDataManager(this);
        loginDataManager.getLoginData();
    }

    @Override
    public void onLoginDataReady(String userID, String password) {
        uid = userID;
        pwd = password;
    }
}
