package com.colin.realestatemanager.providers;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.colin.realestatemanager.database.EstateDatabase;
import com.colin.realestatemanager.models.Estate;

public class EstateContentProvider extends ContentProvider {

    public static final String AUTHORITY = "com.colin.realestatemanager.providers";
    public static final String TABLE_NAME = "estate_table";
    public static final Uri URI_ESTATE = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);


    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        if (getContext() != null) {
            final Cursor cursor = EstateDatabase.getInstance(getContext()).estateDao().getAllEstatesWithCursor();
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
            return cursor;
        }
        throw new IllegalArgumentException("Failed to query row for uri " + uri);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return "vnd.android.cursor.dir/" + AUTHORITY + "." + TABLE_NAME;

    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        if (getContext() != null) {
            long id = EstateDatabase.getInstance(getContext()).estateDao().insert(Estate.fromContentValues(values));
            if (id != 0) {
                getContext().getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id);
            }
        }
        throw new IllegalArgumentException("Failed to insert row into " + uri);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        if (getContext() != null){
            final int count = EstateDatabase.getInstance(getContext()).estateDao().deleteAll();
            getContext().getContentResolver().notifyChange(uri, null);
            return count;
        }
        throw new IllegalArgumentException("Failed to delete row into " + uri);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        if (getContext() != null){
            final int count = EstateDatabase.getInstance(getContext()).estateDao().update(Estate.fromContentValues(values));
            getContext().getContentResolver().notifyChange(uri, null);
            return count;
        }
        throw new IllegalArgumentException("Failed to update row into " + uri);
    }

}
