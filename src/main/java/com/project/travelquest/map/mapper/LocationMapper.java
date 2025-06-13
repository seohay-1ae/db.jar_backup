package com.project.travelquest.map.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.project.travelquest.map.model.Location;

@Mapper
public interface LocationMapper {
    List<Location> getAllLocations();
}
