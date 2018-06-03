package com.example.aymaan.cse110applogin;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.content.Intent;
import android.widget.TextView;
import android.view.View;
import android.support.v7.widget.CardView;

import com.example.jeff.database_access.UserObject;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Shite patch
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here
        }

        setContentView(R.layout.activity_login);


        final EditText etLoginEmail = (EditText) findViewById(R.id.etLoginEmail);
        final EditText etLoginPassword = (EditText) findViewById(R.id.etLoginPassword);
        final TextView tLoginRecovery = (TextView) findViewById(R.id.tLoginRecovery);
        final CardView cLoginLogin = (CardView) findViewById(R.id.cLoginLogin);
        final CardView cLoginRegister = (CardView) findViewById(R.id.cLoginRegister);
        final EditText etLoginError = (EditText) findViewById(R.id.etLoginError);



        cLoginRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent cLoginRegisterIntent = new Intent(LoginActivity.this, RegisterActivity2.class);
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
                String username = etLoginEmail.getText().toString();
                String password = null;
                try {
                    password = Hashing.SHA1(etLoginPassword.getText().toString());
                }
                catch (Exception e) {
                    Log.e("SHA1",e.getMessage());
                }

                UserObject user = new UserObject(username, password);
                //etLoginError.setText(username);
                if (etLoginPassword.getText().toString().equals("")) {
                    etLoginError.setText("Password can not be empty");
                }
                else if (username.equals("")) {
                    etLoginError.setText("Username can not be empty");
                }

                else if(user.fetchFromDatabase() == null){
                    //login failed
                    etLoginError.setText("Login Failure");
                }
                else {
                    //login success
                    Intent cLoginLoginIntent = new Intent(LoginActivity.this, Home.class);
                    LoginActivity.this.startActivity(cLoginLoginIntent);
                    user.synchronize();
                    Hashing.global_user = user;
                }



            }
        });


    }
}
