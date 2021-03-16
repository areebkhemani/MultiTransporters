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

public class myadapter2 extends FirebaseRecyclerAdapter<model2, myadapter2.myviewholder> {
    public myadapter2(@NonNull FirebaseRecyclerOptions<model2> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final myadapter2.myviewholder holder, final int position, @NonNull final model2 model) {


        holder.name.setText(model.getName());
        holder.advance.setText(model.getAdvance());
        holder.fuel.setText(model.getFuel());
        holder.tyre.setText(model.getTyre());
        holder.months.setText(model.getMonths());
        holder.lastedit.setText(model.getLastedit());


        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus=DialogPlus.newDialog(holder.name.getContext())
                        .setContentHolder(new ViewHolder(R.layout.dialogcontent2))
                        .setExpanded(true, 1600)
                        .create();

                View myview = dialogPlus.getHolderView();
                final EditText name = myview.findViewById(R.id.uname1);
                final EditText advance= myview.findViewById(R.id.uadvance1);
                final EditText fuel= myview.findViewById(R.id.ufuel1);
                final EditText tyre = myview.findViewById(R.id.utyre1);
                final EditText months = myview.findViewById(R.id.umonth1);
                final EditText lastedit = myview.findViewById(R.id.ulastedit1);
                Button submit = myview.findViewById(R.id.submit1);

                name.setText(model.getName());
                advance.setText(model.getAdvance());
                fuel.setText(model.getFuel());
                tyre.setText(model.getTyre());
                months.setText(model.getMonths());
                lastedit.setText(model.getLastedit());
                lastedit.setEnabled(false);

                dialogPlus.show();

                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Map<String, Object> map= new HashMap<>();
                            map.put("name", name.getText().toString());
                            map.put("advance", advance.getText().toString());
                            map.put("fuel", fuel.getText().toString());
                            map.put("tyre", tyre.getText().toString());
                            map.put("months", months.getText().toString());
                            map.put("lastedit", lastedit.getText().toString());


                            FirebaseDatabase.getInstance().getReference().child("Advance").child(getRef(position).getKey())
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
                FirebaseDatabase.getInstance().getReference().child("Advance").child(getRef(position).getKey()).removeValue();


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
    public myadapter2.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow2, parent, false);
        return new myviewholder(view);



    }

    class myviewholder extends RecyclerView.ViewHolder{

        TextView name, advance, fuel, tyre, months, lastedit;
        ImageView edit, delete;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            name =itemView.findViewById(R.id.nametext);
            advance =itemView.findViewById(R.id.coursetext);
            tyre =itemView.findViewById(R.id.companytext);
           fuel =itemView.findViewById(R.id.emailtext);
           months = itemView.findViewById(R.id.monthstext);
           lastedit = itemView.findViewById(R.id.lastedittext);

           edit = itemView.findViewById(R.id.editicon);
           delete =itemView.findViewById(R.id.deleteicon);


        }
    }

}
