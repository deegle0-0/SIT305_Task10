package com.example.task10;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.task10.Adapters.TrucksViewModel;
import com.example.task10.data.Trucks;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;

public class UpdateActivity extends AppCompatActivity {

    EditText model, date,phoneNo;

    Button updateButton;

    TrucksViewModel trucksViewModel;
    Trucks trucks;

    String location2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        model = findViewById(R.id.editTextTextPersonName);
        date = findViewById(R.id.editTextTextEmailAddress);

        phoneNo = findViewById(R.id.phone);
        updateButton = findViewById(R.id.button);

        trucks = (Trucks) getIntent().getSerializableExtra("model");
        trucksViewModel = new ViewModelProvider(this).get(TrucksViewModel.class);

        model.setText(trucks.getModel());
        date.setText((CharSequence) trucks.getDates());


        Places.initialize(getApplicationContext(), BuildConfig.API_KEY);// can add api key regularly or buildConfig.apikey

        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment3);

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME,Place.Field.LAT_LNG));

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                // TODO: Get info about the selected place.
                location2 = place.getName();

                Log.i("Tag", "Place: " + place.getName() + ", " + place.getId());

            }
            @Override
            public void onError(@NonNull Status status) {
                // TODO: Handle the error.
                Log.i("Tag", "An error occurred: " + status);
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                int id = trucks.getId();
                String nameValue = model.getText().toString();
                String descValue = date.getText().toString();
                String num = phoneNo.getText().toString();

                intent.putExtra("id", String.valueOf(id));
                intent.putExtra("update_value1", nameValue);
                intent.putExtra("update_value2", descValue);
                intent.putExtra("update_value3", location2);
                intent.putExtra("update_value4", num);
                setResult(RESULT_OK, intent);
                finish();

            }
        });
    }
}