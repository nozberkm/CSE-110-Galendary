package com.example.aymaan.cse110applogin;


import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.support.v7.widget.CardView;
import com.example.jeff.database_access.*;

public class RegisterActivity2 extends AppCompatActivity {

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


        setContentView(R.layout.activity_register2);

        final EditText etRegister2Email = (EditText) findViewById(R.id.etRegister2Email);
        final EditText etRegister2Password = (EditText) findViewById(R.id.etRegister2Password);
        final EditText etRegister2Password2 = (EditText) findViewById(R.id.etRegister2Password2);
        final CardView cRegister2Register = (CardView) findViewById(R.id.cRegister2Register);
        final EditText etRegister2Error = (EditText) findViewById(R.id.etRegister2Error);

        cRegister2Register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                String username = etRegister2Email.getText().toString();
                String password1 = null;
                String password2 = null;
                try {
                    password1 = Hashing.SHA1(etRegister2Password.getText().toString());
                    password2 = Hashing.SHA1(etRegister2Password2.getText().toString());
                }
                catch (Exception e) {
                    Log.e("SHA1",e.getMessage());
                }


                if(password1 == null || password2==null || !(password1.equals(password2))) {
                    etRegister2Error.setText("Passwords do not match");

                }
                else if (username.equals("") && (etRegister2Password.getText().toString().equals("") || etRegister2Password2.getText().toString().equals(""))) {
                    etRegister2Error.setText("Username and Password required");
                }
                else if (etRegister2Password.getText().toString().equals("") || etRegister2Password2.getText().toString().equals("")) {
                    etRegister2Error.setText("Password can not be empty");
                }
                else if (username.equals("")) {
                    etRegister2Error.setText("Username can not be empty");
                }
                else{
                    UserObject user = new UserObject(username,password1);
                    UserObject db_user = user.createInDatabase();
                    if(db_user == null){
                        // Failed to create, no internet or already exists
                        etRegister2Error.setText("No Internet or Account Already Exists");
                    } else {
                        Intent cRegister2RegisterIntent = new Intent(RegisterActivity2.this, LoginActivity.class);
                        RegisterActivity2.this.startActivity(cRegister2RegisterIntent);

                    }
                }

            }
        });


    }
}
