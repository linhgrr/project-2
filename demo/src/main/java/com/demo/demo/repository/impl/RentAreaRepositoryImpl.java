package com.demo.demo.repository.impl;

import com.demo.demo.repository.RentAreaRepository;
import com.demo.demo.repository.entity.RentAreaEntity;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RentAreaRepositoryImpl implements RentAreaRepository {
    static String DB_URL = "jdbc:mysql://localhost:3306/estatebasic";
    static String USER = "root";
    static String PASS = "password";
    @Override
    public List<RentAreaEntity> findRentAreaById(Long id) {
        List <RentAreaEntity> results = new ArrayList<>();
        String sql = "SELECT * FROM rentarea\n" +
                "WHERE buildingid = " + id;
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
        ){
            while (rs.next()){
                RentAreaEntity rentAreaEntity = new RentAreaEntity();
                rentAreaEntity.setId(rs.getLong("id"));
                rentAreaEntity.setValue(rs.getLong("value"));
                rentAreaEntity.setBuildingId(rs.getLong("buildingid"));
                rentAreaEntity.setCreatedDate(rs.getDate("createddate"));
                rentAreaEntity.setModifiedDate(rs.getDate("modifieddate"));
                rentAreaEntity.setCreatedBy(rs.getString("createdby"));
                rentAreaEntity.setModifiedBy(rs.getString("modifiedby"));
                results.add(rentAreaEntity);
            }
            System.out.println("Ket noi robot thanh cong");
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println(sql);
        }
        return results;
    }
}
