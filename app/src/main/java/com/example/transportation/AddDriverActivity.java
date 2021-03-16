package com.example.transportation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

public class AddDriverActivity extends AppCompatActivity {

    TextView t1,t2,t3, roll;
    ImageButton img;
    Uri filepath;
    Bitmap bitmap;
    Spinner spinner;
    long id_autoInc = 0;

    DatabaseReference root;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_driver);

        root=FirebaseDatabase.getInstance().getReference("Driver");
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    id_autoInc = (snapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        img= findViewById(R.id.imageButton);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dexter.withActivity(AddDriverActivity.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                Intent intent= new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent, "Select Image"), 1);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();

            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==1 && resultCode==RESULT_OK)
        {
            filepath=data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filepath);
                bitmap= BitmapFactory.decodeStream(inputStream);
                img.setImageBitmap(bitmap);

            }catch(Exception ex){


            }

        }


        super.onActivityResult(requestCode, resultCode, data);
    }

    public void process(View view)
    {

        //roll = findViewById(R.id.roll);
        t1= findViewById(R.id.t1);
        t2= findViewById(R.id.t2);
        t3= findViewById(R.id.t3);
       // t4= findViewById(R.id.t4);

        spinner = findViewById(R.id.spinner);

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
//        else if(roll.length() == 0)
//        {
//            roll.setError("Field can't be empty");
//        }
        else
        {
            ProgressDialog dialog= new ProgressDialog(this);
            dialog.setTitle("File Uploader");
            dialog.show();

           // final String ID = roll.getText().toString().trim();
            final String Name =t1.getText().toString().trim();
            final String CNIC =t2.getText().toString().trim();
            final String Vehicle =t3.getText().toString().trim();
            final String Company =spinner.getSelectedItem().toString().trim();

            FirebaseStorage storage= FirebaseStorage.getInstance();
            final StorageReference uploader= storage.getReference("Image1"+ new Random().nextInt(50));

            uploader.putFile(filepath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                   // FirebaseDatabase db=FirebaseDatabase.getInstance();
                                  //   root=db.getReference("Driver"); //root declared on the top..!
                                    dataholder obj=new dataholder(Name,CNIC,Vehicle,Company, uri.toString());
                                    root.child(String.valueOf(id_autoInc + 1)).setValue(obj);


                                    t1.setText("");
                                    t2.setText("");
                                    t3.setText("");
                                    // t4.setText("");
                                   // roll.setText("");
                                    img.setImageResource(R.drawable.profile_avatar);

                                    Toast.makeText(getApplicationContext(), "Value Inserted",Toast.LENGTH_LONG).show();
                                    Intent intToHome = new Intent(AddDriverActivity.this, DriverActivity.class);
                                    startActivity(intToHome);

                                    finish();


                                }
                            });

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {


                        }
                    });


        } //end of else statement



    }



}