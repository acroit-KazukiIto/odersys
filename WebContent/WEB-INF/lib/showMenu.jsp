<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, model.ProductInfo" %>
<%

    String tableNum = (String) session.getAttribute("tableNumber");
    if(tableNum == null) {
        tableNum = "-";
    }

    List<ProductInfo> productList = (List<ProductInfo>) session.getAttribute("productList");
    String currentCategory = (String) request.getAttribute("currentCategory");
    
    // 注文リストの件数
    Integer items = (Integer) session.getAttribute("items");
    if(items == null) {
        items = 0;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>メニュー選択</title>
</head>
<body>

    <form action="ShowMenuServlet" method="post">
        <table border="1">
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

    <hr>

    <table width="100%">
        <% 
        if(productList != null) {
            for(ProductInfo p : productList) { 
                if(p.getCategoryName().trim().equals(currentCategory)) {
        %>
        <tr>
            <td>
                <b><%= p.getProductName() %></b><br>
                <%= p.getProductPrice() %>円
            </td>
            <td align="right">
                <% if(p.getProductStock() > 0) { %>
                    <form action="ItemDetailServlet" method="post">
                        <%-- 商品特定に必要な情報を隠しパラメータで送信 --%>
                        <input type="hidden" name="productName" value="<%= p.getProductName() %>">
                        <input type="hidden" name="productPrice" value="<%= p.getProductPrice() %>">
                        <input type="submit" value="＋">
                    </form>
                <% } else { %>
                    売切
                <% } %>
            </td>
        </tr>
        <%-- 商品ごとの区切り線 --%>
        <tr><td colspan="2"><hr></td></tr>
        <% 
                }
            }
        } else {
        %>
        <tr><td colspan="2" align="center">商品データがありません。</td></tr>
        <% } %>
    </table>

    <br><br>

    <table border="1" width="100%">
        <tr>
            <td align="center">
                <form action="OrderHistoryServlet" method="get">
                    <input type="submit" value="履歴・お会計">
                </form>
            </td>

            <td align="center">
                <%= tableNum %>卓
            </td>

            <td align="center">
                <form action="OrderListServlet" method="get">
                    <input type="submit" value="注文リスト(<%= items %>)">
                </form>
            </td>
        </tr>
    </table>

</body>
</html>