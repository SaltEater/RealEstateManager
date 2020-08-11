package com.colin.realestatemanager.repositories;

import androidx.lifecycle.LiveData;

import com.colin.realestatemanager.dao.EstateWithPhotosDao;
import com.colin.realestatemanager.models.EstateWithPhotos;
import com.colin.realestatemanager.models.Filters;
import java.util.List;

public class EstateWithPhotosRepository {
    private EstateWithPhotosDao estateWithPhotosDao;
    private LiveData<List<EstateWithPhotos>> allEstatesWithPhotos;

    public EstateWithPhotosRepository(EstateWithPhotosDao estateWithPhotosDao) {
        this.estateWithPhotosDao = estateWithPhotosDao;
        allEstatesWithPhotos = estateWithPhotosDao.getAllEstatesWithPhotos();
    }

    public LiveData<List<EstateWithPhotos>> getAllEstatesWithPhotos() {
        return allEstatesWithPhotos;
    }

    public LiveData<List<EstateWithPhotos>> updateFilteredEstatesWithPhotos(Filters filters) {
        String realEstateAgent = filters.getRealEstateAgent();
        Boolean soldStateAccepted = filters.getSoldStateAccepted();
        Boolean onSaleStateAccepted = filters.getOnSaleStateAccepted();
        Long entryBeginDate = filters.getEntryBeginDate();
        Long entryEndDate = filters.getEntryEndDate();
        Long soldBeginDate = filters.getSoldBeginDate();
        Long soldEndDate = filters.getSoldEndDate();
        String type = filters.getType();
        Integer minPrice = filters.getMinPrice();
        Integer maxPrice = filters.getMaxPrice();
        String postalCode = filters.getPostalCode();
        String state = filters.getState();
        String city = filters.getCity();
        Integer minSurface = filters.getMinSurface();
        Integer maxSurface = filters.getMaxSurface();
        Integer minNbRooms = filters.getMinNbRooms();
        Integer maxNbRooms = filters.getMaxNbRooms();
        Integer minNbBedrooms = filters.getMinNbBedrooms();
        Integer maxNbBedrooms = filters.getMaxNbBedrooms();
        Integer minNbBathrooms = filters.getMinNbBathrooms();
        Integer maxNbBathrooms = filters.getMaxNbBathrooms();
        Boolean school = filters.getSchool();
        Boolean swimmingPool = filters.getSwimmingPool();
        Boolean shop = filters.getShop();
        Boolean library = filters.getLibrary();
        Boolean park = filters.getPark();
        Boolean restaurant = filters.getRestaurant();

        return estateWithPhotosDao.getFilteredEstateWithPhotos(realEstateAgent, soldStateAccepted, onSaleStateAccepted, entryBeginDate, entryEndDate, soldBeginDate, soldEndDate, type, minPrice, maxPrice,
                postalCode, state, city,
                minSurface, maxSurface, minNbRooms, maxNbRooms, minNbBedrooms, maxNbBedrooms, minNbBathrooms, maxNbBathrooms,
                school, swimmingPool, shop, library, park, restaurant);
    }

}
