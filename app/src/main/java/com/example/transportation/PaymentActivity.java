package com.example.transportation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class PaymentActivity extends AppCompatActivity {
    public static final String FINAL_USERNAME = "FINAL_USERNAME";
RecyclerView recview;
    myadapter2 adapter;
FloatingActionButton floatingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        final String username = getIntent().getStringExtra("keyname"); //getting name

    recview = findViewById(R.id.recview);
    recview.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<model2> options =
                new FirebaseRecyclerOptions.Builder<model2>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Advance"), model2.class)
                        .build();

        adapter = new myadapter2(options);
        recview.setAdapter(adapter);

        floatingButton = findViewById(R.id.floatingActionButton);

        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intToHome = new Intent(PaymentActivity.this, AddPaymentActivity.class);
                intToHome.putExtra("keyname" , username);
                startActivity(intToHome);
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}