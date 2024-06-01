package com.demo.demo.api;

import com.demo.demo.customexception.FieldRequiredException;
import com.demo.demo.dto.BuildingDTO;
import com.demo.demo.dto.response.BuildingResponseDTO;
import com.demo.demo.repository.BuildingRepository;
import com.demo.demo.repository.entity.BuildingEntity;
import com.demo.demo.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//@Controller //dùng để chuyển một java class thành 1 restful api web service
@RestController // = @Controller + @RespondBody
public class BuildingAPI {
    @Autowired
    private BuildingService buildingService;
    @GetMapping("/api/buildings")
    private Object getBuildings(@RequestParam(required = false) Map<Object, Object> attribute,
                                @RequestParam(required = false) List<String> typeCode){
//        List<BuildingResponseDTO> buildingResponseDTOS = buildingService.findAll(nameBuilding, numberOfBasement);
        List<BuildingResponseDTO> results = new ArrayList<>();
        results = buildingService.findAll(attribute, typeCode);
        return results;
    }

    private void validate(BuildingDTO buildingDTO){
        if(buildingDTO.getName() == null || buildingDTO.getName().equals("") || buildingDTO.getRentPrice() == null){
            throw new FieldRequiredException("name or rent price not null");
        }
    }

    @PostMapping("/api/buildings")
    private Object createBuildings(@RequestBody BuildingDTO buildingDTO){
//      System.out.println(buildingDTO.getFirst().getName() + " " + buildingDTO.getFirst().getDistrictId());
        validate(buildingDTO);
        System.out.println("ok");
        return "OK";
    }
}
