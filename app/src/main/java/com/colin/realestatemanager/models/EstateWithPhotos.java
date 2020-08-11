package com.colin.realestatemanager.models;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.io.Serializable;
import java.util.List;

public class EstateWithPhotos implements Serializable {
    @Embedded
    private Estate estate;
    @Relation(parentColumn = "estate_id", entityColumn = "estateId")
    private List<Photo> photos;

    public Estate getEstate() {
        return estate;
    }

    public void setEstate(Estate estate) {
        this.estate = estate;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }
}
