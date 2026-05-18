<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.OrderListInfo" %>
<%
OrderListInfo ol = (OrderListInfo)request.getAttribute ("ol");
Object tableObj = session.getAttribute("tableNumber");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>注文リスト画面</title>
</head>

<body>
<form action="/OrderListServlet" method="get">
	<ul><%= ol.getProductName() %>
		<li></li>
		<li><%= ol.getToppingName() %></li><li><%= ol.getToppingQuantity() %></li><li><%= ol.getToppingPrice() %></li>
    	<li><button type = "submit" name = "Button" value = "-">-</button></li>
    	<li></li>
    	<li><button type = "submit" name = "Button" value = "+">+</button></li>
    	<li><%= ol.getSubTotal() %></li>
 		<form action="ItemDetailsChangeServlet" method="post">
    		<li><button type = "submit" name = "Button" value = "変更">変更</button></li>
    	</form>
    </ul>
    
    
    
</form>

</body>
<footer>
<form action="OrderListServlet" method="post">
	<div ="right">
		<button type = "submit" name = "Button" value = "注文">注文</button>
	</div>
	<div ="center">
		1卓
	</div>
	<div ="left">
		<button type = "submit" name = "Button" value = "メニュー">メニュー</button>
	</div>
</form>
</footer>

</html>