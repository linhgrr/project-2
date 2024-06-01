package com.demo.demo.repository.impl;

import com.demo.demo.repository.BuildingRepository;
import com.demo.demo.repository.entity.BuildingEntity;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class BuildingRepositoryImpl implements BuildingRepository {
    static String DB_URL = "jdbc:mysql://localhost:3306/estatebasic";
    static String USER = "root";
    static String PASS = "password";

    @Override
    public List<BuildingEntity> findAll(Map<Object, Object> attribute, List<String> typeCode) {
        List<BuildingEntity> results = new ArrayList<>();
        String sql = "SELECT b.* FROM estatebasic.building b\n";
        sql += joinProcess(attribute, typeCode) + queryProcess(attribute, typeCode);

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
            System.out.println(sql);
        }
        return results;
    }

    private String joinProcess(Map<Object, Object> attribute, List<String> typeCode){
        String join = "";

        if (attribute.get("startRentArea") != null || attribute.get("endRentArea") != null){
            join += "JOIN rentarea r\n" +
                    "ON r.buildingid = b.id\n";
        }
        if (attribute.get("staffId") != null){
            join += "JOIN assignmentbuilding ab\n" +
                    "ON ab.buildingid = b.id\n";
        }
        if (typeCode != null){
            join += "JOIN buildingrenttype br\n" +
                    "ON br.buildingid = b.id\n" +
                    "JOIN renttype rt\n" +
                    "ON rt.id = br.renttypeid\n";
        }
        return join;
    }

    private String queryProcess(Map<Object, Object> attribute, List<String> typeCode){
        String where = "WHERE 1=1";
        if(attribute.get("nameBuilding") != null && !attribute.get("nameBuilding").toString().isEmpty()){
            where += " AND b.name LIKE '%" + attribute.get("nameBuilding").toString() +"%'";
        }
        if(attribute.get("ward") != null && !attribute.get("ward").toString().isEmpty()){
            where += " AND b.ward LIKE '%" + attribute.get("ward").toString() +"%'";
        }
        if(attribute.get("street") != null && !attribute.get("street").toString().isEmpty()){
            where += " AND b.street LIKE '%" + attribute.get("street").toString() +"%'";
        }
        if(attribute.get("managerName") != null && !attribute.get("managerName").toString().isEmpty()){
            where += " AND b.managername LIKE '%" + attribute.get("managerName").toString() +"%'";
        }
        if(attribute.get("managerPhone") != null && !attribute.get("managerPhone").toString().isEmpty()){
            where += " AND b.managerphonenumber LIKE '%" + attribute.get("managerPhone").toString() +"%'";
        }
        if(attribute.get("level") != null && !attribute.get("level").toString().isEmpty()){
            where += " AND b.level LIKE '%" + attribute.get("level").toString() +"%'";
        }
        if(attribute.get("floorArea") != null){
            where += " AND b.floorarea = " + attribute.get("floorArea").toString();
        }
        if(attribute.get("startRentArea") != null){
            where += " AND r.value >= " + attribute.get("startRentArea").toString();
        }
        if(attribute.get("endRentArea") != null){
            where += " AND r.value <= " + attribute.get("endRentArea").toString();
        }
        if(attribute.get("startRentPrice") != null){
            where += " AND b.rentprice >= " + attribute.get("startRentPrice").toString();
        }
        if (attribute.get("endRentPrice") != null){
            where += " AND b.rentprice <= " + attribute.get("endRentPrice").toString();
        }
        if (attribute.get("districtId") != null){
            where += " AND b.districtid = " + attribute.get("districtId").toString();
        }
        if (attribute.get("staffId") != null){
            where += " AND ab.staffid = " + attribute.get("staffId");
        }
        if (typeCode != null){
            where += " AND rt.code in (";
            for (String it: typeCode){
                where += "'" + it + "'";
                if (!it.equals(typeCode.get(typeCode.size() - 1))){
                    where += ", ";
                } else  where += ")";
            }
        }
        return where;
    }
}
