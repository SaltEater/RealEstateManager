package com.colin.realestatemanager.repositories;

import com.colin.realestatemanager.dao.EstateDao;
import com.colin.realestatemanager.models.Estate;

public class EstateRepository {
    private EstateDao estateDao;

    public EstateRepository(EstateDao estateDao) {
        this.estateDao = estateDao;
    }

    public void insert(Estate estate) {
        estateDao.insert(estate);
    }

    public void update(Estate estate) {
        estateDao.update(estate);
    }
}
