package com.brott.meinenotizen.database;

import android.app.Application;
import androidx.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class AppRepository {

    private SubjectDao subjectDao;
    private EntryDao entryDao;

    private LiveData<List<Subject>> allSubjects;
    private static LiveData<List<Entry>> entries;

    public AppRepository(Application application) {
        AppRoomDatabase db = AppRoomDatabase.getDatabase(application);
        subjectDao = db.subjectDao();
        entryDao = db.entryDao();
        allSubjects = subjectDao.getAllSubjects();
    }

    public LiveData<List<Subject>> getAllSubjects() {
        return allSubjects;
    }

    public LiveData<List<Entry>> getAllEntries(Subject subject) {
    //    new getEntriesAsyncTask(entryDao).execute(subject);
    //    return entries;

      entries =  entryDao.getEntries(subject.getId());
      return entries;

    }

    public void insert(Entry entry) {
        new insertEntryAsyncTask(entryDao).execute(entry);
    }

    public void insert(Subject subject) {
        new insertAsyncTask(subjectDao).execute(subject);
    }

    public void deleteSubject(Subject subject) {
        new deleteSubjectAsyncTask(subjectDao).execute(subject);
    }

    public void deleteEntry(Entry entry){
        new deleteEntryAsyncTask(entryDao).execute(entry);
    }

    private static class getEntriesAsyncTask extends AsyncTask<Subject, Void, Void>{
        private EntryDao asyncTaskDao;

        getEntriesAsyncTask(EntryDao dao){asyncTaskDao = dao;}

        @Override
        protected Void doInBackground(final Subject... params) {
            entries = asyncTaskDao.getEntries(params[0].getId());
            return null;
        }
    }

    private static class deleteSubjectAsyncTask extends AsyncTask<Subject, Void, Void>{
        private SubjectDao asyncTaskDao;

        deleteSubjectAsyncTask(SubjectDao dao){asyncTaskDao = dao;}

        @Override
        protected Void doInBackground(final Subject... params) {
            asyncTaskDao.deleteSubject(params[0].getId());
            return null;
        }
    }

    private static class deleteEntryAsyncTask extends AsyncTask<Entry, Void, Void>{
        private EntryDao asyncTaskDao;

        deleteEntryAsyncTask(EntryDao dao){asyncTaskDao = dao;}

        @Override
        protected Void doInBackground(final Entry... params) {
            asyncTaskDao.delete(params[0]);
            return null;
        }
    }

    private static class insertAsyncTask extends AsyncTask<Subject, Void, Void> {

        private SubjectDao asyncTaskDao;

        insertAsyncTask(SubjectDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Subject... params) {
            asyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class insertEntryAsyncTask extends AsyncTask<Entry, Void, Void> {

        private EntryDao asyncTaskDao;

        insertEntryAsyncTask(EntryDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Entry... params) {
            asyncTaskDao.insert(params[0]);
            return null;
        }
    }
}



