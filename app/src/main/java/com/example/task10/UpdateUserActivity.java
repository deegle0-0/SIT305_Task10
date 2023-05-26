package com.example.task10;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.task10.Adapters.UsersViewModel;
import com.example.task10.data.Users;

public class UpdateUserActivity extends AppCompatActivity {
    EditText name,username,pass,confirmpass;

    Button updateButton;

    UsersViewModel usersViewModel;
    Users users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        name= findViewById(R.id.updateFullName);
        username= findViewById(R.id.updateUsername);
        pass= findViewById(R.id.passwordUpdate);
        confirmpass= findViewById(R.id.confirmPassUpdate);

        users = (Users) getIntent().getSerializableExtra("users");
        usersViewModel = new ViewModelProvider(this).get(UsersViewModel.class);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                int id = users.getId();
                String nameValue = name.getText().toString();
                String usernameVal = username.getText().toString();
                String passVal = pass.getText().toString();
                intent.putExtra("id", String.valueOf(id));
                intent.putExtra("update_value1", nameValue);
                intent.putExtra("update_value2", usernameVal);
                intent.putExtra("update_value3",passVal);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}