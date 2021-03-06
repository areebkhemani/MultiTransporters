package com.example.transportation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class myadapter extends FirebaseRecyclerAdapter<model, myadapter.myviewholder>

{
 public myadapter(@NonNull FirebaseRecyclerOptions<model> options) {
  super(options);
 }

 @Override
 protected void onBindViewHolder(@NonNull final myviewholder holder, final int position, @NonNull final model model)
 {

  holder.name.setText(model.getName());
  holder.cnic.setText(model.getCnic());
  holder.vehicle.setText(model.getVehicle());
  holder.company.setText(model.getCompany());

  Glide.with(holder.img.getContext()).load(model.getPimage()).into(holder.img);

  holder.edit.setOnClickListener(new View.OnClickListener() {
   @Override
   public void onClick(View view) {
     final DialogPlus dialogPlus=DialogPlus.newDialog( holder.img.getContext())
             .setContentHolder(new ViewHolder(R.layout.dialogcontent))
             .setExpanded(true, 1600)
             .create();


     View myview=dialogPlus.getHolderView();
    final EditText purl=myview.findViewById(R.id.uimgurl0);
    final EditText name=myview.findViewById(R.id.uname0);
    final EditText cnic=myview.findViewById(R.id.ucnic0);
    final EditText vehicle=myview.findViewById(R.id.uvehicle0);
    final EditText company=myview.findViewById(R.id.ucompany0);
    Button submit=myview.findViewById(R.id.submit0);


    purl.setText(model.getPimage());
    name.setText(model.getName());
    cnic.setText(model.getCnic());
    vehicle.setText(model.getVehicle());
    company.setText(model.getCompany());
    purl.setEnabled(false);

    dialogPlus.show();

    submit.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View view) {
        Map<String, Object> map=new HashMap<>();
        map.put("purl", purl.getText().toString());
      map.put("name", name.getText().toString());
      map.put("cnic", cnic.getText().toString());
      map.put("vehicle", vehicle.getText().toString());
      map.put("company", company.getText().toString());

      FirebaseDatabase.getInstance().getReference().child("Driver")
               .child(getRef(position).getKey()).updateChildren(map)
               .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                 dialogPlus.dismiss();
                }
               })

              .addOnFailureListener(new OnFailureListener() {
               @Override
               public void onFailure(@NonNull Exception e) {

                dialogPlus.dismiss();
               }
              });





     }
    });




   }
  });

    holder.delete.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View view) {
      AlertDialog.Builder builder=new AlertDialog.Builder(holder.img.getContext());
      builder.setTitle("Delete Panel");
      builder.setMessage("Are you sure you want to delete?");

      builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
       @Override
       public void onClick(DialogInterface dialogInterface, int i) {

        FirebaseDatabase.getInstance().getReference().child("Driver")
                .child(getRef(position ).getKey()).removeValue();


       }
      });

      builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
       @Override
       public void onClick(DialogInterface dialogInterface, int i) {

       }
      });

      builder.show();


     }
    });

 }

 @NonNull
 @Override
 public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
  View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
  return  new myviewholder(view);



 }

 class myviewholder extends RecyclerView.ViewHolder

 {

  CircleImageView img;
  ImageView edit, delete;
  TextView name,cnic, vehicle, company;
  public myviewholder(@NonNull View itemView) {
   super(itemView);
   img=(CircleImageView) itemView.findViewById(R.id.img1);
   name=(TextView)itemView.findViewById(R.id.nametext);
   vehicle=(TextView)itemView.findViewById(R.id.emailtext);
   company=(TextView)itemView.findViewById(R.id.companytext);
   cnic=(TextView)itemView.findViewById(R.id.coursetext);

    edit=(ImageView)itemView.findViewById(R.id.editicon);
    delete=(ImageView)itemView.findViewById(R.id.deleteicon);
  }
 }

}
