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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class tyreadapter extends FirebaseRecyclerAdapter<tyremodel, tyreadapter.myviewholder>

{

    public tyreadapter(@NonNull FirebaseRecyclerOptions<tyremodel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final myviewholder holder, final int position, @NonNull final tyremodel model) {

        holder.name.setText(model.getName());
        holder.vehicle.setText(model.getVehicle());
        holder.tyre.setText(model.getTyre());
        holder.ammount.setText(model.getAmmount());
        holder.balance.setText(model.getBalance());





        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus=DialogPlus.newDialog(holder.name.getContext())
                        .setContentHolder(new ViewHolder(R.layout.dialogcontent3))
                        .setExpanded(true, 1600)
                        .create();

                View myview = dialogPlus.getHolderView();
                final EditText name = myview.findViewById(R.id.uname2);
                final EditText vehicle= myview.findViewById(R.id.uvehicle2);
                final EditText tyre = myview.findViewById(R.id.utyre2);
                final EditText ammount = myview.findViewById(R.id.uammount2);
                final EditText balance = myview.findViewById(R.id.ubalance2);
                Button submit = myview.findViewById(R.id.submit2);

                name.setText(model.getName());
                vehicle.setText(model.getVehicle());
                tyre.setText(model.getTyre());
                ammount.setText(model.getAmmount());
                balance.setText(model.getBalance());
                //lastedit.setEnabled(false);

                dialogPlus.show();

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String, Object> map= new HashMap<>();
                        map.put("name", name.getText().toString());
                        map.put("vehicle", vehicle.getText().toString());
                        map.put("tyre", tyre.getText().toString());
                        map.put("ammount", ammount.getText().toString());
                        map.put("balance", balance.getText().toString());


                        FirebaseDatabase.getInstance().getReference().child("Tyre").child(getRef(position).getKey())
                                .updateChildren(map)
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
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.name.getContext());
                builder.setTitle("Delete Panel");
                builder.setMessage("Sure you want to delete ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("Tyre").child(getRef(position).getKey()).removeValue();


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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow3, parent, false);
        return new myviewholder(view);

    }

    class myviewholder extends RecyclerView.ViewHolder{

        TextView name, vehicle, tyre, ammount, balance;
        ImageView edit, delete;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            name =itemView.findViewById(R.id.nametext);
            vehicle = itemView.findViewById(R.id.vehicletext);
            tyre =itemView.findViewById(R.id.tyretext);
            ammount =itemView.findViewById(R.id.ammounttext);
            balance = itemView.findViewById(R.id.balancetext);


            edit=(ImageView)itemView.findViewById(R.id.editicon);
            delete=(ImageView)itemView.findViewById(R.id.deleteicon);


          //  edit = itemView.findViewById(R.id.editicon);
         //   delete =itemView.findViewById(R.id.deleteicon);


        }
    }


}
