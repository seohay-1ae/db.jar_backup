package com.project.travelquest.openapi.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.travelquest.openapi.dto.TourPlaceItem;
import com.project.travelquest.openapi.dao.TourPlaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.net.URI;

@Service
@RequiredArgsConstructor
@Slf4j
public class TourPlaceService {

    private final TourPlaceRepository tourPlaceRepository;

    private final String BASE_URL = "https://apis.data.go.kr/B551011/KorService2/areaBasedList2";

    public int fetchAndSaveTourPlaces() {
        int insertedCount = 0;
        try {
            RestTemplate restTemplate = new RestTemplate();
            ObjectMapper mapper = new ObjectMapper();

            String rawServiceKey = "ldwOHe0GqXzqMYbwXnHZC+j/vqlORiQ2TytN8jKHOem5eChSUm5P2CnQFB5yI7rbxk+Ip9ZB+T8pSyvXG/pumA==";
            String encodedServiceKey = URLEncoder.encode(rawServiceKey, StandardCharsets.UTF_8);

            log.info("원본 서비스 키: {}", rawServiceKey);
            log.info("인코딩된 서비스 키: {}", encodedServiceKey);

            String url = BASE_URL +
                    "?serviceKey=" + encodedServiceKey +
                    "&MobileApp=AppTest" +
                    "&MobileOS=ETC" +
                    "&arrange=Q" +
                    "&contentTypeId=14" +
                    "&areaCode=34" +
                    "&_type=json" +
                    "&numOfRows=70" +
                    "&pageNo=1";

            log.info("최종 요청 URL: {}", url);

            URI apiUrl = new URI(url);


            HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "Mozilla/5.0");
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);

            if (response.getStatusCode() == HttpStatus.OK &&
                    response.getHeaders().getContentType() != null &&
                    response.getHeaders().getContentType().toString().contains("json")) {

                String jsonString = response.getBody();
                JsonNode root = mapper.readTree(jsonString);
                JsonNode itemsNode = root.path("response").path("body").path("items").path("item");

                List<TourPlaceItem> tourPlaces = new ArrayList<>();

                if (itemsNode.isArray()) {
                    for (JsonNode node : itemsNode) {
                        if (!node.path("firstimage").asText().isBlank()) {
                            TourPlaceItem dto = mapper.treeToValue(node, TourPlaceItem.class);
                            tourPlaces.add(dto);
                        }
                    }
                }

                if (!tourPlaces.isEmpty()) {
                    insertedCount = tourPlaceRepository.insertTourPlaces(tourPlaces);
                    log.info("관광지 {}건 저장 완료", insertedCount);
                } else {
                    log.warn("저장할 관광지 데이터가 없습니다.");
                }

            } else {
                log.error("JSON 응답이 아닙니다. Content-Type: {}, 응답 내용:\n{}",
                        response.getHeaders().getContentType(), response.getBody());
            }

        } catch (Exception e) {
            log.error("관광지 저장 중 오류 발생", e);
        }
        return insertedCount;
    }
}
