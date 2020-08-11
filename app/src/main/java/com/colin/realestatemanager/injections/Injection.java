package com.colin.realestatemanager.injections;

import android.content.Context;
import com.colin.realestatemanager.database.EstateDatabase;
import com.colin.realestatemanager.repositories.EstateRepository;
import com.colin.realestatemanager.repositories.EstateWithPhotosRepository;
import com.colin.realestatemanager.repositories.PhotoRepository;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Injection {

    public static EstateRepository provideEstateRepository(Context context) {
        EstateDatabase database = EstateDatabase.getInstance(context);
        return new EstateRepository(database.estateDao());
    }

    public static PhotoRepository providePhotoRepository(Context context) {
        EstateDatabase database = EstateDatabase.getInstance(context);
        return new PhotoRepository(database.photoDao());
    }

    public static EstateWithPhotosRepository provideGeneralRepository(Context context) {
        EstateDatabase database = EstateDatabase.getInstance(context);
        return new EstateWithPhotosRepository(database.estateWithPhotosDao());
    }

    public static Executor provideExecutor() {
        return Executors.newSingleThreadExecutor();
    }

}
