window.onload = function () {
    kakao.maps.load(function () {
        const container = document.getElementById('map');
        const options = {
            center: new kakao.maps.LatLng(36.5184, 126.8000), // 충남 중심 좌표
            level: 10
        };
        const map = new kakao.maps.Map(container, options);

        // ✅ 여기서 DB 위치 정보를 불러와 마커 생성
        fetch('/api/locations')
            .then(res => res.json())
            .then(locations => {
                locations.forEach(loc => {
                    // console.log(`[DEBUG] ${loc.title} 위치: (${loc.mapy}, ${loc.mapx})`);
                    const marker = new kakao.maps.Marker({
                        map: map,
                        position: new kakao.maps.LatLng(loc.mapy, loc.mapx),
                        title: loc.title
                    });

                    const infowindow = new kakao.maps.InfoWindow({
                        content: `<div style="padding:5px;">${loc.title}<br/>${loc.addr1}</div>`
                    });

                    // 마커에 마우스 올리면 인포윈도우 표시
                    kakao.maps.event.addListener(marker, 'mouseover', function () {
                        infowindow.open(map, marker);
                    });
                    kakao.maps.event.addListener(marker, 'mouseout', function () {
                        infowindow.close();
                    });
                });
            });
    });
};
