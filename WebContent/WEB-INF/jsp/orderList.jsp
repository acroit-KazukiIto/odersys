<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String tableNum = (String) session.getAttribute("tableNumber");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>注文リスト画面</title>
<link rel="stylesheet" href="./css/style.css">
</head>
<body>
	<div class="container">
		<div class="content">
			<c:choose>
				<c:when test="${empty olList}">
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
				</c:when>
				<c:otherwise>

					<c:forEach var="item" items="${olList}">

						<input type="hidden" name="oid" value="${item.orderId}">
						<input type="hidden" name="oid" value="${item.subTotal}">
						<table 　class="order-table">
							<tr>
								<th>${item.orderId}${item.productName}${item.productPrice}</th>
								<th></th>
								<th></th>
							</tr>
							<c:if test="${!empty item.toppings}">
								<c:forEach var="t" items="${item.toppings }">
									<tr>
										<td>・${t.name}✕${t.quantity}</td>
									</tr>
								</c:forEach>
								<tr>
									<form action="ItemDetailsChangeServlet" method="get">
										<input type="hidden" name="oid" value="${item.orderId}">
										<td><button type="submit" name="Button" value="変更">変更</button>
										</td>
									</form>
								</tr>
							</c:if>
							<tr>
								<c:if test="${item.orderQuantity == 1}">
									<form action="OrderRemoveServlet" method="post">
										<input type="hidden" name="oid" value="${item.orderId}">
										<td><button type="submit" name="Button" value="削除">削除</button>
										</td>
									</form>
								</c:if>
								<c:if test="${item.orderQuantity > 1}">
									<form action="OrderListServlet" method="post">
										<input type="hidden" name="oid" value="${item.orderId}">
										<td><button type="submit" name="Button" value="-">-</button>
										</td>
									</form>
								</c:if>
								<td>${item.orderQuantity}</td>
								<c:if test="${item.orderQuantity <10}">
									<form action="OrderListServlet" method="post">
										<input type="hidden" name="oid" value="${item.orderId}">
										<td><button type="submit" name="Button" value="+">+</button>
										</td>
									</form>
								</c:if>
								<c:if test="${item.orderQuantity == 10}">
									<td>注文上限です</td>
								</c:if>


							</tr>
							<tr>
								<td>小計：${item.subTotal}</td>
							</tr>



						</table>
						<p>----------------------------------------------------------</p><br>
					</c:forEach>
				</c:otherwise>
			</c:choose>
			合計：${aop.allOrderPrice}

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
			</div>
		</div>
</body>


</html>