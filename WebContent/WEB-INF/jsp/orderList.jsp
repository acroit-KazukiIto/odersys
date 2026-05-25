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
<link rel="stylesheet" href="./css/style.css">
</head>

<body>
	<%
	if (oid == 0) {
	%>
	<h1>リストはからです。</h1>
	<div = footer1>
	<div="center"><%=tableNum%>卓
	</div>
	<div="left">
		<form action="ShowMenuServlet" method="get">
			<button type="submit" name="Button" value="メニュー">メニュー</button>
		</form>
	</div>

</footer>
	<%
	
	} else {
	%>
	<form action="OrderRemoveServlet" method="post">
		<h2>オーダ削除はこちらから</h2>
		<input type="text" name="orderId">
		<button type="submit" name="Button" value="削除">削除</button>
	</form>
	
	<%for(OrderListInfo ol : olList) {%>
	<form action="OrderListServlet" method="post">
	<input type="hidden" name="oid" value="<%=ol.getOrderId()%>">
	<input type="hidden" name="oid" value="<%=ol.getSubTotal()%>">
	<table>
	<tr>
	<th><%=ol.getOrderId()%></th><th><%=ol.getProductName()%></th><th><%=ol.getProductPrice()%></th>
	</tr>
	<tr>
	<td><%=ol.getToppingName()%></td><td><%=ol.getToppingQuantity()%></td><td><%=ol.getToppingPrice()%></td>
	</tr>
	<tr>
	<td><button type="submit" name="Button" value="-">-</button></td>
	</tr>
	<tr>
	<td><%=ol.getProductQuantity()%></td>
	</tr>
	<tr>
	<td><button type="submit" name="Button" value="+">+</button></td>
	</tr>
	<tr>
	<td><form action="ItemDetailsChangeServlet" method="get">
			<button type="submit" name="Button" value="変更">変更</button>
		</form></td><td><%=ol.getSubTotal()%></td>
	</tr>
	
	
	</table>
		</form>
		

	</ul>
	<%
	}
	%>
	${aop.allOrderPrice}
	
	<div = footer2>

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
	<%
	}
	%>
</body>


</html>