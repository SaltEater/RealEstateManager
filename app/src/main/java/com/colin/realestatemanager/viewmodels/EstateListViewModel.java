package com.colin.realestatemanager.viewmodels;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import com.colin.realestatemanager.injections.Injection;
import com.colin.realestatemanager.models.EstateWithPhotos;
import com.colin.realestatemanager.models.Filters;
import com.colin.realestatemanager.repositories.EstateWithPhotosRepository;
import java.util.List;

public class EstateListViewModel extends AndroidViewModel {

    private LiveData<List<EstateWithPhotos>> filteredEstates;
    private MutableLiveData<Filters> filterLiveData;

    public EstateListViewModel(Application application) {
        super(application);
        EstateWithPhotosRepository repository = Injection.provideGeneralRepository(application);
        filterLiveData = new MutableLiveData<>(new Filters());
        filteredEstates = Transformations.switchMap(filterLiveData, repository::updateFilteredEstatesWithPhotos);
    }

    public LiveData<List<EstateWithPhotos>> getFilteredEstates() {
        return filteredEstates;
    }

    public void setFilters (Filters filters) {
        filterLiveData.setValue(filters);
    }

    public Filters getFilters() {
        return filterLiveData.getValue();
    }
}
