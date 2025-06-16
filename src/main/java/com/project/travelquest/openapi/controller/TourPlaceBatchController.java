package com.project.travelquest.openapi.controller;

import com.project.travelquest.openapi.service.TourPlaceDetailBatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/batch")
public class TourPlaceBatchController {

    private final TourPlaceDetailBatchService batchService;

    // 수동 실행용 API
    @PostMapping("/tour-place-detail")
    public String executeTourPlaceDetailBatch() {
        batchService.executeDetailUpdate();
        return "관광지 상세정보 업데이트 배치 실행 완료";
    }
}
