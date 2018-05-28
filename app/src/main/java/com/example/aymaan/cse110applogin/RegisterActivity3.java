package com.example.aymaan.cse110applogin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.content.Intent;
import android.support.v7.widget.CardView;

public class RegisterActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register3);

        final EditText etRegister3Email = (EditText) findViewById(R.id.etRegister3Email);
        final CardView cRegister3Login = (CardView) findViewById(R.id.cRegister3Login);
        final CardView cRegister3Register = (CardView) findViewById(R.id.cRegister3Register);

        cRegister3Register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent cRegister3RegisterIntent = new Intent(RegisterActivity3.this, RegisterActivity2.class);
                RegisterActivity3.this.startActivity(cRegister3RegisterIntent);
            }
        });

        cRegister3Login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent cRegister3LoginIntent = new Intent(RegisterActivity3.this, RegisterActivity.class);
                RegisterActivity3.this.startActivity(cRegister3LoginIntent);
            }
        });
    }
}
