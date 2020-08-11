package com.colin.realestatemanager.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Random;

@Entity(tableName = "photo_table")
public class Photo implements Comparable<Photo>, Serializable {
    @PrimaryKey(autoGenerate = true)
    private long photoId;
    private String name;
    private String path;
    private long estateId;
    private byte order;

    public Photo() {

    }

    public Photo(String name, String path, long estateId) {
        photoId = new Random().nextLong();
        this.name = name;
        this.path = path;
        this.estateId = estateId;
    }

    public long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(long photoId) {
        this.photoId = photoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getEstateId() {
        return estateId;
    }

    public void setEstateId(long estateId) {
        this.estateId = estateId;
    }

    public byte getOrder() {
        return order;
    }

    public void setOrder(byte order) {
        this.order = order;
    }

    @Override
    public int compareTo(Photo o) {
        return order - o.getOrder();
    }
}
