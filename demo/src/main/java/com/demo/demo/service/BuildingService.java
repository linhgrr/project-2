package com.demo.demo.service;

import com.demo.demo.dto.response.BuildingResponseDTO;

import java.util.List;
import java.util.Map;

public interface BuildingService {
    List<BuildingResponseDTO> findAll(Map<Object, Object> attribute, List<String> typeCode);
}
