package com.project.travelquest.openapi.dao;

import com.project.travelquest.openapi.dto.TourPlaceItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

@Repository
@Slf4j
public class TourPlaceRepository {

    private final DataSource dataSource;

    public TourPlaceRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public int insertTourPlaces(List<TourPlaceItem> places) {
        String sql = "INSERT INTO tour_place (contentid, areacode, addr1, addr2, createdtime, firstimage, firstimage2, mapx, mapy, modifiedtime, sigungucode, tel, title, contenttypeid) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int count = 0;

        try (Connection conn = dataSource.getConnection()) {
            for (TourPlaceItem item : places) {
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
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
                    count += pstmt.executeUpdate();
                }
            }
        } catch (Exception e) {
            log.error("관광지 저장 중 DB 오류", e);
        }

        return count;
    }
}
