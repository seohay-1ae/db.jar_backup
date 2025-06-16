package com.project.travelquest.openapi.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.travelquest.openapi.dao.TourPlaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TourPlaceDetailBatchService {

    private final TourPlaceRepository tourPlaceRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();

    private final String API_URL = "https://apis.data.go.kr/B551011/KorService2/detailCommon2";
    private final String SERVICE_KEY_RAW = "ldwOHe0GqXzqMYbwXnHZC+j/vqlORiQ2TytN8jKHOem5eChSUm5P2CnQFB5yI7rbxk+Ip9ZB+T8pSyvXG/pumA==";

    public void executeDetailUpdate() {
        List<String> contentIds = tourPlaceRepository.getAllContentIds();

        String encodedServiceKey = URLEncoder.encode(SERVICE_KEY_RAW, StandardCharsets.UTF_8);

        for (String contentid : contentIds) {
            try {
                String encodedContentId = URLEncoder.encode(contentid, StandardCharsets.UTF_8);

                String requestUrl = String.format(
                        "%s?serviceKey=%s&MobileApp=AppTest&MobileOS=ETC&pageNo=1&numOfRows=10&contentId=%s&_type=json",
                        API_URL, encodedServiceKey, encodedContentId
                );

                URI uri = new URI(requestUrl);

                String response = restTemplate.getForObject(uri, String.class);
                JsonNode root = objectMapper.readTree(response);
                JsonNode itemsNode = root.path("response").path("body").path("items").path("item");

                if (itemsNode.isMissingNode() || itemsNode.isNull() || !itemsNode.isArray() || itemsNode.size() == 0) {
                    log.warn("contentid {} 에 대한 상세 데이터가 없습니다.", contentid);
                    continue;  // 데이터 없으면 다음 처리로
                }

                JsonNode itemNode = itemsNode.get(0); // 배열 첫 번째 요소

                String homepage = itemNode.path("homepage").asText(null);
                String overview = itemNode.path("overview").asText(null);
                String tel = itemNode.path("tel").asText(null);

                // HTML 태그 제거 (예: <a href=...>) — 필요 시만 적용
                if (homepage != null && !homepage.isEmpty()) {
                    homepage = Jsoup.parse(homepage).text();
                }

                tourPlaceRepository.updateTourPlaceDetail(contentid, homepage, overview, tel);

                log.info("Updated contentid: {}", contentid);

                Thread.sleep(200); // API 요청 제한 대비

            } catch (Exception e) {
                log.error("공공데이터 API 처리 중 오류 - contentid: " + contentid, e);
            }
        }
    }
}
