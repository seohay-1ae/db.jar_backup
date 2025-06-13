<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>카카오 지도</title>
    <meta charset="UTF-8">
    <style>
        #map {
            width: 100%;
            height: 650px;
        }
    </style>
    <!-- ✅ 카카오맵 SDK (autoload=false 설정 필수) -->
    <script src="https://dapi.kakao.com/v2/maps/sdk.js?appkey=965da81937482b91465d0c491e837eba&libraries=services&autoload=false"></script>
</head>
<body>
<h2>카카오 지도</h2>
<div id="map"></div>

<!-- ✅ 우리가 만든 외부 JS -->
<script src="/static/js/map.js"></script>
</body>
</html>
