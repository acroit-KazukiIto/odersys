<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, model.ItemDetailsInfo" %>
<%
    // セッションから表示用データを取得
    String pName = (String) session.getAttribute("selectedPName");
    Integer pPrice = (Integer) session.getAttribute("selectedPPrice");
    String tableNum = (String) session.getAttribute("tableNumber");
    List<ItemDetailsInfo> toppingList = (List<ItemDetailsInfo>) session.getAttribute("toppingList");
    Integer subTotal = (Integer) session.getAttribute("subTotal");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>商品詳細</title>
</head>
<body style="margin: 0; padding-top: 80px; font-family: sans-serif;">

    <footer style="position: fixed; top: 0; left: 0; width: 100%; background-color: white; border-bottom: 1px solid #ccc; z-index: 100;">
        <table width="100%" border="1" style="height: 60px; text-align: center; border-collapse: collapse;">
            <tr>
                <td width="33%">
                    <form action="ShowMenuServlet" method="get">
                        <input type="submit" value="メニュー" style="width: 90%; height: 40px;">
                    </form>
                </td>

                <td width="34%">
                    <strong><%= (tableNum != null) ? tableNum : "-" %>卓</strong>
                </td>

                <td width="33%">
                    <form action="OrderListServlet" method="post">
                        <input type="submit" value="追加" style="width: 90%; height: 40px; background-color: orange; color: white; border: none; font-weight: bold;">
                    </form>
                </td>
            </tr>
        </table>
    </footer>

    <div style="padding: 10px; background-color: #f9f9f9;">
        <table width="100%" cellpadding="5">
            <tr>
                <td>
                    <small>選択中の商品：</small><br>
                    <strong><%= (pName != null) ? pName : "未選択" %></strong>
                </td>
                <td align="right">
                    <%= (pPrice != null) ? pPrice : 0 %>円
                </td>
            </tr>
        </table>
    </div>
    <hr>

    <div style="padding: 10px;">
        <table width="100%" cellpadding="5">
            <% 
            if(toppingList != null && !toppingList.isEmpty()) { 
                for(int i = 0; i < toppingList.size(); i++) { 
                    ItemDetailsInfo t = toppingList.get(i); 
            %>
            <tr>
                <td>
                    <%= t.getToppingName() %><br>
                    <small>+<%= t.getToppingPrice() %>円</small>
                </td>
                <td align="right">
                    <form action="ItemDetailsServlet" method="post" style="display: inline;">
                        <button type="submit" name="action" value="minus_<%= i %>" 
                            <%= t.getToppingQuantity() <= 0 ? "disabled" : "" %>> － </button>
                        
                        <span style="margin: 0 10px;"><strong><%= t.getToppingQuantity() %></strong></span>
                        
                        <button type="submit" name="action" value="plus_<%= i %>" 
                            <%= t.getToppingQuantity() >= 20 ? "disabled" : "" %>> ＋ </button>
                    </form>
                </td>
            </tr>
            <tr><td colspan="2"><hr></td></tr>
            <% 
                } 
            } else {
            %>
            <tr><td colspan="2" align="center">トッピングデータがありません</td></tr>
            <% } %>
        </table>
    </div>

    <div align="right" style="padding: 20px; font-size: 1.2em;">
        小計：<strong><%= (subTotal != null) ? subTotal : 0 %>円</strong>
    </div>

</body>

</html>