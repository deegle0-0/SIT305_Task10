package com.example.task10;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.task10.Adapters.UsersViewModel;
import com.example.task10.data.UserRepository;

public class MainActivity extends AppCompatActivity {

    EditText username,password;
    Button login,signup;

    SharedPreferences sharedPreferences;
    private UsersViewModel usersViewModel;

    UserRepository userRepository;
    Application application;

    boolean loginpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.LoginButton);
        signup = findViewById(R.id.SignUpButton);

        usersViewModel = new ViewModelProvider(this).get(UsersViewModel.class);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this,signupActivity.class);

                startActivity(myIntent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(usersViewModel.loginUser(username.getText().toString(),password.getText().toString()))
                {
                    Log.v("Login Status","Success");
                    Intent myIntent = new Intent(MainActivity.this, AppScreen.class);
                    startActivity(myIntent);
                }
                else {
                    Log.v("Login Status","Failed");
                    Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}