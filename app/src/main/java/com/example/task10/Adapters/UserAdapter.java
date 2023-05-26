package com.example.task10.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task10.R;
import com.example.task10.data.Users;

public class UserAdapter extends ListAdapter<Users,UserAdapter.MyViewHolder > {
    Context context;

    UsersViewModel usersViewModel;


    public UserAdapter(@NonNull DiffUtil.ItemCallback<Users> diffCallback, Context context,UsersViewModel usersViewModel) {
        super(diffCallback);
        this.context = context;
        this.usersViewModel = usersViewModel;
    }

    public UserAdapter(@NonNull AsyncDifferConfig<Users> config) {
        super(config);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.users, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Users current = getItem(position);
        holder.name.setText(current.getUsername());
        holder.id.setText(String.valueOf(current.getId()));
        holder.password.setText(current.getPassword());
        holder.phoneNo.setText(current.getPhoneNo());

        holder.deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usersViewModel.delete(current);
            }
        });
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView id,name,password,phoneNo;
        ImageView deleteImage;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.username);
            id = itemView.findViewById(R.id.userViewID);
            password = itemView.findViewById(R.id.userpass);
            phoneNo = itemView.findViewById(R.id.userNo);
            deleteImage = itemView.findViewById(R.id.deleteImage);

        }
    }
    public static class UserDiff extends DiffUtil.ItemCallback<Users>{

        @Override
        public boolean areItemsTheSame(@NonNull Users oldItem, @NonNull Users newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Users oldItem, @NonNull Users newItem) {
            return oldItem.getFullName().equals(newItem.getFullName());
        }
    }
}
