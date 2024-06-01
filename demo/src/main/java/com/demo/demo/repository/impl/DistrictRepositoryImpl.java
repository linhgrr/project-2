package com.demo.demo.repository.impl;

import com.demo.demo.repository.DistrictRepository;
import com.demo.demo.repository.entity.BuildingEntity;
import com.demo.demo.repository.entity.DistrictEntity;

import java.sql.*;

public class DistrictRepositoryImpl implements DistrictRepository {
    static String DB_URL = "jdbc:mysql://localhost:3306/estatebasic";
    static String USER = "root";
    static String PASS = "password";
    @Override
    public DistrictEntity findDistrictById(Long id) {
        String sql = "SELECT district.* \n" +
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
            System.out.println("Ket noi robot thanh cong");
        }catch (SQLException e){
            e.printStackTrace();
        }
        return results;
    }
}
