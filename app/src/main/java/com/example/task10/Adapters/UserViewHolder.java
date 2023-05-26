package com.example.task10.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task10.R;

public class UserViewHolder extends RecyclerView.ViewHolder{

    private TextView id,name,password,phoneNo;


    public UserViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.username);
        id = itemView.findViewById(R.id.textViewID);
        password = itemView.findViewById(R.id.userpass);
        phoneNo = itemView.findViewById(R.id.userNo);
    }
    public void bind(int idValue, String nameValue, String descriptionValue,String number){
        name.setText(nameValue);
        id.setText(String.valueOf(idValue));
        password.setText(descriptionValue);
        phoneNo.setText(number);
    }

    public static UserViewHolder create(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.users, parent, false);
        return new UserViewHolder(view);
    }
}
