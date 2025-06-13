package com.project.travelquest.openapi.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class ApiExplorer {

    public static <T> T getApiJsonData(String serviceKey, String baseUrl, Class<T> dtoClass) {
        try {
            StringBuilder urlBuilder = new StringBuilder(baseUrl);
            urlBuilder.append("?serviceKey=").append(serviceKey);
            urlBuilder.append("&MobileApp=AppTest");
            urlBuilder.append("&MobileOS=ETC");
            urlBuilder.append("&arrange=A");
            urlBuilder.append("&contentTypeId=14");
            urlBuilder.append("&areaCode=34");
            urlBuilder.append("&_type=json");
            urlBuilder.append("&numOfRows=1000&pageNo=1");

            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader rd = new BufferedReader(new InputStreamReader(
                    conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300
                            ? conn.getInputStream()
                            : conn.getErrorStream()
            ));

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();

            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(sb.toString(), dtoClass);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
