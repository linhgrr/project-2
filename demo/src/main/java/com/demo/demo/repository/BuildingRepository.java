package com.demo.demo.repository;

import com.demo.demo.repository.entity.BuildingEntity;

import java.util.List;
import java.util.Map;

public interface BuildingRepository {
    //chi duoc co hang so va abs method
    List<BuildingEntity> findAll(Map<Object, Object> attribute, List<String> typeCode);
}
