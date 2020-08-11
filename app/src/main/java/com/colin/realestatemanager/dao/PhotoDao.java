package com.colin.realestatemanager.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.colin.realestatemanager.models.Photo;

@Dao
public interface PhotoDao {

    @Insert
    void insert(Photo photo);

    @Update
    void update(Photo photo);

    @Delete
    void delete(Photo photo);

    @Query("SELECT COUNT(photoId) FROM photo_table")
    int getNbPhotos();

    @Query("SELECT * FROM photo_table WHERE photoId = :id")
    Photo getPhoto(long id);
}
