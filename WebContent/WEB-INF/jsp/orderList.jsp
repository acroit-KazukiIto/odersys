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
<form action="OrderListServlet" method="post">
	<ul><%= ol.getProductName() %>
		<li></li>
		<li><%= ol.getToppingName() %></li><li><%= ol.getToppingQuantity() %></li><li><%= ol.getToppingPrice() %></li>
    	<li><input type = "button" name = "Button" value = "-"></li>
    	<li></li>
    	<li><input type = "button" name = "Button" value = "+"></li>
    	<li><%= ol.getSubTotal() %></li>
    	<form action="ItemDetailsChangeServlet" method="post">
    		<li><input type = "button" name = "Button" value = "変更"></li>
    	</form>
    </ul>
    
    
    
</form>

</body>
<footer>
<form action="OrderListServlet" method="post">
<div ="right">
	<input type ="button" name ="Button" value ="メニュー">
</div>
<div ="center">
	1卓
</div>
<div ="left">
	<input type ="button" name ="Button" value ="注文する">
</div>
</form>
</footer>

</html>