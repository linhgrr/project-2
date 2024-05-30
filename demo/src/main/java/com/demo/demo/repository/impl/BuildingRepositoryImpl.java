package com.demo.demo.repository.impl;

import com.demo.demo.dto.BuildingDTO;
import com.demo.demo.repository.BuildingRepository;
import com.demo.demo.repository.entity.BuildingEntity;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BuildingRepositoryImpl implements BuildingRepository {
    static String DB_URL = "jdbc:mysql://localhost:3306/estatebasic";
    static String USER = "root";
    static String PASS = "password";
    @Override
    public List<BuildingEntity> findAll(String nameBuilding, Long numberOfBasement) {
        List<BuildingEntity> results = new ArrayList<>();
        String sql = "select b.* from estatebasic.building b";
        String where = " Where 1=1";
        if (nameBuilding != null && !nameBuilding.isEmpty()){
            where += " and b.name like '%" + nameBuilding +"%'";
        }
        if (numberOfBasement != null){
            where += " and b.numberofbasement = " + numberOfBasement;
        }
        sql += where;
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
        ){
            while (rs.next()){
                BuildingEntity buildingEntity = new BuildingEntity();
                buildingEntity.setName(rs.getString("name"));
                buildingEntity.setDistrictId(rs.getLong("districtid"));
                buildingEntity.setStreet(rs.getString("street"));
                buildingEntity.setWard(rs.getString("ward"));
                buildingEntity.setRentPrice(rs.getLong("rentprice"));
                results.add(buildingEntity);
            }
            System.out.println("Ket noi robot thanh cong");
        }catch (SQLException e){
            e.printStackTrace();
        }
        return results;
    }

    @Override
    public void delete(List<Long> ids) {

    }
}
