package com.example.aymaan.cse110applogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.support.v7.widget.CardView;

public class RegisterActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        final EditText etRegister2Password = (EditText) findViewById(R.id.etRegister2Password);
        final EditText etRegister2Password2 = (EditText) findViewById(R.id.etRegister2Password2);
        final CardView cRegister2Register = (CardView) findViewById(R.id.cRegister2Register);

        cRegister2Register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent cRegister2RegisterIntent = new Intent(RegisterActivity2.this, LoginActivity.class);
                RegisterActivity2.this.startActivity(cRegister2RegisterIntent);
            }
        });
    }
}
