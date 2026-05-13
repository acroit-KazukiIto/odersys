<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page import="model.OrderList" %>

<%@OrderList ol = (OrderList)request.getAttribute("orderList") %>

<html>
<head>
<meta charset="UTF-8">
<title>注文リスト画面</title>
</head>
<body>
	<ul>
        <%-- "orderList" として取得したリストをループ表示 --%>
        <c:forEach var="item" items="${orderList}">
            <li><c:out value="${item}" /></li>
        </c:forEach>
    </ul>
</body>
<footer>
<input type = "button" name = "menuButton" value="メニュー">
<input type = "button" name = "orderButton" value="注文する">
</footer>
</html>