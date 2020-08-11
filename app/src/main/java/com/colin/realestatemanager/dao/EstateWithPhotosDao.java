package com.colin.realestatemanager.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.colin.realestatemanager.models.EstateWithPhotos;

import java.util.List;

@Dao
public interface EstateWithPhotosDao {

    @Transaction
    @Query("SELECT * FROM estate_table")
    LiveData<List<EstateWithPhotos>> getAllEstatesWithPhotos();

    @Transaction
    @Query("SELECT * FROM estate_table WHERE real_estate_agent like :realEstateAgent AND (status = :soldStateAccepted OR (status = (NOT :onSaleStateAccepted))) AND begin_date BETWEEN :entryBeginDate AND :entryEndDate AND end_date BETWEEN :soldBeginDate AND :soldEndDate AND type like :type AND price BETWEEN :minPrice and :maxPrice AND postal_code like :postalCode AND state like :state AND city like :city AND surface BETWEEN :minSurface AND :maxSurface AND rooms_number BETWEEN :minNbRooms AND :maxNbRooms AND  bedrooms_number BETWEEN :minNbBedrooms and :maxNbBedrooms AND bathrooms_number BETWEEN :minNbBathrooms AND :maxNbBathrooms AND (school = :school OR school = 1) AND (swimming_pool = :swimmingPool OR swimming_pool = 1) AND (shop = :shop OR shop = 1) AND (library =:library OR library = 1) AND (park = :park OR park = 1) AND (restaurant = :restaurant OR restaurant = 1)")
    LiveData<List<EstateWithPhotos>> getFilteredEstateWithPhotos(String realEstateAgent, boolean soldStateAccepted, boolean onSaleStateAccepted, long entryBeginDate, long entryEndDate, long soldBeginDate, long soldEndDate, String type, int minPrice, int maxPrice,
                                                                 String postalCode, String state, String city,
                                                                 int minSurface, int maxSurface, int minNbRooms, int maxNbRooms, int minNbBedrooms, int maxNbBedrooms, int minNbBathrooms, int maxNbBathrooms,
                                                                 boolean school, boolean swimmingPool, boolean shop, boolean library, boolean park, boolean restaurant);
}
