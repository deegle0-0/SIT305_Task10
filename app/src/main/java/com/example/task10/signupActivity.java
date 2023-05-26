package com.example.task10;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.task10.data.Users;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class signupActivity extends AppCompatActivity {
    EditText fullname,username,password,confirmpassword;
    EditText phoneNo;

    Button signup;
    ImageView uploadImage;
    FloatingActionButton upload;
    private final int request_code =1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        fullname = findViewById(R.id.Full_Name);
        username = findViewById(R.id.userName);
        password = findViewById(R.id.password);
        confirmpassword = findViewById(R.id.confirmPassword);

        phoneNo = findViewById(R.id.phoneNo);
        signup = findViewById(R.id.SignupBtn);
        uploadImage = findViewById(R.id.uploadImage);
        upload = findViewById(R.id.upload);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK);

                gallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallery,request_code);

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                if(TextUtils.isEmpty(fullname.getText())|| TextUtils.isEmpty(username.getText()) || TextUtils.isEmpty(password.getText()) || TextUtils.isEmpty(confirmpassword.getText())){
                    setResult(RESULT_CANCELED, intent);
                }

                else{
//                    if(password.getText().toString() != confirmpassword.getText().toString())
//                    {
//                        String msg= "Password don't match";
//                        Toast.makeText(signupActivity.this,msg,Toast.LENGTH_LONG).show();
//                    }
//                    else{
                        Users user = new Users(0,fullname.getText().toString(),username.getText().toString(),
                                password.getText().toString(),phoneNo.getText().toString());
                        String nameValue = username.getText().toString();
                        intent.putExtra("add_value1", nameValue);
                    Log.v("added","user added");
                        setResult(RESULT_OK, intent);

//                    }

                }
                finish();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK)
        {
            if(resultCode==request_code)
            {
                uploadImage.setImageURI(data.getData());
            }
        }
    }
}