package com.brott.meinenotizen.database;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface SubjectDao {

    @Insert
    void insert(Subject subject);

    @Update
    public void updateSubject(Subject subject);

    @Query("SELECT * from subject_table ORDER BY name ASC")
    LiveData<List<Subject>> getAllSubjects();

    @Query("DELETE FROM subject_table WHERE id = :subjectId")
    void deleteSubject(int subjectId);
}
