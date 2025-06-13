package com.project.travelquest.map.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MapController {

    @GetMapping("/map")
    public String mapPage() {
        return "map/map";  // /WEB-INF/views/map/map.jsp로 매핑됨
    }
}
