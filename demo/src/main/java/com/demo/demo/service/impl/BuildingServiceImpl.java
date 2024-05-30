package com.demo.demo.service.impl;

import com.demo.demo.dto.response.BuildingResponseDTO;
import com.demo.demo.repository.BuildingRepository;
import com.demo.demo.repository.entity.BuildingEntity;
import com.demo.demo.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BuildingServiceImpl implements BuildingService {
    @Autowired
    private BuildingRepository buildingRepository;
    @Override
    public List<BuildingResponseDTO> findAll(String nameBuilding, Long numberOfBasement) {
        List<BuildingEntity> buildingEntities = buildingRepository.findAll(nameBuilding, numberOfBasement);
        List<BuildingResponseDTO> results = new ArrayList<>();
        for (BuildingEntity it: buildingEntities){
            BuildingResponseDTO buildingResponseDTO = new BuildingResponseDTO();
            buildingResponseDTO.setId(it.getId());
            buildingResponseDTO.setName(it.getName());
            buildingResponseDTO.setAddress(it.getStreet() + ", " + it.getWard() + ", " + it.getDistrictId());
            buildingResponseDTO.setRentPrice(it.getRentPrice());
            results.add(buildingResponseDTO);
        }
        return results;
    }
}
