package com.brott.meinenotizen.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

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
