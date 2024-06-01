package com.demo.demo.repository;

import com.demo.demo.repository.entity.DistrictEntity;

public interface DistrictRepository {
    public DistrictEntity findDistrictById(Long id);
}
