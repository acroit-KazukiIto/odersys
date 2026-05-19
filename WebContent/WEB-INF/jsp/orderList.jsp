<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.OrderListInfo"%>
<%
OrderListInfo ol = (OrderListInfo) request.getAttribute("ol");
Object tableObj = session.getAttribute("tableNumber");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>注文リスト画面</title>
</head>

<body>
<form action="OrderRemoveServlet" method="post">
<h2>オーダ削除はこちらから</h2>
<input type="text" name="orderId">
<button type="submit" name="Button" value="削除">削除</button>
</form>

	<c:forEach var="ol" items="ol">

		<ul><%=ol.getOrderId() %><%=ol.getProductName()%><%=ol.getProductPrice() %>
			<form action="OrderListServlet" method="get">
				<li></li>
				<li><%=ol.getToppingName()%></li>
				<li><%=ol.getToppingQuantity()%></li>
				<li><%=ol.getToppingPrice()%></li>
				<li><button type="submit" name="Button" value="-">-</button></li>
				<li><%=ol.getProductQuantity()%></li>
				<li><button type="submit" name="Button" value="+">+</button></li>
				<li><%=ol.getSubTotal()%></li>
			</form>
			<form action="ItemDetailsChangeServlet" method="get">
				<li><button type="submit" name="Button" value="変更">変更</button></li>
			</form>

		</ul>
	</c:forEach>

</body>
<footer>

	<div="right">
		<form action="OrderCompleteServlet" method="get">
			<button type="submit" name="Button" value="注文">注文</button>
		</form>
	</div>
	<div="center">1卓</div>
	<div="left">
		<form action="ShowMenuServlet" method="get">
			<button type="submit" name="Button" value="メニュー">メニュー</button>
		</form>
	</div>

</footer>

</html>