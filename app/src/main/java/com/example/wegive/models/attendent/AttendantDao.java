package com.example.wegive.models.attendent;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import java.util.List;

@Dao
public interface AttendantDao {
    @Query("select * from ATTENDANTS")
    LiveData<List<Attendant>> getAll();

    @Query("select * from ATTENDANTS WHERE postId = :postId")
    LiveData<List<Attendant>> getAllByPostId(String postId);

    @Query("select * from ATTENDANTS where id = :attendantId")
    Attendant getAttendantById(String attendantId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Attendant... attendants);


    @Delete
    void delete(Attendant attendant);

    @Query("DELETE FROM ATTENDANTS WHERE id= :id")
    void deleteAttendantById(String id);
}
