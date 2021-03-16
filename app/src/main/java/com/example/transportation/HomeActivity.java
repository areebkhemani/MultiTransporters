package com.example.transportation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class HomeActivity extends AppCompatActivity {
    public static final String FINAL_USER = "FINAL_USER";


    ImageButton btnDrivers;
    ImageButton btnPayments;
    ImageButton btnTyre, btnSpareParts, btnPayDues, btnPetrol; // onClick has been set for the unused ones..!
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final String username = getIntent().getStringExtra("keyname");




        btnDrivers = findViewById(R.id.imageButton);
        btnDrivers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intToHome = new Intent(HomeActivity.this, DriverActivity.class);
                startActivity(intToHome);
            }
        });

        btnPayments = findViewById(R.id.imageButton1);

            btnPayments.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intToHome = new Intent(HomeActivity.this, PaymentActivity.class);
                    intToHome.putExtra("keyname", username);
                    startActivity(intToHome);
                }
            });

        btnTyre = findViewById(R.id.img_Tyres);
        btnTyre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent abc = new Intent(HomeActivity.this, TyreActivity.class);
                startActivity(abc);

            }
        });




    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Exit..")
                .setMessage("Are you sure you want to close this Application?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        System.exit(1);
                        //finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }


    public void OpenComingSoon(View view) {
        Intent go = new Intent(this, ComingSoon.class);
        startActivity(go);
    }
}