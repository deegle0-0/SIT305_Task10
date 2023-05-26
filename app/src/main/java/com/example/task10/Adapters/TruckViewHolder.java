package com.example.task10.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.task10.R;

public class TruckViewHolder extends RecyclerView.ViewHolder {
    private TextView id,model,dates;

    public TruckViewHolder(View itemView) {
        super(itemView);
        model = itemView.findViewById(R.id.textViewName);
        id = itemView.findViewById(R.id.textViewID);
        dates = itemView.findViewById(R.id.textViewDescription);
    }

    public void bind(int idValue, String nameValue, String descriptionValue){
        model.setText(nameValue);
        id.setText(String.valueOf(idValue));
        dates.setText(descriptionValue);
    }

    public static TruckViewHolder create(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trucks, parent, false);
        return new TruckViewHolder(view);
    }
}
