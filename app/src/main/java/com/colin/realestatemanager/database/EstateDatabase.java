package com.colin.realestatemanager.database;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.colin.realestatemanager.dao.EstateDao;
import com.colin.realestatemanager.dao.EstateWithPhotosDao;
import com.colin.realestatemanager.dao.PhotoDao;
import com.colin.realestatemanager.models.Estate;
import com.colin.realestatemanager.models.Photo;

@Database(entities = {Estate.class, Photo.class}, version = 1, exportSchema = false)
public abstract class EstateDatabase extends RoomDatabase {

    private static volatile EstateDatabase INSTANCE;

    public abstract EstateDao estateDao();
    public abstract PhotoDao photoDao();
    public abstract EstateWithPhotosDao estateWithPhotosDao();


    public static EstateDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (EstateDatabase.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        EstateDatabase.class, "estate_database.db")
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        return INSTANCE;
    }
}
