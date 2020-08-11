package com.colin.realestatemanager.repositories;

import com.colin.realestatemanager.dao.PhotoDao;
import com.colin.realestatemanager.models.Photo;

public class PhotoRepository {
    private PhotoDao photoDao;

    public PhotoRepository(PhotoDao photoDao) {
        this.photoDao = photoDao;
    }

    public void insert(Photo photo) {
        photoDao.insert(photo);
    }

    public void update(Photo photo) {
        photoDao.update(photo);
    }
}
