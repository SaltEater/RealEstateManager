package com.colin.realestatemanager.viewmodels;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import com.colin.realestatemanager.injections.Injection;
import com.colin.realestatemanager.models.Estate;
import com.colin.realestatemanager.models.EstateWithPhotos;
import com.colin.realestatemanager.models.Photo;
import com.colin.realestatemanager.repositories.EstateRepository;
import com.colin.realestatemanager.repositories.PhotoRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Executor;

public class ModifyViewModel extends AndroidViewModel {
    private EstateRepository estateRepository;
    private PhotoRepository photoRepository;
    private Executor executor = Injection.provideExecutor();
    private ArrayList<Photo> photos = new ArrayList<>();

    public ModifyViewModel(Application application) {
        super(application);
        this.estateRepository = Injection.provideEstateRepository(application);
        this.photoRepository = Injection.providePhotoRepository(application);
    }

    public void init(EstateWithPhotos estate) {
        if (estate != null) {
            Collections.sort(estate.getPhotos());
            photos.addAll(estate.getPhotos());
        }
    }

    public ArrayList<Photo> getPhotos() {
        return photos;
    }

    public void updateEstate(Estate estate) {
        executor.execute(() -> estateRepository.update(estate));
    }

    public void deletePhoto(Photo photo) {
        executor.execute(() -> photoRepository.update(photo));
    }

    public void insertPhoto(Photo photo) {
        executor.execute(() -> photoRepository.insert(photo));
    }

}
