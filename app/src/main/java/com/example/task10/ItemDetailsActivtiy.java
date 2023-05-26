package com.example.task10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.task10.Adapters.TrucksViewModel;
import com.example.task10.MapsThings.FetchURL;
import com.example.task10.MapsThings.TaskLoadedCallback;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ItemDetailsActivtiy extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback {

    Button book,call;
    TextView location;

    String SECRET_KEY ="sk_test_51Ll4AiHU2L95eZkwHXbF8dL8aJ3UpnkgLnGTcuKt8rCtsWsA9mxz9DvMpIESgmQ3RAqA3KV724qmhw7BEZmaoKTk00HUDHanK6";
    String PUBLISH_KEY = "pk_test_51Ll4AiHU2L95eZkwmcSWdUP0k05Kxwd8OVzuwarPBqYZxjLskh5pyiJrJhJSPWYxoNpY6Y1fwL3zfcPlT4s86eQt00lEBVOe3d";

    String customerID,ephericalKey,ClientSecret,number;

    PaymentSheet paymentSheet;
    SharedPreferences sharedPreferences;

    private GoogleMap map;
    private TrucksViewModel trucksViewModel;

    MarkerOptions place1,place2;

    Polyline currentLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details_activtiy);

        location = findViewById(R.id.location);
        book = findViewById(R.id.bookbtn);
        call = findViewById(R.id.callbtl);

        trucksViewModel = new ViewModelProvider(ItemDetailsActivtiy.this).get(TrucksViewModel.class);

        MapFragment supportMapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);

        supportMapFragment.getMapAsync(this);

        place1 = new MarkerOptions().position(new LatLng(-37.813629,144.963058)).title(location.getText().toString());
        place2 = new MarkerOptions().position(new LatLng(-37.849920,145.119210)).title("Deakin Uni");

        String url = getUrl(place1.getPosition(),place2.getPosition(),"driving");

        Log.i("URl ", url);

        new FetchURL(this).execute(url,"driving");

        String locationVal = getIntent().getStringExtra("locationVal");
        number = getIntent().getStringExtra("number");
        location.setText(locationVal);
//
        // Stripe
        PaymentConfiguration.init(this,PUBLISH_KEY);

        paymentSheet = new PaymentSheet(this,this::onPaymentResult);

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaymentFlow();
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+number));
                startActivity(intent);
            }
        });


        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://api.stripe.com/v1/customers",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            customerID = object.getString("id");

                            Log.i("Payment","Customer ID done");

                            getEphericalKey();


                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<>();
                headers.put("Authorization","Bearer "+SECRET_KEY);
                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private String getUrl(LatLng position, LatLng position1, String directionMode) {
        String origin = "origin=" + position.latitude + "," + position.longitude;
        String dest = "destination=" + position1.latitude + "," + position1.longitude;

        String mode = "mode=" +directionMode;

        String parameters = origin + "&" + dest + "&" + mode;

        String output = "json";

        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters+ "&key="
                + BuildConfig.MAPS_API_KEY;
        return url;
    }

    private void onPaymentResult(PaymentSheetResult paymentSheetResult) {
        if(paymentSheetResult instanceof PaymentSheetResult.Completed){
            Toast.makeText(this,"Payment Successful",Toast.LENGTH_LONG).show();
        } else if (paymentSheetResult instanceof PaymentSheetResult.Canceled || paymentSheetResult instanceof PaymentSheetResult.Failed){
            Toast.makeText(this,"Payment Canceled",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,"Payment Failed",Toast.LENGTH_LONG).show();
        }

    }

    private void getEphericalKey() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://api.stripe.com/v1/ephemeral_keys",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            ephericalKey = object.getString("id");

                            Log.i("Payment","Epherical thing done");

                            getClientSecret(customerID,ephericalKey);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<>();
                headers.put("Authorization","Bearer "+SECRET_KEY);
                headers.put("Stripe-Version","2022-11-15");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("customer",customerID);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void getClientSecret(String customerID, String ephericalKey) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://api.stripe.com/v1/payment_intents",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            ClientSecret = object.getString("client_secret");
                            Log.i("Payment","Client Secret done");

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<>();
                headers.put("Authorization","Bearer "+SECRET_KEY);
                return headers;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("customer",customerID);
                params.put("amount","10"+"00");
                params.put("currency","eur");
                params.put("automatic_payment_methods[enabled]","true");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void PaymentFlow() {
        paymentSheet.presentWithPaymentIntent(ClientSecret,new PaymentSheet.Configuration("SIT 305 Deakin",
                        new PaymentSheet.CustomerConfiguration(
                                customerID,
                                ephericalKey
                        )));
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        map.addMarker(place1);
        map.addMarker(place2);

    }

    @Override
    public void onTaskDone(Object... values) {
        if(currentLine !=null)
            currentLine.remove();
        currentLine = map.addPolyline((PolylineOptions) values[0]);
    }
}