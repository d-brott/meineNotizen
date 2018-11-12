package com.brott.meinenotizen.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

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
