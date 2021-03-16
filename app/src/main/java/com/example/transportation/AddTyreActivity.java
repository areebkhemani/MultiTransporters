package com.example.transportation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.InputStream;
import java.util.Random;

public class AddTyreActivity extends AppCompatActivity {

    TextView t1,t2,t3, t4, t5;
    DatabaseReference dbref;
    long root_id = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tyre);

            dbref = FirebaseDatabase.getInstance().getReference("Tyre");
            dbref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists())
                    {
                        root_id = (snapshot.getChildrenCount());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });





    }




    public void process(View view)
    {

        t1= findViewById(R.id.name);
        t2= findViewById(R.id.vehicle);
        t3= findViewById(R.id.tyre);
        t4= findViewById(R.id.ammount);
        t5= findViewById(R.id.balance);



        if(t1.length()== 0 )
        {
            t1.setError("Field can't be empty");
        }
        else if(t2.length()== 0)
        {
            t2.setError("Field can't be empty");
        }
        else if(t3.length() == 0)
        {
            t3.setError("Field can't be empty");
        }
        else if(t4.length() == 0)
        {
            t4.setError("Field cant be empty");
        }

        else if(t5.length() == 0)
        {
            t5.setError("Field cant be empty");
        }
        else
        {



            final String Name =t1.getText().toString().trim();
            final String Vehicle =t2.getText().toString().trim();
            final String Tyre =t3.getText().toString().trim();
            final String Ammount = t4.getText().toString().trim();
            final String Balance = t5.getText().toString().trim();

            //FirebaseDatabase db=FirebaseDatabase.getInstance();
             //dbref=db.getReference("Tyre");
            tyredataholder obj=new tyredataholder(Name,Vehicle,Tyre, Ammount, Balance);
            dbref.child(String.valueOf(root_id + 1)).setValue(obj);


            t1.setText("");
            t2.setText("");
            t3.setText("");
            t4.setText("");
            t5.setText("");

            Toast.makeText(getApplicationContext(), "Value Inserted",Toast.LENGTH_LONG).show();
            Intent intToHome = new Intent(AddTyreActivity.this, TyreActivity.class);
            startActivity(intToHome);
            finish();


        } //end of else statement



    }



}