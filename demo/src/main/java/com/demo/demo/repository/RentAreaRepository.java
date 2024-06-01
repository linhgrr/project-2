package com.demo.demo.repository;

import com.demo.demo.repository.entity.RentAreaEntity;

import java.util.List;

public interface RentAreaRepository {
    public List<RentAreaEntity> findRentAreaById(Long id);
}
