package com.example.aymaan.cse110applogin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.widget.CardView;

public class RecoveryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery);

        final EditText etRecoveryEmail = (EditText) findViewById(R.id.etRecoveryEmail);
        final CardView cRecoveryRecovery = (CardView) findViewById(R.id.cRecoveryRecovery);

        cRecoveryRecovery.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent bRecoveryRecoveryIntent = new Intent(RecoveryActivity.this, LoginActivity.class);
                RecoveryActivity.this.startActivity(bRecoveryRecoveryIntent);
            }
        });
    }
}
