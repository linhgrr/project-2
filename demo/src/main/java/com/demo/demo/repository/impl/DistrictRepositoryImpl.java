package com.demo.demo.repository.impl;

import com.demo.demo.repository.DistrictRepository;
import com.demo.demo.repository.entity.BuildingEntity;
import com.demo.demo.repository.entity.DistrictEntity;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class DistrictRepositoryImpl implements DistrictRepository {
    static String DB_URL = "jdbc:mysql://localhost:3306/estatebasic";
    static String USER = "root";
    static String PASS = "password";
    @Override
    public DistrictEntity findDistrictById(Long id) {
        String sql = "SELECT district.* FROM district\n" +
                "WHERE district.id = " + id.toString();
        DistrictEntity results = new DistrictEntity();
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
        ){
            while (rs.next()){
                results.setId(rs.getLong("id"));
                results.setCode(rs.getString("code"));
                results.setName(rs.getString("name"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return results;
    }
}
