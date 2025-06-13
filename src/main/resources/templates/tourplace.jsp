<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.project.travelquest.openapi.dto.TourPlaceDTO" %>
<html>
<head>
    <title>Tour Places</title>
</head>
<body>
<h2>관광지 목록</h2>
<table border="1">
    <tr>
        <th>콘텐츠ID</th><th>제목</th><th>주소</th>
    </tr>
    <%
        List<TourPlaceDTO> itemList = (List<TourPlaceDTO>) request.getAttribute("itemList");
        if (itemList != null) {
            for (TourPlaceDTO dto : itemList) {
    %>
    <tr>
        <td><%= dto.getContentId() %></td>
        <td><%= dto.getTitle() %></td>
        <td><%= dto.getAddr1() %></td>
    </tr>
    <%
            }
        }
    %>
</table>
</body>
</html>
