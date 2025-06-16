package com.project.travelquest.openapi.controller;

import com.project.travelquest.openapi.service.TourPlaceIntroBatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/batch")
public class TourIntroBatchController {
    private final TourPlaceIntroBatchService batchService;

    @PostMapping("/intro-update")
    public String executeTourPlaceDetailBatch() {
        batchService.executeDetailIntroUpdate();
        return "Intro batch update 실행 완료";
    }
}
