package com.example.task10.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task10.ItemDetailsActivtiy;
import com.example.task10.R;
import com.example.task10.UpdateActivity;
import com.example.task10.data.Trucks;

public class TrucksAdapter extends ListAdapter<Trucks,TrucksAdapter.MyViewHolder > {

    Context context;

    TrucksViewModel trucksViewModel;

    SharedPreferences sharedPreferences ;
    public TrucksAdapter(@NonNull DiffUtil.ItemCallback<Trucks> diffCallback, Context context,TrucksViewModel trucksViewModel) {
        super(diffCallback);
        this.context = context;
        this.trucksViewModel = trucksViewModel;
    }

    public TrucksAdapter(@NonNull AsyncDifferConfig<Trucks> config) {
        super(config);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trucks, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Trucks current = getItem(position);
        holder.model.setText(current.getModel());
        holder.id.setText(String.valueOf(current.getId()));
        holder.dates.setText((CharSequence) current.getDates());
        holder.location.setText(current.getLocation());
        holder.number = current.getNumber();

        holder.updateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateActivity.class);

                intent.putExtra("model", current);
                ((Activity) context).startActivityForResult(intent,2);
            }
        });

        holder.deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trucksViewModel.delete(current);
            }
        });

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView id,model,dates,location;
        ImageView updateImage, deleteImage;

        Button estimate;
        String number;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            model = itemView.findViewById(R.id.textViewName);
            id = itemView.findViewById(R.id.textViewID);
            dates = itemView.findViewById(R.id.textViewDescription);
            location = itemView.findViewById(R.id.destination3);
            estimate = itemView.findViewById(R.id.estimate);
            updateImage = itemView.findViewById(R.id.updateImage);
            deleteImage = itemView.findViewById(R.id.deleteImage);

            sharedPreferences = context.getSharedPreferences("My_Pref", Context.MODE_PRIVATE);

            SharedPreferences.Editor myEditor = sharedPreferences.edit();

            estimate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION)
                    {
                        Intent intent = new Intent(v.getContext(), ItemDetailsActivtiy.class);


                        intent.putExtra("name",model.getText().toString());
                        intent.putExtra("desc",dates.getText().toString());
                        intent.putExtra("locationVal",location.getText().toString());
                        intent.putExtra("number",number);

                        Log.i("Location value " ,location.getText().toString());

                        v.getContext().startActivity(intent);
                    }
                }
            });

        }
    }
    public static class TruckDiff extends DiffUtil.ItemCallback<Trucks>{

        @Override
        public boolean areItemsTheSame(@NonNull Trucks oldItem, @NonNull Trucks newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Trucks oldItem, @NonNull Trucks newItem) {
            return oldItem.getModel().equals(newItem.getModel());
        }
    }
}
