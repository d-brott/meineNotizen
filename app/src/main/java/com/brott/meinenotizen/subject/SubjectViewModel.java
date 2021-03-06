package com.brott.meinenotizen.subject;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.brott.meinenotizen.database.AppRepository;
import com.brott.meinenotizen.database.Subject;

import java.util.List;

public class SubjectViewModel extends AndroidViewModel {

    private AppRepository appRepository;

    private LiveData<List<Subject>> allSubjects;

    public SubjectViewModel(Application application) {
        super(application);
        appRepository = new AppRepository(application);
        allSubjects = appRepository.getAllSubjects();
    }

    public LiveData<List<Subject>> getAllSubjects() {
        return allSubjects;
    }

    public void insert(Subject subject) {
        appRepository.insert(subject);
    }

    public void update(Subject subject) {appRepository.updateSubject(subject);}

    public void deleteSubject(Subject subject){
        appRepository.deleteSubject(subject);
    }

}
