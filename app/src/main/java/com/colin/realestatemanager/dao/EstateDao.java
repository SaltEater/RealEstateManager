package com.colin.realestatemanager.dao;

import android.database.Cursor;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.colin.realestatemanager.models.Estate;

@Dao
public interface EstateDao {

    @Insert
    long insert(Estate estate);

    @Update
    int update(Estate estate);

    @Delete
    int delete(Estate estate);

    @Query("SELECT * FROM estate_table")
    Cursor getAllEstatesWithCursor();

    @Query("DELETE FROM estate_table WHERE estate_id = :id")
    int deleteById(long id);

    @Query("DELETE FROM estate_table")
    int deleteAll();

    @Query("SELECT COUNT(estate_id) FROM estate_table")
    int getNbEstates();

    @Query("SELECT * FROM estate_table WHERE estate_id = :id")
    Estate getEstate(long id);
}
