package com.project.travelquest.openapi.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.travelquest.openapi.dao.TourPlaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class TourPlaceIntroBatchService {

    private final TourPlaceRepository tourPlaceRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();

    private final String API_URL = "https://apis.data.go.kr/B551011/KorService2/detailIntro2";
    private final String SERVICE_KEY_RAW = "ldwOHe0GqXzqMYbwXnHZC+j/vqlORiQ2TytN8jKHOem5eChSUm5P2CnQFB5yI7rbxk+Ip9ZB+T8pSyvXG/pumA==";

    public void executeDetailIntroUpdate() {
        List<Map<String, Object>> contentList = tourPlaceRepository.getAllContentIdAndType();
        String encodedServiceKey = URLEncoder.encode(SERVICE_KEY_RAW, StandardCharsets.UTF_8);

        for (Map<String, Object> entry : contentList) {
            String contentId = entry.get("contentid").toString();
            String contentTypeId = entry.get("contenttypeid").toString();

            try {
                String requestUrl = String.format(
                        "%s?serviceKey=%s&MobileApp=AppTest&MobileOS=ETC&pageNo=1&numOfRows=10&_type=json&contentId=%s&contentTypeId=%s",
                        API_URL, encodedServiceKey, contentId, contentTypeId
                );

                URI uri = new URI(requestUrl);
                String response = restTemplate.getForObject(uri, String.class);
                JsonNode itemsNode = objectMapper.readTree(response)
                        .path("response").path("body").path("items").path("item");

                if (!itemsNode.isArray() || itemsNode.size() == 0) {
                    log.warn("contentId {} 의 intro 데이터가 없습니다.", contentId);
                    continue;
                }

                JsonNode item = itemsNode.get(0);

                // 공통 필드
                String openDate = item.path("opendate").asText(null);

                // 초기화
                String infocenter = null;
                String restdate = null;
                String usetime = null;
                String usefee = null;
                String usetimeculture = null;

                if ("12".equals(contentTypeId)) {
                    infocenter = item.path("infocenter").asText(null);
                    restdate = item.path("restdate").asText(null);
                    usetime = item.path("usetime").asText(null);

                } else if ("14".equals(contentTypeId)) {
                    infocenter = item.path("infocenterculture").asText(null);
                    restdate = item.path("restdateculture").asText(null);
                    usetime = item.path("usetimeculture").asText(null);
                    usefee = item.path("usefee").asText(null);
                    usetimeculture = usetime;
                }

                // DB 업데이트
                tourPlaceRepository.updateTourPlaceIntroCommon(contentId, openDate, infocenter, restdate, usetime);
                if ("14".equals(contentTypeId) && usefee != null) {
                    tourPlaceRepository.updateTourPlaceIntroUseFee(contentId, usefee);
                }

                log.info("Intro 정보 업데이트 완료 - contentId: {}", contentId);
                Thread.sleep(200); // API 과도 호출 방지

            } catch (Exception e) {
                log.error("Intro API 처리 중 오류 - contentId: " + contentId, e);
            }
        }
    }
}
