package com.example.task10.Adapters;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.task10.data.Trucks;
import com.example.task10.data.TrucksRepository;

import java.util.List;

public class TrucksViewModel extends AndroidViewModel {
    TrucksRepository trucksRepository;

    LiveData<List<Trucks>> trucksAvail;

    public TrucksViewModel(@NonNull Application application) {
        super(application);
        trucksRepository = new TrucksRepository(application);
        trucksAvail = trucksRepository.getTrucks();
    }
    public LiveData<List<Trucks>> getTrucksAvail() {
        return trucksAvail;
    }

    /*
    Created a wrapper insert() method that calls the Repository's
    insert() method. In this way, the implementation of insert()
    is encapsulated from the UI.
     */
    public void insert(Trucks trucks){
        trucksRepository.insert(trucks);
    }

    public void update(Trucks trucks){
        trucksRepository.update(trucks);
    }

    public void delete(Trucks trucks){
        trucksRepository.delete(trucks);
    }
}
