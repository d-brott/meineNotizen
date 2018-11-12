package com.brott.meinenotizen.entry;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.brott.meinenotizen.database.AppRepository;
import com.brott.meinenotizen.database.Entry;
import com.brott.meinenotizen.database.Subject;

import java.util.List;

class EntryViewModel extends AndroidViewModel {
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

    public void delete(Entry entry){
        appRepository.deleteEntry(entry);
    }

}
