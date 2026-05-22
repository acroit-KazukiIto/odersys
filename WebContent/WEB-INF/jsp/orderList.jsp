<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="model.OrderListInfo"%>
<%
List<OrderListInfo> olList = (List<OrderListInfo>) request.getAttribute("olList");
//OrderListInfo aop = (OrderListInfo) request.getAttribute("allOrderPrice");
int oid = olList.size();
int oid2 = olList.size();
System.out.println("リスト確認：" + oid);

String tableNum = (String) session.getAttribute("tableNumber");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>注文リスト画面</title>
</head>

<body>
	<%
	if (oid == 0) {
	%>
	<h1>リストはからです。</h1>
	<%
	} else {
	%>
	<form action="OrderRemoveServlet" method="post">
		<h2>オーダ削除はこちらから</h2>
		<input type="text" name="orderId">
		<button type="submit" name="Button" value="削除">削除</button>
	</form>
	
	<%for(OrderListInfo ol : olList) {%>
	
	<ul><%=ol.getOrderId()%><%=ol.getProductName()%><%=ol.getProductPrice()%>
		<form action="OrderListServlet" method="post">
			<input type="hidden" name="oid" value="<%=ol.getOrderId()%>">
			<input type="hidden" name="oid" value="<%=ol.getSubTotal()%>">
			<li><%=ol.getToppingName()%></li>
			<li><%=ol.getToppingQuantity()%></li>
			<li><%=ol.getToppingPrice()%></li>
			<li><button type="submit" name="Button" value="-">-</button></li>
			<li><%=ol.getProductQuantity()%></li>
			<li><button type="submit" name="Button" value="+">+</button></li>
			<li><%=ol.getSubTotal()%></li>
			<li></li>
		</form>
		<form action="ItemDetailsChangeServlet" method="get">
			<li><button type="submit" name="Button" value="変更">変更</button></li>
		</form>

	</ul>
	<%
	}
	%>
	${aop.allOrderPrice}
	<%
	}
	%>
	

</body>
<footer>

	<div="right">
		<form action="OrderCompleteServlet" method="get">
			<button type="submit" name="Button" value="注文">注文</button>
		</form>
	</div>
	<div="center"><%=tableNum%>卓
	</div>
	<div="left">
		<form action="ShowMenuServlet" method="get">
			<button type="submit" name="Button" value="メニュー">メニュー</button>
		</form>
	</div>

</footer>

</html>