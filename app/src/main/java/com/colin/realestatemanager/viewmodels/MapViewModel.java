package com.colin.realestatemanager.viewmodels;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.colin.realestatemanager.injections.Injection;
import com.colin.realestatemanager.models.EstateWithPhotos;
import com.colin.realestatemanager.repositories.EstateWithPhotosRepository;
import java.util.List;

public class MapViewModel extends AndroidViewModel {

    private LiveData<List<EstateWithPhotos>> estates;

    public MapViewModel(Application application) {
        super(application);
        EstateWithPhotosRepository repository = Injection.provideGeneralRepository(application);
        estates = repository.getAllEstatesWithPhotos();
    }
    public LiveData<List<EstateWithPhotos>> getAllEstatesWithPhotos() {
        return estates;
    }
}
