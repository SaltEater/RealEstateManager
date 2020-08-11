package com.colin.realestatemanager;

import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.colin.realestatemanager.database.EstateDatabase;
import com.colin.realestatemanager.models.Estate;
import com.colin.realestatemanager.models.Photo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class DaoInstrumentedTest {

    private EstateDatabase database;

    @Before
    public void init() {
    database =  Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
            EstateDatabase.class)
            .allowMainThreadQueries()
            .build();
    }

    @Test
    public void insert_update_and_delete_estate() {

        //Insert
        int before = database.estateDao().getNbEstates();
        database.estateDao().insert(estate);
        int after = database.estateDao().getNbEstates();
        assertEquals(before + 1, after);

        // Update
        estate.setPark(true);
        database.estateDao().update(estate);
        assertTrue(database.estateDao().getEstate(estate.getEstateId()).isPark());

        // Delete
        database.estateDao().delete(estate);
        int end = database.estateDao().getNbEstates();
        assertEquals(end, before);
    }

    @Test
    public void insert_update_and_delete_photo() {

        //Insert
        int before = database.photoDao().getNbPhotos();
        database.photoDao().insert(photo);
        int after = database.photoDao().getNbPhotos();
        assertEquals(before + 1, after);

        // Update
        photo.setOrder((byte) 2);
        database.photoDao().update(photo);
        assertEquals(database.photoDao().getPhoto(photo.getPhotoId()).getOrder(), (byte) 2);

        // Delete
        database.photoDao().delete(photo);
        int end = database.photoDao().getNbPhotos();
        assertEquals(end, before);
    }

    private Estate estate = new Estate("realEstateAgent", false, -1, -1, "description", "type", 1, "address", "complement", "postalCode",
                                                "city", "state", 0, 0, 0, 0, false,
                                        false,false, false, false, false);

    private Photo photo = new Photo( "name", "path", -1);
    
}
