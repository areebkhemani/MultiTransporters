package com.example.transportation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class TyreActivity extends AppCompatActivity {

    RecyclerView recview;
    tyreadapter adapter;



    FloatingActionButton floatingButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tyre);

        recview=(RecyclerView)findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(this ));

        FirebaseRecyclerOptions<tyremodel> options =
                new FirebaseRecyclerOptions.Builder<tyremodel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Tyre"), tyremodel.class)
                        .build();


        adapter = new tyreadapter(options);
        recview.setAdapter(adapter);

        floatingButton = findViewById(R.id.floatingActionButton);

        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intToHome = new Intent(TyreActivity.this, AddTyreActivity.class);
                startActivity(intToHome);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.searchmenu, menu);
        MenuItem item = menu.findItem(R.id.search);

        SearchView searchview= (SearchView) item.getActionView();
        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                processsearch(s);


                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                processsearch(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


    private void processsearch(String s){

        FirebaseRecyclerOptions<tyremodel> options =
                new FirebaseRecyclerOptions.Builder<tyremodel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Tyre").orderByChild("name").startAt(s).endAt(s+ "\uf8ff"), tyremodel.class)
                        .build();

        adapter=new tyreadapter(options);
        adapter.startListening();
        recview.setAdapter(adapter);

    }
}