<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, model.ItemDetailsInfo" %>
<%
    // セッションから値を取得
    String pName = (String) session.getAttribute("selectedPName");
    Integer pPrice = (Integer) session.getAttribute("selectedPPrice");
    List<ItemDetailsInfo> toppingList = (List<ItemDetailsInfo>) session.getAttribute("toppingList");
    Integer subTotal = (Integer) session.getAttribute("subTotal");
    String tableNum = (String) session.getAttribute("tableNumber");
    // menuから送られてきたカテゴリ名を取得して保存するa
    String category = request.getParameter("productCategory");
    if (category != null && !category.isEmpty()) {
        session.setAttribute("savedCategory", category.trim());
    }

    String savedCategory = (String) session.getAttribute("savedCategory");
    if (savedCategory == null) {
        savedCategory = "";
    }

    // お好み焼き・もんじゃ焼き・鉄板焼き・鉄板焼の時だけトッピングを表示
    boolean showTopping = savedCategory.equals("お好み焼き") ||
                          savedCategory.equals("もんじゃ焼き") ||
                          savedCategory.equals("鉄板焼") ||
                          savedCategory.equals("鉄板焼き");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>商品詳細</title>
</head>
<body style="margin: 0; padding-bottom: 120px; font-family: sans-serif;">

    <%-- Body 上部 --%>
    <table width="100%" border="0" style="padding: 10px;">
        <tr>
            <td align="left"><strong style="font-size: 1.2em;"><%= pName %></strong></td>
            <td align="right"><%= (pPrice != null ? pPrice : 0) %>円(税込)</td>
        </tr>
    </table>
    <hr>
    <% if(showTopping) { %>
    <div style="padding: 10px;">
        <table width="100%" border="0" cellpadding="10" style="table-layout: fixed;">
            <% if(toppingList != null) { 
                for(int i=0; i < toppingList.size(); i++) { 
                    ItemDetailsInfo t = toppingList.get(i); %>
            <tr>
                <td align="left" width="60%"><%= t.getToppingName() %><br><small><%= t.getToppingPrice() %>円</small></td>
                <td align="right" width="40%" style="white-space: nowrap;">
                    <form action="ItemDetailsServlet" method="post" style="display:inline;">
                        <button type="submit" name="Button" value="-<%= i %>" 
                                style="width:40px; height:40px;" 
                                <%= (t.getToppingQuantity() <= 0) ? "disabled" : "" %>>－</button>
                        
                        <span style="display: inline-block; width: 25px; text-align: center; font-weight: bold;">
                            <%= t.getToppingQuantity() %>
                        </span>
                        
                        <button type="submit" name="Button" value="+<%= i %>" 
                                style="width:40px; height:40px;" 
                                <%= (t.getToppingQuantity() >= 20) ? "disabled" : "" %>>＋</button>
                    </form>
                </td>
            </tr>
            <% } } %>
        </table>
    </div>
    <% } %>

    <%-- Body 下部: 右揃え小計 --%>
    <div align="right" style="padding: 20px; border-top: 1px solid #ccc; margin-bottom: 50px;">
        <span style="text-decoration: underline; font-weight: bold; font-size: 1.3em;">
            小計:<%= subTotal %>円(税込)
        </span>
    </div>

    <%-- Footer: 画面最下部に白背景で固定 --%>
    <div style="position: fixed; bottom: 0; left: 0; width: 100%; background: #ffffff; border-top: 2px solid #333; z-index: 1000; padding: 10px 0;">
        <table width="100%" border="0" style="height: 60px; table-layout: fixed; text-align: center;">
            <tr>
                <td>
                    <form action="ShowMenuServlet" method="post" style="margin:0;">
                        <input type="submit" name="Button" value="メニュー" style="width: 90%; height: 50px;">
                    </form>
                </td>
                <td>
                    <strong style="font-size: 1.5em;"><%= tableNum %>卓</strong>
                </td>
                <td>
                    <form action="OrderListServlet" method="post" style="margin:0;">
                        <input type="submit" name="Button" value="追加" 
                               style="width: 90%; height: 50px; background: orange; color: white; border: none; font-weight: bold;">
                    </form>
                </td>
            </tr>
        </table>
    </div>

</body>
</html>