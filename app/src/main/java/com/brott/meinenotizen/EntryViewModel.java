package com.brott.meinenotizen;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.brott.meinenotizen.data.AppRepository;
import com.brott.meinenotizen.data.Entry;
import com.brott.meinenotizen.data.Subject;

import java.util.List;

public class EntryViewModel extends AndroidViewModel {

    private AppRepository appRepository;

    public EntryViewModel(Application application) {
        super(application);
        appRepository = new AppRepository(application);
    }

    public LiveData<List<Entry>> getAllEntries(Subject subject){
        return appRepository.getAllEntries(subject);
    }

    public void insert(Entry entry){
        appRepository.insert(entry);
    }

}
