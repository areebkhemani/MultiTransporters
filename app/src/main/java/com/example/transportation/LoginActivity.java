package com.example.transportation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.view.View.*;

public class LoginActivity extends AppCompatActivity {
    public static final String FINAL_NAME = "FINAL_NAME";


    EditText emailId, password;
    Button btnSignIn;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;



      private CheckBox mCheckBoxRemember;
      private  SharedPreferences mPrefs, pref;
      private static final String PREFS_NAME = "PrefsFile";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPrefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);


        mFirebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.txt_Username);
        password = findViewById(R.id.txt_Password);
        btnSignIn = findViewById(R.id.btn_login);
        mCheckBoxRemember = (CheckBox) findViewById(R.id.checkBox);





        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if( mFirebaseUser != null ){
                    Toast.makeText(LoginActivity.this,"You are logged in",Toast.LENGTH_SHORT).show();
                    HomeIntent();
                }
                else{
                    Toast.makeText(LoginActivity.this,"Please Login",Toast.LENGTH_SHORT).show();
                }
            }
        };

        btnSignIn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = emailId.getText().toString();
                final String pwd = password.getText().toString();
                if (email.isEmpty()) {
                    emailId.setError("Please enter email id");
                    emailId.requestFocus();
                } else if (pwd.isEmpty()) {
                    password.setError("Please enter your password");
                    password.requestFocus();
                } else if (email.isEmpty() && pwd.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Fields Are Empty!", Toast.LENGTH_SHORT).show();
                } else if (!(email.isEmpty() && pwd.isEmpty())) {
                    mFirebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Email/Password Incorrect", Toast.LENGTH_SHORT).show();
                            }
                            else if(mCheckBoxRemember.isChecked())
                            {
                                Boolean boolischecked = mCheckBoxRemember.isChecked();
                                SharedPreferences.Editor editor = mPrefs.edit();
                                editor.putString("pref_name", email);
                                editor.putString("pref_pass", pwd);
                                editor.putBoolean("pref_check", boolischecked);
                                editor.apply();


                                HomeIntent();

                            }
                            else{
                                mPrefs.edit().clear().apply();
                                HomeIntent();
                            }

                        }
                    });
                } else {
                    Toast.makeText(LoginActivity.this, "Error Occurred!", Toast.LENGTH_SHORT).show();

                }

            }


        });

            getPreferencesData();

    }

    private void getPreferencesData() {
        SharedPreferences sp = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        if (sp.contains("pref_name")){
            String u = sp.getString("pref_name", "not found");
            emailId.setText(u.toString());
        }
        if(sp.contains("pref_pass")){
            String p = sp.getString("pref_pass", "not found");
              password.setText(p.toString());

        }
        if(sp.contains("pref_check")){
            Boolean b = sp.getBoolean("pref_check", false);
            mCheckBoxRemember.setChecked(b);



        }
    }
    public void HomeIntent(){
       // throw new RuntimeException("CRASH CHECK..!");
        String user = emailId.getText().toString().trim();

        pref = getSharedPreferences("user_details",MODE_PRIVATE);
        SharedPreferences.Editor editor1 = pref.edit();
        editor1.putString("StoredUsername",user);
        editor1.commit();


        Intent intToHome = new Intent(LoginActivity.this, HomeActivity.class);
        intToHome.putExtra("keyname", user);
        startActivity(intToHome);

        // passing it to add payment activity

    }

}
