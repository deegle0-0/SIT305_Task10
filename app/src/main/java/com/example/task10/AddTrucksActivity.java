package com.example.task10;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.task10.data.Trucks;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

public class AddTrucksActivity extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;

    EditText name,number;
    Button add,dateButton,timeButton;
    int hour,min;

    String location2;
    Double lat,lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trucks);

        name = findViewById(R.id.editTextName);
        number = findViewById(R.id.phoneNoInput);
        initDatePicker();
        dateButton = findViewById(R.id.date);
        timeButton = findViewById(R.id.selectTime);


        add = findViewById(R.id.button);

        Places.initialize(getApplicationContext(), BuildConfig.API_KEY);// can add api key regularly or buildConfig.apikey

        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment2);

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME,Place.Field.LAT_LNG));

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                // TODO: Get info about the selected place.
                location2 = place.getName();
                lat = place.getLatLng().latitude;
                lon = place.getLatLng().longitude;

                Log.i("Tag", "Place: " + place.getName() + ", " + place.getId());
                Log.i("Tag", "Lat Long: " + lat + ", " + lon);

            }
            @Override
            public void onError(@NonNull Status status) {
                // TODO: Handle the error.
                Log.i("Tag", "An error occurred: " + status);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                if(TextUtils.isEmpty(name.getText())){
                    setResult(RESULT_CANCELED, intent);
                }
                else {

                    String nameValue = name.getText().toString();
                    String descValue = dateButton.getText().toString();
                    String num = number.getText().toString();
                    intent.putExtra("add_value1", nameValue);
                    intent.putExtra("add_value2", descValue);
                    intent.putExtra("add_value3", location2);
                    intent.putExtra("add_value4", num);
                    setResult(RESULT_OK, intent);
                }

                finish();
            }
        });
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);

            }
        };
        Calendar cal =Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_DARK;

        datePickerDialog = new DatePickerDialog(this,style,dateSetListener,year,month,day);


    }
    private String makeDateString(int day, int month, int year)
    {
        return month+ " " + day + " " +year;
    }

    public void popTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                hour = hourOfDay;
                min = minute;
                timeButton.setText(String.format(Locale.getDefault(),
                        "%02d:%02d",hour,min));
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,onTimeSetListener,hour,min,true);

        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    public void openDatePicker(View view) {

        datePickerDialog.show();
    }
}