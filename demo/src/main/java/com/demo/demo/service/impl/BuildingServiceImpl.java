package com.demo.demo.service.impl;

import com.demo.demo.dto.response.BuildingResponseDTO;
import com.demo.demo.repository.BuildingRepository;
import com.demo.demo.repository.DistrictRepository;
import com.demo.demo.repository.RentAreaRepository;
import com.demo.demo.repository.entity.BuildingEntity;
import com.demo.demo.repository.entity.RentAreaEntity;
import com.demo.demo.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BuildingServiceImpl implements BuildingService {
    @Autowired
    private BuildingRepository buildingRepository;
    @Autowired
    private DistrictRepository districtRepository;
    @Autowired
    private RentAreaRepository rentAreaRepository;
    @Override
    public List<BuildingResponseDTO> findAll(Map<Object, Object> attribute, List<String> typeCode) {
        List<BuildingEntity> buildingEntities = buildingRepository.findAll(attribute, typeCode);
        List<BuildingResponseDTO> buildingResponseDTOS = new ArrayList<>();
        for (BuildingEntity it: buildingEntities){
            //lay string chi rent area
            List<RentAreaEntity> rentAreaEntity = rentAreaRepository.findRentAreaById(it.getId());
            String rentArea = "";
            for (int i = 0; i < rentAreaEntity.size(); i++) {
                RentAreaEntity item = rentAreaEntity.get(i);
                rentArea += item.getValue();
                if (i != rentAreaEntity.size() - 1) {
                    rentArea += ", ";
                }
            }
            // con lai
            BuildingResponseDTO buildingResponseDTO = new BuildingResponseDTO();
            buildingResponseDTO.setId(it.getId());
            buildingResponseDTO.setName(it.getName());
            buildingResponseDTO.setAddress(it.getStreet() + " " + it.getWard() + " " + districtRepository.findDistrictById(it.getDistrictId()).getName());
            buildingResponseDTO.setNumberOfBasement(it.getNumberOfBasement());
            buildingResponseDTO.setManagerName(it.getManagerName());
            buildingResponseDTO.setManagerPhone(it.getManagerPhone());
            buildingResponseDTO.setFloorArea(it.getFloorArea());
            buildingResponseDTO.setFreeArea(it.getFreeArea());
            buildingResponseDTO.setRentArea(rentArea);
            buildingResponseDTO.setBrokerageFee(it.getBrokerageFee());
            buildingResponseDTO.setRentPrice(it.getRentPrice());
            buildingResponseDTOS.add(buildingResponseDTO);
        }
        return buildingResponseDTOS;
    }
}
