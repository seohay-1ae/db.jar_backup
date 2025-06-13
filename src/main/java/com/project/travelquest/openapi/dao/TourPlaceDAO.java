package com.project.travelquest.openapi.dao;

import com.project.travelquest.openapi.dto.TourPlaceApiResponse.Item;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

public class TourPlaceDAO {
    private Connection con;
    private PreparedStatement pstmt;
    private DataSource dataFactory;

    public TourPlaceDAO() {
        try {
            Context ctx = new InitialContext();
            Context envContext = (Context) ctx.lookup("java:/comp/env");
            dataFactory = (DataSource) envContext.lookup("jdbc/oracle");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int insertData(List<Item> itemList) {
        int result = 0;
        try {
            con = dataFactory.getConnection();
            String sql = "INSERT INTO tour_place (contentid, areacode, addr1, addr2, createdtime, firstimage, firstimage2, mapx, mapy, modifiedtime, sigungucode, tel, title, contenttypeid) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";

            for (Item item : itemList) {
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1, item.getContentid());
                pstmt.setString(2, item.getAreacode());
                pstmt.setString(3, item.getAddr1());
                pstmt.setString(4, item.getAddr2());
                pstmt.setString(5, item.getCreatedtime());
                pstmt.setString(6, item.getFirstimage());
                pstmt.setString(7, item.getFirstimage2());
                pstmt.setString(8, item.getMapx());
                pstmt.setString(9, item.getMapy());
                pstmt.setString(10, item.getModifiedtime());
                pstmt.setString(11, item.getSigungucode());
                pstmt.setString(12, item.getTel());
                pstmt.setString(13, item.getTitle());
                pstmt.setString(14, item.getContenttypeid());

                result = pstmt.executeUpdate();
            }

            pstmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("SQL 구문 실행 중 예외 발생: " + e.toString());
        }
        return result;
    }

    public List<Item> getList() {
        List<Item> items = new ArrayList<>();
        ResultSet rs = null;

        try {
            con = dataFactory.getConnection();
            pstmt = con.prepareStatement("SELECT * FROM tour_place");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Item item = new Item();
                item.setContentid(rs.getString("contentid"));
                item.setAreacode(rs.getString("areacode"));
                item.setAddr1(rs.getString("addr1"));
                item.setAddr2(rs.getString("addr2"));
                item.setCreatedtime(rs.getString("createdtime"));
                item.setFirstimage(rs.getString("firstimage"));
                item.setFirstimage2(rs.getString("firstimage2"));
                item.setMapx(rs.getString("mapx"));
                item.setMapy(rs.getString("mapy"));
                item.setModifiedtime(rs.getString("modifiedtime"));
                item.setSigungucode(rs.getString("sigungucode"));
                item.setTel(rs.getString("tel"));
                item.setTitle(rs.getString("title"));
                item.setContentid(rs.getString("contenttypeid"));

                items.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return items;
    }
}
