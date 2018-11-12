package com.brott.meinenotizen.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface EntryDao {

    @Insert
    void insert(Entry entry);

    @Update
    void update(Entry... entries);

    @Delete
    void delete(Entry... entries);

    @Query("SELECT * FROM entry_table WHERE subjectId =:id")
    LiveData<List<Entry>> getEntries(int id);


}
