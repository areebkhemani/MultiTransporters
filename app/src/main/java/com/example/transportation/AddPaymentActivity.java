package com.example.transportation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class AddPaymentActivity extends AppCompatActivity {

    EditText p1, p2, p3, p4, p5, p6;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    SharedPreferences prf;

    long id = 0;
    DatabaseReference node;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment);

        //Id auto increment
        node = FirebaseDatabase.getInstance().getReference("Advance");

        node.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    id = (snapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        // EDITED BY SAMAN

        p6 = (EditText) findViewById(R.id.p6);
         String user = getIntent().getStringExtra("keyname");

        prf = getSharedPreferences("user_details",MODE_PRIVATE);


        p6.setText(prf.getString("StoredUsername", null));
        p6.setEnabled(false);

        p5 = findViewById(R.id.p5);
        p5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AddPaymentActivity.this, android.R.style.Widget, mDateSetListener, year, month, day);
                //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;
                String date = day + "/" + month + "/" + year;
                p5.setText(date);
            }
        };
    }

    public void payment(View view) {

        p1 = (EditText) findViewById(R.id.p1);
        p2 = (EditText) findViewById(R.id.p2);
        p3 = (EditText) findViewById(R.id.p3);
        p4 = (EditText) findViewById(R.id.p4);
        p5 = (EditText) findViewById(R.id.p5);
        p6 = (EditText) findViewById(R.id.p6);

        if (p1.length() == 0) {
            p1.setError("Field can't be empty");

        } else if (p2.length() == 0) {
            p2.setError("Field can't be empty");
        } else if (p3.length() == 0) {
            p3.setError("Field can't be empty");
        } else if (p4.length() == 0) {
            p4.setError("Field can't be empty");
        } else if (p5.length() == 0) {
            p5.setError("Field can't be empty");
        } else if (p6.length() == 0) {
            p6.setError("Field can't be empty");
        } else {
            String name = p1.getText().toString().trim();
            String advance = p2.getText().toString().trim();
            String fuel = p3.getText().toString().trim();
            String tyre = p4.getText().toString().trim();
            String months = p5.getText().toString().trim();
            String lastedit = p6.getText().toString().trim();


            paymentdataholder obj = new paymentdataholder(name, advance, fuel, tyre, months, lastedit);

            //FirebaseDatabase db = FirebaseDatabase.getInstance();
            // node = db.getReference("Advance");

            node.child(String.valueOf(id + 1)).setValue(obj);

            p1.setText("");
            p2.setText("");
            p3.setText("");
            p4.setText("");
            p5.setText("");
            p6.setText("");
            Toast.makeText(getApplicationContext(), "Value Inserted", Toast.LENGTH_LONG).show();
            Intent intToHome = new Intent(AddPaymentActivity.this, PaymentActivity.class);
            startActivity(intToHome);
            finish();

        }  //end of else statement
    }
}