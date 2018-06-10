package com.example.aymaan.cse110applogin;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class PasswordChange extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        final AlertDialog.Builder builder = new AlertDialog.Builder(PasswordChange.this);


        //mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.nav_action);
        //setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        final Button changePass = (Button) findViewById(R.id.changePass);
        final TextView currentPassField = (TextView) findViewById(R.id.currentPassImp);
        final TextView newPassField = (TextView) findViewById(R.id.newPassImp);
        final TextView reenterNewPassField = (TextView) findViewById(R.id.reenterNewPassImp);

        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentPass = currentPassField.getText().toString();
                String newPass = newPassField.getText().toString();
                String reenterNewPass = reenterNewPassField.getText().toString();

                try {
                    currentPass = Hashing.SHA1(currentPass);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                //Check if valid
                if(currentPass == null || currentPass == "" || newPass == null || newPass == "" ||
                        reenterNewPass == null || reenterNewPass == ""){
                    builder.setMessage("Please fill in all fields.");
                    Dialog fillError = builder.create();
                }
                else if(LoginActivity.userLogin.getPasshash() != currentPass){
                    builder.setMessage("Current password Incorrect.");
                    Dialog verifyError = builder.create();
                }
                else if(newPass != reenterNewPass) {
                    builder.setMessage("New password does not match.");
                    Dialog matchError = builder.create();
                }
                else{
                    try {
                        newPass = Hashing.SHA1(newPass);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    //Update password with new hash
                }



                changePass.setBackgroundColor(100);
                finish();
            }
        });





        //TODO:Either remove below or figure out why it is there in the first place
        /*
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Password Change");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        // fab.setOnClickListener(new View.OnClickListener() {

        // @Override
        //   public void onClick(View view) {

        //      Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        //            .setAction("Action", null).show();
*/
    }
    // });

    //}

}
