package com.example.aymaan.cse110applogin;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PasswordChange extends AppCompatActivity {
    private android.support.v7.widget.Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);

        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);

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
                if(newPass.equals(reenterNewPass)) {
                    if(LoginActivity.userLogin.changePassword(currentPass,newPass)) {
                        finish();
                    }
                    else {
                        Snackbar.make(view, "Incorrect current password", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}