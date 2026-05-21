<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, model.ItemDetailsInfo" %>

<%
    String productId = (String) request.getAttribute("productId");

    String pName = (String) request.getAttribute("selectedPName");

    Integer pPrice =
            (Integer) request.getAttribute("selectedPPrice");

    List<ItemDetailsInfo> toppingList =
            (List<ItemDetailsInfo>) request.getAttribute("toppingList");

    Integer subTotal =
            (Integer) request.getAttribute("subTotal");

    String category =
            (String) request.getAttribute("currentCategory");

    // 卓番号
    String tableNum =
            (String) session.getAttribute("tableNumber");

    if (tableNum == null) {
        tableNum = "ー";
    }
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>商品詳細</title>
</head>

<body style="margin:0; padding-bottom:120px; font-family:sans-serif;">

    <%-- 上部 --%>
    <table width="100%" border="0" style="padding:10px;">
        <tr>
            <td align="left">
                <strong style="font-size:1.2em;">
                    <%= pName %>
                </strong>
            </td>

            <td align="right">
                <%= (pPrice != null ? pPrice : 0) %>円(税込)
            </td>
        </tr>
    </table>

    <hr>

    <% if(toppingList != null) { %>

    <div style="padding:10px;">

        <table width="100%" border="0"
               cellpadding="10"
               style="table-layout:fixed;">

            <% for(int i = 0; i < toppingList.size(); i++) {

                ItemDetailsInfo t =
                        toppingList.get(i);
            %>

            <tr>

                <td align="left" width="60%">
                    <%= t.getToppingName() %><br>
                    <small>
                        <%= t.getToppingPrice() %>円
                    </small>
                </td>

                <td align="right"
                    width="40%"
                    style="white-space:nowrap;">

                    <form action="ItemDetailsServlet"
                          method="post"
                          style="display:inline;">

                        <%-- 商品情報 --%>
                        <input type="hidden"
                               name="productId"
                               value="<%= productId %>">

                        <input type="hidden"
                               name="productName"
                               value="<%= pName %>">

                        <input type="hidden"
                               name="productPrice"
                               value="<%= pPrice %>">

                        <input type="hidden"
                               name="productCategory"
                               value="<%= category %>">

                        <%-- 数量保持 --%>
                        <% for(int j = 0; j < toppingList.size(); j++) { %>

                        <input type="hidden"
                               name="oldQty_<%= j %>"
                               value="<%= toppingList.get(j).getToppingQuantity() %>">

                        <% } %>

                        <%-- マイナス --%>
                        <button type="submit"
                                name="Button"
                                value="-<%= i %>"
                                style="width:40px; height:40px;"
                                <%= (t.getToppingQuantity() <= 0)
                                ? "disabled"
                                : "" %>>

                            －

                        </button>

                        <%-- 数量表示 --%>
                        <span style="
                            display:inline-block;
                            width:25px;
                            text-align:center;
                            font-weight:bold;">

                            <%= t.getToppingQuantity() %>

                        </span>

                        <%-- プラス --%>
                        <button type="submit"
                                name="Button"
                                value="+<%= i %>"
                                style="width:40px; height:40px;"
                                <%= (t.getToppingQuantity() >= 20)
                                ? "disabled"
                                : "" %>>

                            ＋

                        </button>

                    </form>

                </td>

            </tr>

            <% } %>

        </table>

    </div>

    <% } %>

    <%-- 小計 --%>
    <div align="right"
         style="padding:20px;
                border-top:1px solid #ccc;
                margin-bottom:50px;">

        <span style="
            text-decoration:underline;
            font-weight:bold;
            font-size:1.3em;">

            小計:
            <%= (subTotal != null ? subTotal : 0) %>円(税込)

        </span>

    </div>

    <%-- Footer --%>
    <div style="
        position:fixed;
        bottom:0;
        left:0;
        width:100%;
        background:#ffffff;
        border-top:2px solid #333;
        z-index:1000;
        padding:10px 0;">

        <table width="100%"
               border="0"
               style="
               height:60px;
               table-layout:fixed;
               text-align:center;">

            <tr>

                <%-- メニュー --%>
                <td>

                    <form action="ShowMenuServlet"
                          method="post"
                          style="margin:0;">

                        <input type="submit"
                               name="Button"
                               value="メニュー"
                               style="width:90%; height:50px;">

                    </form>

                </td>

                <%-- 卓番号 --%>
                <td>

                    <strong style="font-size:1.5em;">
                        <%= tableNum %>卓
                    </strong>

                </td>

                <%-- 追加 --%>
                <td>

                    <form action="ItemDetailsServlet"
                          method="post"
                          style="margin:0;">

                        <input type="hidden"
                               name="productId"
                               value="<%= productId %>">

                        <input type="hidden"
                               name="productName"
                               value="<%= pName %>">

                        <input type="hidden"
                               name="productPrice"
                               value="<%= pPrice %>">

                        <input type="hidden"
                               name="productCategory"
                               value="<%= category %>">

                        <input type="submit"
                               name="Button"
                               value="追加"
                               style="
                               width:90%;
                               height:50px;
                               background:orange;
                               color:white;
                               border:none;
                               font-weight:bold;">

                    </form>

                </td>

            </tr>

        </table>

    </div>

</body>
</html>