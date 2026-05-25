<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page
	import="java.util.List,model.OrderListInfo, model.ItemDetailsInfo"%>
<%@ page import="java.util.Set"%>

<%
String productId = (String) request.getAttribute("productId");

String pName = (String) request.getAttribute("selectedPName");

Integer pPrice = (Integer) request.getAttribute("selectedPPrice");

List<ItemDetailsInfo> toppingList = (List<ItemDetailsInfo>) request.getAttribute("toppingList");

OrderListInfo ol = (OrderListInfo) request.getAttribute("ol");

Integer subTotal = (Integer) request.getAttribute("subTotal");

String category = (String) request.getAttribute("currentCategory");

String tableNum = (String) session.getAttribute("tableNumber");

String formAction = (String) request.getAttribute("formAction");

if (formAction == null) {
	formAction = "ItemDetailsServlet";
}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>商品詳細</title>
</head>

<body style="margin: 0; padding-bottom: 120px; font-family: sans-serif;">

	<form action="ItemDetailsChangeServlet" method="post">
		<input type="hidden" name="oid" value="<%=ol.getOrderId()%>">
		<input type="hidden" name="op" value="<%=ol.getOrderPrice()%>">
		<input type="hidden" name="pp" value="<%=ol.getProductPrice()%>">
		<input type="hidden" name="pn" value="<%=ol.getProductName()%>">
		<input type="hidden" name="tq" value="<%=ol.getToppingQuantity()%>">
		<input type="hidden" name="cn" value="<%=ol.getCategoryName()%>">
		<input type="hidden" name="tid" value="<%=ol.getToppingId()%>">


		<table>
			<tr>
				<th><%=ol.getOrderId()%></th>
				<th><%=ol.getProductName()%></th>
				<th><%=ol.getProductPrice()%></th>
			</tr>
			<tr>
				<td>コーン</td>
				<td><button type="submit" name="Button" value="minus"
						style="width: 40px; height: 40px;" <%=ol.getToppingQuantity()%>>
						－</button><%=ol.getToppingQuantity()%>
					<button type="submit" name="Button" value="plus"
						style="width: 40px; height: 40px;" <%=ol.getToppingQuantity()%>>＋</td>
			</tr>
			<br>
			<tr>
				<td>カレー</td>
				<td>
					<!-- マイナス -->
					<button type="submit" name="Button" value="minus"
						style="width: 40px; height: 40px;" <%=ol.getToppingQuantity()%>>
						－</button><%=ol.getToppingQuantity()%><!-- プラス -->
					<button type="submit" name="Button" value="plus"
						style="width: 40px; height: 40px;" <%=ol.getToppingQuantity()%>>
						＋</button>
				</td>
			</tr>
			<br>
			<tr>
				<td>チーズ</td>
				<td><button type="submit" name="Button" value="minus"
						style="width: 40px; height: 40px;" <%=ol.getToppingQuantity()%>>
						－</button><%=ol.getToppingQuantity()%><!-- プラス -->
					<button type="submit" name="Button" value="plus"
						style="width: 40px; height: 40px;" <%=ol.getToppingQuantity()%>>
						＋</button></td>
			</tr>
			<br>
			<tr>
				<td>もち</td>
				<td><button type="submit" name="Button" value="minus"
						style="width: 40px; height: 40px;" <%=ol.getToppingQuantity()%>>
						－</button><%=ol.getToppingQuantity()%><!-- プラス -->
					<button type="submit" name="Button" value="plus"
						style="width: 40px; height: 40px;" <%=ol.getToppingQuantity()%>>
						＋</button></td>
			</tr>
			<br>
			<tr>
				<td>ツナ</td>
				<td><button type="submit" name="Button" value="minus"
						style="width: 40px; height: 40px;" <%=ol.getToppingQuantity()%>>
						－</button><%=ol.getToppingQuantity()%><!-- プラス -->
					<button type="submit" name="Button" value="plus"
						style="width: 40px; height: 40px;" <%=ol.getToppingQuantity()%>>
						＋</button></td>
			</tr>
			<br>
			<tr>
				<td>ベビースター</td>
				<td><button type="submit" name="Button" value="minus"
						style="width: 40px; height: 40px;" <%=ol.getToppingQuantity()%>>
						－</button><%=ol.getToppingQuantity()%><!-- プラス -->
					<button type="submit" name="Button" value="plus"
						style="width: 40px; height: 40px;" <%=ol.getToppingQuantity()%>>
						＋</button></td>
			</tr>
			<br>

		</table>


	</form><%=ol.getSubTotal()%>


	<!-- 下固定 -->
	<div
		style="position: fixed; bottom: 0; left: 0; width: 100%; background: #fff; border-top: 2px solid #333; padding: 10px 0;">

		<table width="100%" style="table-layout: fixed;">

			<tr>

				<!-- メニュー -->
				<td align="center">

					<form action="ShowMenuServlet" method="post">

						<input type="submit" value="メニュー"
							style="width: 90%; height: 50px;">

					</form>

				</td>

				<!-- 卓番号 -->
				<td align="center"><strong style="font-size: 1.5em;">

						<%=tableNum%>卓

				</strong></td>

				<!-- 変更 -->
				<td align="center">

					<form action="OrderListServlet" method="get">

						<input type="submit" name="Button" value="変更"
							style="width: 90%; height: 50px; background: orange; color: white; border: none; font-weight: bold;">

					</form>

				</td>

			</tr>

		</table>

	</div>

</body>
</html>