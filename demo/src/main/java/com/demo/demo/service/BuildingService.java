package com.demo.demo.service;

import com.demo.demo.dto.response.BuildingResponseDTO;

import java.util.List;

public interface BuildingService {
    List<BuildingResponseDTO> findAll(String nameBuilding, Long numberOfBasement);
}
