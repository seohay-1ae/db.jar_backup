package com.project.travelquest.openapi.controller;

import com.project.travelquest.openapi.service.TourPlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TourPlaceController {

    private final TourPlaceService tourPlaceService;

    @GetMapping("/tour-place/fetch")
    public String fetchTourPlace() {
        int savedCount = tourPlaceService.fetchAndSaveTourPlaces();
        return savedCount > 0
                ? "데이터 저장 완료: " + savedCount + "건"
                : "저장할 데이터가 없거나 실패함";
    }
}
