<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.OrderList" %>
<%
OrderList ol = (OrderList)request.getAttribute ("ol");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>注文リスト画面</title>
</head>
<body>
<h1>注文リスト画面</h1>
	<ul><%= ol.getProductName() %>
		<li></li>
		<li><%= ol.getToppingName() %></li><li><%= ol.getToppingQuantity() %></li><li><%= ol.getToppingPrice() %></li>
    	<li><input type = "button" name = "minusButton" value = "-"></li>
    	<li><input type = "number" name = "orderQuantity"></li>
    	<li><input type = "button" name = "plusButton" value = "+"></li>
    	<li><%= ol.getSubTotal() %></li>
    	<li><input type = "button" name = "orderChangeButton" value = "変更"></li>
    </ul>
    
    
    
    
</body>
<footer>
<input type = "button" name = "menuButton" value="メニュー">
<input type = "button" name = "orderButton" value="注文する">
</footer>

</html>