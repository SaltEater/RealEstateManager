package com.colin.realestatemanager;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import com.colin.realestatemanager.database.EstateDatabase;
import com.colin.realestatemanager.providers.EstateContentProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class ProviderInstrumentedTest {

    private ContentResolver mContentResolver;
    private static long USER_ID = 1;
    private EstateDatabase db;

    @Before
    public void setUp() {
        db = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                EstateDatabase.class)
                .allowMainThreadQueries()
                .build();
        mContentResolver = InstrumentationRegistry.getContext().getContentResolver();
        mContentResolver.delete(ContentUris.withAppendedId(EstateContentProvider.URI_ESTATE, USER_ID), "", null);
    }

    @Test
    public void getItemsWhenNoEstateInserted() {
        final Cursor cursor = mContentResolver.query(ContentUris.withAppendedId(EstateContentProvider.URI_ESTATE, USER_ID), null, null, null, null);
        assertThat(cursor, notNullValue());
        assertThat(cursor.getCount(), is(0));
        cursor.close();
    }

    @Test
    public void insertAndGetEstate() {
        // BEFORE : Adding demo estate
        final Uri userUri = mContentResolver.insert(EstateContentProvider.URI_ESTATE, generateEstate());
        // TEST
        final Cursor cursor = mContentResolver.query(ContentUris.withAppendedId(EstateContentProvider.URI_ESTATE, USER_ID), null, null, null, null);
        assertThat(cursor, notNullValue());
        assertThat(cursor.getCount(), is(1));
        assertThat(cursor.moveToFirst(), is(true));
        assertThat(cursor.getString(cursor.getColumnIndexOrThrow("price")), is("10"));
    }

    @After
    public void closeDb() {
        mContentResolver.delete(ContentUris.withAppendedId(EstateContentProvider.URI_ESTATE, USER_ID), "", null);
        db.close();
    }

    // ---

    private ContentValues generateEstate() {
        final ContentValues values = new ContentValues();
        values.put("description", "Detailed description");
        values.put("type", "Apartment");
        values.put("price", 10);
        values.put("address", "5 rue Monge");
        values.put("complement", "");
        values.put("postalCode", "75005");
        values.put("city", "Paris");
        values.put("state", "France");
        values.put("surface", 0);
        values.put("nbRooms", 0);
        values.put("nbBedrooms", 0);
        values.put("nbBathrooms", 0);
        values.put("school", true);
        values.put("swimmingPool", false);
        values.put("shop", true);
        values.put("library", true);
        values.put("park", true);
        values.put("restaurant", true);
        return values;
    }
}
