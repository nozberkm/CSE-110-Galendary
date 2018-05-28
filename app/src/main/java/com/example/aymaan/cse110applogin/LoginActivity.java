package com.example.aymaan.cse110applogin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.content.Intent;
import android.widget.TextView;
import android.view.View;
import android.support.v7.widget.CardView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText etLoginEmail = (EditText) findViewById(R.id.etLoginEmail);
        final EditText etLoginPassword = (EditText) findViewById(R.id.etLoginPassword);
        final TextView tLoginRecovery = (TextView) findViewById(R.id.tLoginRecovery);
        final CardView cLoginLogin = (CardView) findViewById(R.id.cLoginLogin);
        final CardView cLoginRegister = (CardView) findViewById(R.id.cLoginRegister);

        cLoginRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent cLoginRegisterIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(cLoginRegisterIntent);
            }
        });

        tLoginRecovery.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent tLoginRecoveryIntent = new Intent(LoginActivity.this, RecoveryActivity.class);
                LoginActivity.this.startActivity(tLoginRecoveryIntent);
            }
        });

        cLoginLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent cLoginLoginIntent = new Intent(LoginActivity.this, SuccessActivity.class);
                LoginActivity.this.startActivity(cLoginLoginIntent);
            }
        });


    }
}
