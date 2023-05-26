package com.example.task10.Adapters;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.task10.data.UserRepository;
import com.example.task10.data.Users;

import java.util.List;

public class UsersViewModel extends AndroidViewModel {
    UserRepository userRepository;

    LiveData<List<Users>> users;

    public UsersViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
        users = userRepository.getUsers();
    }

    public LiveData<List<Users>> getUsers() {
        return users;
    }

    public void insert(Users user){
        userRepository.insert(user);
    }

    public void update(Users user){
        userRepository.update(user);
    }

    public void delete(Users user){
        userRepository.delete(user);
    }

    public boolean loginUser(String user,String pass){
        if(userRepository.loginUser(user,pass))
        {
            return true;
        }
        return false;
    }
}
