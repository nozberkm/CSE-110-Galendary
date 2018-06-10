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

import com.example.jeff.database_access.GroupObject;
import com.example.jeff.database_access.*;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class LoginActivity extends AppCompatActivity {
    public static UserObject userLogin;

    /*@Override
    public void onBackPressed(){
    }*/
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userLogin = null;

        // Shite patch
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here
        }

        if (SaveSharedPreference.getUserName(LoginActivity.this).length() != 0){
            Intent checkLoggedOut = getIntent();
            if(checkLoggedOut != null && checkLoggedOut.getBooleanExtra("logout", false)) {

                SaveSharedPreference.clearUserName(LoginActivity.this);
                SaveSharedPreference.clearPassWord(LoginActivity.this);
            }

            else {
                String uname = SaveSharedPreference.getUserName(LoginActivity.this);
                String pword = SaveSharedPreference.getPassWord(LoginActivity.this);

                Log.d("STORED USER: ", SaveSharedPreference.getUserName(LoginActivity.this));
                Log.d("STORED PASS: ", SaveSharedPreference.getPassWord(LoginActivity.this));

                UserObject user = new UserObject(uname, pword);
                userLogin = user.fetchFromDatabase();
                if(userLogin == null){
                    SaveSharedPreference.clearUserName(LoginActivity.this);
                    SaveSharedPreference.clearPassWord(LoginActivity.this);
                } else {
                    userLogin.synchronize();

                    Intent loggedInPrev = new Intent(LoginActivity.this, Home.class);
                    LoginActivity.this.startActivity(loggedInPrev);
                    finish();
                }
            }
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

                if(username.equals("t")){
//                    try {
//                        password = Hashing.SHA1("t");
//                    } catch (NoSuchAlgorithmException e) {
//                        e.printStackTrace();
//                    } catch (UnsupportedEncodingException e) {
//                        e.printStackTrace();
//                    }

//                    System.err.println("TESTING DELETE USER");

//                    String hash = Hashing.SHA1("poop");
//                    UserObject user = new UserObject("jeffreytcash@gmail.com", )



////                    UserObject user = new UserObject(username, password);
//                    UserObject user = new UserObject("jeff", "not_sha2");
//
//                    if(user.fetchFromDatabase() == null){
//                        System.err.println("SHEEEITTTTT");
//                    }
//                    user.synchronize();
//
//
//                    GroupObject individual_group = user.getIndividualGroup();
//                    for(EntryObject eo : individual_group.getEntries()){
//                        if(eo.getId() == 127){
//                            System.err.println("FOUND IT");
//                            Date test = null;
//                            try {
//                                test =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-06-11 05:01:49");
//                            } catch (ParseException e) {
//                                e.printStackTrace();
//                            }
//                            if(test != null) {
//                                System.err.println("attempting to move it to the 11th");
//                                eo.setStart(test);
//                                eo.setEnd(test);
//                                eo.setTitle("ENTRY HAS BEEN UPDATED");
//                                eo.setDescription("YES, CUNT!");
//                                System.err.println(eo);
//                                boolean status = eo.pushUpdate();
//
//                                System.err.println("status: " + status);
//                            }
//
//
//                        }
//                    }
//                    EntryObject personal_entry = new EntryObject();
//                    personal_entry.setTitle("MOVE THIS TO 6/10");
//                    personal_entry.setStart(new Date());
//                    personal_entry.setEnd(new Date());
//                    personal_entry.setDescription("Please, cunt");
//
//                    individual_group.pushEntry(personal_entry);


//                    System.err.println(user);
//
//                    ArrayList<GroupObject> groups = user.getGroups();
//                    GroupObject test_group = null;
//                    for(GroupObject go : groups){
//                        if(go.getId() == 45) test_group = go;
//                    }
//
//
//                    GroupObject indi_group = test_group;// user.getIndividualGroup();
//System.err.println(indi_group);
//                    EntryObject eo = new EntryObject();
//
////                    eo.setStart(new Date());
//                    eo.setEnd(new Date());
//                    eo.setTitle("TEST ADDING EVENT TO DELETE");
//                    eo.setDescription("PLEASE PLZ DELTE");
//
//                    indi_group.pushEntry(eo);
//
//                    user.synchronize();
//                    System.err.println("eo:");
//                    System.err.println(eo);
//
//                    eo.delete();
//
//
//
//                    GroupObject joined = user.joinGroupByEnrollmentCode("l6k1v6w");
//
//                    System.err.println("Joined:");
//                    System.err.println(joined);




                }


                try {
                    password = Hashing.SHA1(etLoginPassword.getText().toString());
                }
                catch (Exception e) {
                    Log.e("SHA1",e.getMessage());
                }

                UserObject user = new UserObject(username, password);

                userLogin = user.fetchFromDatabase();
                //etLoginError.setText(username);
                if (etLoginPassword.getText().toString().equals("")) {
                    etLoginError.setText("Password can not be empty");
                }
                else if (username.equals("")) {
                    etLoginError.setText("Username can not be empty");
                }

                else if(userLogin == null){
                    //login failed
                    etLoginError.setText("Login Failure");
                }
                else {
                    //login success
                    SaveSharedPreference.setUserName(LoginActivity.this, username);
                    SaveSharedPreference.setPassWord(LoginActivity.this, password);
                    Log.d("STORING USER: ", SaveSharedPreference.getUserName(LoginActivity.this));
                    Log.d("STORING PASS: ", SaveSharedPreference.getPassWord(LoginActivity.this));
                    userLogin.synchronize();
                    Intent cLoginLoginIntent = new Intent(LoginActivity.this, Home.class);
                    LoginActivity.this.startActivity(cLoginLoginIntent);
                    finish();
                    //Hashing.global_user = user;
                }



            }
        });


    }
}
