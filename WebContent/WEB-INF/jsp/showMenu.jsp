<%@ page language="java"
contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<%@ page import="java.util.List"%>
<%@ page import="model.ProductInfo"%>

<%
    List<ProductInfo> productList =
        (List<ProductInfo>)session.getAttribute("productList");

    String currentCategory =
        (String)request.getAttribute("currentCategory");

    Object tableObj =
        session.getAttribute("tableNumber");

    String tableNum =
        (tableObj != null)
        ? tableObj.toString()
        : "-";

    Integer items =
        (Integer)session.getAttribute("items");

    if(items == null){
        items = 0;
    }
%>

<!DOCTYPE html>

<html>

<head>

<meta charset="UTF-8">

<title>メニュー表示</title>

</head>

<body style="margin:0;
             padding-bottom:80px;">

<!-- カテゴリボタン -->

<nav>

<form action="ShowMenuServlet"
      method="post">

<table border="1"
       width="100%"
       style="text-align:center;">

<tr>

<td>
<input type="submit"
       name="category"
       value="お好み焼き">
</td>

<td>
<input type="submit"
       name="category"
       value="もんじゃ焼き">
</td>

<td>
<input type="submit"
       name="category"
       value="鉄板焼き">
</td>

<td>
<input type="submit"
       name="category"
       value="サイドメニュー">
</td>

<td>
<input type="submit"
       name="category"
       value="ソフトドリンク">
</td>

<td>
<input type="submit"
       name="category"
       value="お酒">
</td>

<td>
<input type="submit"
       name="category"
       value="ボトル">
</td>

</tr>

</table>

</form>

</nav>

<hr>

<!-- 商品一覧 -->

<div style="padding:10px;">

<table width="100%"
       cellpadding="5">

<%
if(productList != null){

    for(ProductInfo p : productList){

        /*
         * カテゴリ一致
         * 表示フラグ1
         * の商品だけ表示
         */
        if(p.getCategoryName().trim().equals(currentCategory)
                && p.getProductDisplayFlag() == 1){
%>

<tr>

<td>

<strong>

<%= p.getProductName() %>

</strong>

<br>

<%= p.getProductPrice() %>円

</td>

<td align="right">

<%
if(p.getProductStock() > 0){
%>

<form action="ItemDetailsServlet"
      method="get">

<!-- 商品名 -->
<input type="hidden"
       name="productName"
       value="<%= p.getProductName() %>">

<!-- 商品価格 -->
<input type="hidden"
       name="productPrice"
       value="<%= p.getProductPrice() %>">

<!-- カテゴリ -->
<input type="hidden"
       name="productCategory"
       value="<%= p.getCategoryName() %>">

<input type="submit"
       value=" ＋ ">

</form>

<%
}else{
%>

<span style="color:red;">

売切

</span>

<%
}
%>

</td>

</tr>

<tr>

<td colspan="2">

<hr>

</td>

</tr>

<%
        }
    }

}else{
%>

<tr>

<td colspan="2"
    align="center">

商品が登録されていません

</td>

</tr>

<%
}
%>

</table>

</div>

<!-- 下固定メニュー -->

<footer style="
position:fixed;
bottom:0;
left:0;
width:100%;
background-color:white;
border-top:1px solid #ccc;">

<table width="100%"
       border="1"
       style="
       height:60px;
       text-align:center;">

<tr>

<!-- 履歴 -->

<td width="33%">

<form action="OrderHistoryServlet"
      method="get">

<input type="hidden"
       name="tableId"
       value="<%= tableNum %>">

<input type="submit"
       value="履歴・お会計">

</form>

</td>

<!-- 卓番号 -->

<td width="34%">

<strong>

<%= tableNum %>卓

</strong>

</td>

<!-- 注文リスト -->

<td width="33%">

<form action="OrderListServlet"
      method="get">

<input type="submit"
       value="注文リスト(<%= items %>)">

</form>

</td>

</tr>

</table>

</footer>

</body>

</html>