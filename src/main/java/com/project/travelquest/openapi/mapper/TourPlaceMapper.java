package com.project.travelquest.openapi.mapper;

import com.project.travelquest.openapi.dto.TourPlaceDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TourPlaceMapper {
    int insertTourPlace(TourPlaceDTO tourPlace);

    int insertTourPlaces(@Param("list") List<TourPlaceDTO> tourPlaceList);
}
