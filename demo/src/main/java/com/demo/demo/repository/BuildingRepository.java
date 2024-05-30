package com.demo.demo.repository;

import com.demo.demo.repository.entity.BuildingEntity;

import java.util.List;

public interface BuildingRepository {
    //chi duoc co hang so va abs method
    List<BuildingEntity> findAll(String nameBuilding, Long numberOfBasement);
    void delete (List<Long> ids);
}
