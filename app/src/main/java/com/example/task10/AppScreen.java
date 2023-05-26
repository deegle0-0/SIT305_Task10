package com.example.task10;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task10.Adapters.TrucksAdapter;
import com.example.task10.Adapters.TrucksViewModel;
import com.example.task10.data.Trucks;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AppScreen extends AppCompatActivity {


    private TrucksViewModel trucksViewModel;

    FloatingActionButton floatingActionButton;

    TrucksAdapter trucksAdapter;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_screen);

        recyclerView = findViewById(R.id.recyclerview);

        floatingActionButton = findViewById(R.id.fab);

        trucksViewModel = new ViewModelProvider(this).get(TrucksViewModel.class);

        trucksAdapter = new TrucksAdapter(new TrucksAdapter.TruckDiff(),this,trucksViewModel);

        recyclerView.setAdapter(trucksAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppScreen.this, AddTrucksActivity.class);
                startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                Trucks truck = new Trucks(0, data.getStringExtra("add_value1"),
                        data.getStringExtra("add_value2"),data.getStringExtra("add_value3"),
                        data.getStringExtra("add_value4"));
                trucksViewModel.insert(truck);
            }
            else {
                Toast.makeText(AppScreen.this, "Not SAVED", Toast.LENGTH_SHORT).show();
            }
        }
        else if(requestCode == 2){
            if(resultCode == RESULT_OK){
                Trucks truck =  new Trucks(Integer.parseInt(data.getStringExtra("id")),
                        data.getStringExtra("update_value1"),
                        data.getStringExtra("update_value2"),
                        data.getStringExtra("update_value3"),
                        data.getStringExtra("update_value4"));
                trucksViewModel.update(truck);
                Toast.makeText(AppScreen.this, "UPDATED", Toast.LENGTH_SHORT).show();

            }
            else {
                Toast.makeText(AppScreen.this, "Not UPDATED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        trucksViewModel.getTrucksAvail().observe(this, trucks -> {
            //update the cached copy of the words in the adapter
            trucksAdapter.submitList(trucks);
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);

        return true;
    }

        @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.home)
        {
            return true;
        }
        else if(id == R.id.account)
        {
            return true;
        }
        else if(id == R.id.orders)
        {
            return true;
        }
        else{
            return super.onOptionsItemSelected(item);
        }
    }
}