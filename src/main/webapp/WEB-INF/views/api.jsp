<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>레저 스포츠 시설 조회</title>
    <style>
        table { width: 100%; border-collapse: collapse; margin-top: 1rem; }
        th, td { border: 1px solid #888; padding: 8px; text-align: center; }
        th { background: #f0f0f0; }
        .error { color: red; font-weight: bold; }
    </style>
</head>
<body>
<h2>레저 스포츠 시설 조회 결과</h2>

<c:choose>
    <c:when test="${not empty errorMsg}">
        <div class="error">${errorMsg}</div>
    </c:when>
    <c:when test="${not empty items}">
        <table>
            <thead>
            <tr>
                <th>시군구명</th>
                <th>시설종목명</th>
                <th>홈페이지주소</th>
                <th>시설명</th>
                <th>도로명주소</th>
                <th>전화번호</th>
                <th>행번호</th>
                <th>시도</th>
                <th>지번주소</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="item" items="${items}">
                <tr>
                    <td>${item.sgg_nm}</td>
                    <td>${item.code_nm}</td>
                    <td><a href="${item.homepage_url}" target="_blank">${item.homepage_url}</a></td>
                    <td>${item.faci_nm}</td>
                    <td>${item.faci_road_addr}</td>
                    <td>${item.faci_tel_no}</td>
                    <td>${item.row_num}</td>
                    <td>${item.ctpv_nm}</td>
                    <td>${item.faci_post_addr}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:when>
    <c:otherwise>
        <p>표시할 데이터가 없습니다.</p>
    </c:otherwise>
</c:choose>

</body>
</html>
