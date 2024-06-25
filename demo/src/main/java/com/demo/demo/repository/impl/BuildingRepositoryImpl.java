package com.demo.demo.repository.impl;

import com.demo.demo.repository.BuildingRepository;
import com.demo.demo.repository.entity.BuildingEntity;
import com.demo.demo.utils.NumberUtil;
import com.demo.demo.utils.StringUtil;
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
        StringBuilder sql = new StringBuilder("SELECT b.* FROM estatebasic.building b\n");
        joinProcess(attribute, typeCode, sql);

        StringBuilder where = new StringBuilder("WHERE 1=1 ");
        querrySqlNormal(attribute, where);
        querrySqlSpecial(attribute, typeCode, where);

        sql.append(where).append(" \nGROUP BY b.id");
        System.out.println(sql);


        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(String.valueOf(sql));
        ){
            while (rs.next()){
                BuildingEntity buildingEntity = new BuildingEntity();
                buildingEntity.setId(rs.getLong("id"));
                buildingEntity.setName(rs.getString("name"));
                buildingEntity.setDistrictId(rs.getLong("districtid"));
                buildingEntity.setStreet(rs.getString("street"));
                buildingEntity.setWard(rs.getString("ward"));
                buildingEntity.setNumberOfBasement(rs.getLong("numberofbasement"));
                buildingEntity.setManagerName(rs.getString("managername"));
                buildingEntity.setManagerPhone(rs.getString("managerphonenumber"));
                buildingEntity.setFloorArea(rs.getLong("floorarea"));
                buildingEntity.setBrokerageFee(rs.getLong("brokeragefee"));
                buildingEntity.setRentPrice(rs.getLong("rentprice"));
                results.add(buildingEntity);
            }
            System.out.println(sql);
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println(sql);
        }
        return results;
    }


    private void joinProcess(Map<Object, Object> attribute, List<String> typeCode, StringBuilder sql){
        String stadffId = (String) attribute.get("staffId");
        if (StringUtil.checkData(stadffId)){
            sql.append("JOIN assignmentbuilding ab\n" +
                    "ON ab.buildingid = b.id\n");
        }

        String startRentArea = (String) attribute.get("startRentArea");
        String endRentArea = (String) attribute.get("endRentArea");
        if (StringUtil.checkData(startRentArea) || StringUtil.checkData(endRentArea)){
            sql.append("JOIN rentarea r\n" +
                    "ON r.buildingid = b.id\n");
        }

        if (typeCode != null && !typeCode.isEmpty()){
            sql.append("JOIN buildingrenttype br\n" +
                    "ON br.buildingid = b.id\n" +
                    "JOIN renttype rt\n" +
                    "ON rt.id = br.renttypeid\n");
        }
    }

    private void querrySqlNormal(Map<Object, Object> attribute, StringBuilder where){
        for(Map.Entry<Object, Object> item : attribute.entrySet()){
            String key = (String)item.getKey();
            if(!key.equals("staffId") && !key.equals("typeCode") && !key.endsWith("RentArea") && !key.endsWith("RentPrice")){
                String value = (String)item.getValue();
                if (NumberUtil.isNumber(value)){
                    where.append(" AND b.").append(key.toLowerCase()).append(" = ").append(value);
                }
                else{
                    where.append(" AND b.").append(key.toLowerCase()).append(" LIKE '%").append(value).append("%' ");
                }
            }
        }
    }

    private void querrySqlSpecial(Map<Object, Object> attribute, List<String> typeCode, StringBuilder where){
        String startRentArea = (String)attribute.get("startRentArea");
        String endRentArea = (String)attribute.get("endRentArea");
        if (StringUtil.checkData(startRentArea) || StringUtil.checkData(endRentArea)){
            if (StringUtil.checkData(startRentArea)){
                where.append(" AND r.value >= " + startRentArea);
            }
            if (StringUtil.checkData(endRentArea)){
                where.append(" AND r.value <=" + endRentArea);
            }
        }
        String startRentPrice = (String)attribute.get("startRentPrice");
        String endRentPrice = (String)attribute.get("endRentPrice");
        if (StringUtil.checkData(startRentPrice) || StringUtil.checkData(endRentPrice)){
            if (StringUtil.checkData(startRentPrice)){
                where.append(" AND b.rentprice >= " + startRentPrice);
            }
            if (StringUtil.checkData(endRentPrice)){
                where.append(" AND b.rentprice <=" + endRentPrice);
            }
        }

        String staffId = (String)attribute.get("staffId");
        if(StringUtil.checkData(staffId)){
            where.append(" AND ab.staffid = " + staffId);
        }

        if (typeCode != null && !typeCode.isEmpty()){
            where.append(" AND rt.code in (");
            for (String it: typeCode){
                where.append("'" + it + "'") ;
                if (!it.equals(typeCode.get(typeCode.size() - 1))){
                    where.append(", ");
                } else  where.append(") ");
            }
        }
    }

}
