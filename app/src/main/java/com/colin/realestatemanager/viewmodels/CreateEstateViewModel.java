package com.colin.realestatemanager.viewmodels;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.colin.realestatemanager.injections.Injection;
import com.colin.realestatemanager.models.Estate;
import com.colin.realestatemanager.models.Photo;
import com.colin.realestatemanager.repositories.EstateRepository;
import com.colin.realestatemanager.repositories.PhotoRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class CreateEstateViewModel extends AndroidViewModel {
    private EstateRepository estateRepository;
    private PhotoRepository photoRepository;
    private Executor executor = Injection.provideExecutor();
    private MutableLiveData<List<Photo>> photos = new MutableLiveData<>(new ArrayList<>());

    public CreateEstateViewModel(Application application) {
        super(application);
        this.estateRepository = Injection.provideEstateRepository(application);
        this.photoRepository = Injection.providePhotoRepository(application);
    }

    public MutableLiveData<List<Photo>> getPhotosLiveData() {
        return photos;
    }

    public void insertEstate(Estate estate) {
        executor.execute(() -> estateRepository.insert(estate));
    }

    public void insertPhotos(long estateId) {
        for (int i = 0; i < photos.getValue().size(); i++) {
            final Photo photo = photos.getValue().get(i);
            photo.setOrder((byte) i);
            photo.setEstateId(estateId);
            executor.execute(() -> photoRepository.insert(photo));
        }
    }

}
