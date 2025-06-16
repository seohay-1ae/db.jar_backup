package com.project.travelquest.openapi.dao;

import com.project.travelquest.openapi.dto.TourPlaceItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

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

    // 1. contentid 목록 불러오기
    public List<String> getAllContentIds() {
        List<String> contentIds = new ArrayList<>();
        String sql = "SELECT contentid FROM tour_place";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                contentIds.add(rs.getString("contentid"));
            }
        } catch (Exception e) {
            log.error("contentid 목록 조회 중 오류", e);
        }

        return contentIds;
    }

    // 🔹 contentid + contenttypeid 목록 반환
    public List<Map<String, Object>> getAllContentIdAndType() {
        List<Map<String, Object>> result = new ArrayList<>();
        String sql = "SELECT contentid, contenttypeid FROM tour_place";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("contentid", rs.getString("contentid"));
                row.put("contenttypeid", rs.getString("contenttypeid"));
                result.add(row);
            }

        } catch (Exception e) {
            log.error("contentid, contenttypeid 목록 조회 중 오류", e);
        }

        return result;
    }

    // 2. 관광지 상세 정보 업데이트
    public void updateTourPlaceDetail(String contentid, String homepage, String overview, String tel) {
        String sql = "UPDATE tour_place SET homepage = ?, overview = ?, tel = ? WHERE contentid = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, homepage);
            pstmt.setString(2, overview);
            pstmt.setString(3, tel);
            pstmt.setString(4, contentid);

            pstmt.executeUpdate();

        } catch (Exception e) {
            log.error("관광지 상세 정보 업데이트 중 오류 - contentid: " + contentid, e);
        }
    }

    // 🔹 intro 공통 정보 업데이트 (contentTypeId 12, 14 공통)
    public void updateTourPlaceIntroCommon(String contentid, String opendate, String infocenter,
                                           String restdate, String usetime) {
        String sql = "UPDATE tour_place SET opendate = ?, infocenter = ?, restdate = ?, usetime = ? WHERE contentid = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, opendate);
            pstmt.setString(2, infocenter);
            pstmt.setString(3, restdate);
            pstmt.setString(4, usetime);
            pstmt.setString(5, contentid);

            pstmt.executeUpdate();

        } catch (Exception e) {
            log.error("intro 공통 정보 업데이트 중 오류 - contentid: " + contentid, e);
        }
    }

    // 🔹 intro useFee 추가 업데이트 (contentTypeId = 14 전용)
    public void updateTourPlaceIntroUseFee(String contentid, String usefee) {
        String sql = "UPDATE tour_place SET usefee = ? WHERE contentid = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usefee);
            pstmt.setString(2, contentid);

            pstmt.executeUpdate();

        } catch (Exception e) {
            log.error("useFee 업데이트 중 오류 - contentid: " + contentid, e);
        }
    }
}
