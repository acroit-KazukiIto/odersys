<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="model.ProductInfo"%>

<%
    List<ProductInfo> productList = (List<ProductInfo>)session.getAttribute("productList");
    String currentCategory = (String)request.getAttribute("currentCategory");
    Object tableObj = session.getAttribute("tableNumber");
    String tableNum = (tableObj != null) ? tableObj.toString() : "-";
    Integer items = (Integer)session.getAttribute("items");
    if(items == null){ items = 0; }
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>メニュー表示</title>

<link rel="stylesheet" href="./css/style.css">

<style>
body{
    margin:0;
    padding-bottom:90px;
    font-family: sans-serif;
}

/* ===== カテゴリ：横スクロール ===== */
.category-wrap{
    overflow-x:auto;
    -webkit-overflow-scrolling:touch;
}

.category-table{
    white-space:nowrap;
}


/* ===== 商品ボタンだけ調整 ===== */
.btn-add{
    padding:10px 18px;
    font-size:18px;
    border-radius:8px;
    border:1px solid #333;
    background:#fff;
}

/* ===== フッター固定 ===== */
footer{
    position:fixed;
    bottom:0;
    left:0;
    width:100%;
    background:#fff;
    border-top:1px solid #ccc;
}

.footer-table{
    width:100%;
    text-align:center;
    border-collapse:collapse;
}

.footer-table td{
    padding:8px;
}

.footer-table input{
    width:100%;
    padding:10px;
    font-size:14px;
}
</style>
</head>

<body>

<!-- ================= カテゴリ ================= -->
<nav class="category-wrap">
<form action="ShowMenuServlet" method="post">
<table border="1" width="100%" style="text-align:center;">
<tr>
<td><input type="submit" name="category" value="お好み焼き"></td>
<td><input type="submit" name="category" value="もんじゃ焼き"></td>
<td><input type="submit" name="category" value="鉄板焼き"></td>
<td><input type="submit" name="category" value="サイドメニュー"></td>
<td><input type="submit" name="category" value="ソフトドリンク"></td>
<td><input type="submit" name="category" value="お酒"></td>
<td><input type="submit" name="category" value="ボトル"></td>
</tr>
</table>
</form>
</nav>

<hr>

<!-- ================= 商品一覧 ================= -->
<div style="padding:10px;">
<table width="100%" cellpadding="5">
<%
if(productList != null){
    for(ProductInfo p : productList){
        if(p.getCategoryName().trim().equals(currentCategory)
                && p.getProductDisplayFlag() == 1){
%>
<tr>
<td>
<strong><%= p.getProductName() %></strong><br>
<%= p.getProductPrice() %>円
</td>

<td align="right">
<% if(p.getProductStock() > 0){ %>
<form action="/odersys/ItemDetailsServlet" method="get">
<input type="hidden" name="productId" value="<%= p.getProductId() %>">
<input type="hidden" name="productName" value="<%= p.getProductName() %>">
<input type="hidden" name="productPrice" value="<%= p.getProductPrice() %>">
<input type="hidden" name="productCategory" value="<%= p.getCategoryName() %>">
<input class="btn-add" type="submit" value="＋">
</form>
<% } else { %>
<span style="color:black;">売切</span>
<% } %>
</td>
</tr>
<tr><td colspan="2"><hr></td></tr>
<%
        }
    }
}
%>

</table>
</div>
<!-- ================= フッター ================= -->

<footer>
<table width="100%" border="1" style="height:80px; text-align:center;">
<tr>

<!-- 履歴・お会計 -->
<td width="33%">
<form action="OrderHistoryServlet" method="get">

<input type="hidden"
       name="tableId"
       value="<%= tableNum %>">

<input type="submit"
       value="￥ 履歴・お会計"
       class="btn-order-history"
       style="width:100%; height:80px;">

</form>
</td>

<!-- 卓番号 -->
<td width="34%">
<div class="table-num">
    <%= tableNum %>卓
</div>
</td>

<!-- 注文リスト -->
<td width="33%">
<form action="OrderListServlet" method="get">

<% if(items > 0){ %>

<input type="submit"
       value="注文リスト🛒<%= items %>"
       class="btn-order-list"
       style="width:100%; height:80px;">

<% } else { %>

<input type="submit"
       value="注文リスト"
       class="btn-order-list"
       style="width:100%; height:80px;">

<% } %>

</form>
</td>

</tr>
</table>
</footer>
</body>
</html>