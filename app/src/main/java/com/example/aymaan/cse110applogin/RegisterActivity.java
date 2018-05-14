package com.example.aymaan.cse110applogin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.content.Intent;
import android.support.v7.widget.CardView;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText etRegisterEmail = (EditText) findViewById(R.id.etRegisterEmail);
        final CardView cRegisterLogin = (CardView) findViewById(R.id.cRegisterLogin);
        final CardView cRegisterRegister = (CardView) findViewById(R.id.cRegisterRegister);

        cRegisterRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent cRegisterRegisterIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                RegisterActivity.this.startActivity(cRegisterRegisterIntent);
            }
        });

        cRegisterLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent cRegisterLoginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                RegisterActivity.this.startActivity(cRegisterLoginIntent);
            }
        });
    }
}
