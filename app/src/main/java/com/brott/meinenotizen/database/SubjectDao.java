package com.brott.meinenotizen.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface SubjectDao {

    @Insert
    void insert(Subject subject);

    @Query("SELECT * from subject_table ORDER BY name ASC")
    LiveData<List<Subject>> getAllSubjects();

    @Query("DELETE FROM subject_table WHERE id = :subjectId")
    void deleteSubject(int subjectId);
}
