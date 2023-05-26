package com.example.task10.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class UserRepository {
    UserDAO userDAO;
    LiveData<List<Users>> users;
    boolean temp;

    public UserRepository(Application application)
    {
        UsersRoomDatabase db = UsersRoomDatabase.getDatabase(application);
        userDAO = db.userDAO();
        users = userDAO.getUsers();
    }

    public LiveData<List<Users>> getUsers(){return users;}

    public void insert(Users user){
        UsersRoomDatabase.databaseWriteExecutor.execute(()->{
            userDAO.insert(user);
        });
    }

    public void update(Users user){
        UsersRoomDatabase.databaseWriteExecutor.execute(()->{
            userDAO.update(user);
        });
    }


    public void delete(Users user){
        UsersRoomDatabase.databaseWriteExecutor.execute(()->{
            userDAO.delete(user);
        });
    }

    public boolean loginUser(String user,String pass){
        UsersRoomDatabase.databaseWriteExecutor.execute(()->{
            temp = userDAO.loginUser(user, pass);
        });
        return true;
    }
}
