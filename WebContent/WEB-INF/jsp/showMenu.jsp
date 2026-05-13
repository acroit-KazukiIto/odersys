<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.ProductInfo" %>
<%
    //  tablenumverからもらう物
    String tableNumber = request.getParameter("tableNumber");
    if (tableNumber == null || tableNumber.isEmpty()) {
        tableNumber = "0";
    }

    // サーブレットの商品リストをもらう
    List<ProductInfo> productList = (List<ProductInfo>) request.getAttribute("productList");
    
    // カートの数
    Integer items = (Integer) session.getAttribute("items");
    if (items == null) items = 0;
%>

<div>
    <button type="button">お好み焼き</button>
    <button type="button">もんじゃ焼き</button>
    <button type="button">鉄板焼き</button>
    </div>

<hr>

<% if (productList != null) { %>
    <% for (ProductInfo p : productList) { %>
        <div>
            <%= p.getProductName() %> / <%= p.getProductPrice() %>円
            <form action="ItemDetailServlet" method="get" style="display:inline;">
                <input type="hidden" name="productId" value="<%= p.getProductId() %>">
                <input type="hidden" name="tableNumber" value="<%= tableNumber %>">
                <button type="submit">＋</button>
            </form>
        </div>
        <br>
    <% } %>
<% } %>

<hr>

<table width="100%" border="1">
    <tr>
        <td align="center">
            <a href="OrderHistoryServlet?tableNumber=<%= tableNumber %>" style="display:block; text-decoration:none; color:black; padding:10px;">
                履歴・お会計
            </a>
        </td>
        
        <td align="center">
            <%= tableNumber %> 卓
        </td>
        
        <td align="center">
            <a href="OrderListServlet?tableNumber=<%= tableNumber %>" style="display:block; text-decoration:none; color:black; padding:10px;">
                注文リスト 
                <% if(items > 0) { %> (<%= items %>) <% } %>
            </a>
        </td>
    </tr>
</table>