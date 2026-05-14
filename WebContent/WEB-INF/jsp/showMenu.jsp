<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, model.ProductInfo, model.TableInfo" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>メニュー画面</title>
</head>
<body>

    <div id="categoryHeader">
        <table border="0">
            <tr>
                <td><button onclick="location.href='ShowMenuServlet?category=okonomiyaki'">お好み焼き</button></td>
                <td><button onclick="location.href='ShowMenuServlet?category=monjayaki'">もんじゃ焼き</button></td>
                <td><button onclick="location.href='ShowMenuServlet?category=teppanyaki'">鉄板焼き</button></td>
                <td><button onclick="location.href='ShowMenuServlet?category=sideMenu'">サイドメニュー</button></td>
                <td><button onclick="location.href='ShowMenuServlet?category=softDrink'">ソフトドリンク</button></td>
                <td><button onclick="location.href='ShowMenuServlet?category=alcohol'">お酒</button></td>
                <td><button onclick="location.href='ShowMenuServlet?category=bottle'">ボトル</button></td>
            </tr>
        </table>
    </div>

    <hr>

    <div id="productList">
        <table border="1" width="100%">
            <%
                List<ProductInfo> productList = (List<ProductInfo>) request.getAttribute("productList");
                if (productList != null && !productList.isEmpty()) {
                    for (ProductInfo product : productList) {
            %>
                <tr>
                    <td width="60%">
                        <strong><%= product.getProductName() %></strong>
                    </td>
                    <td width="20%">
                        <%= product.getProductPrice() %>円
                    </td>
                    <td width="20%" align="center">
                        <form action="ItemDetailServlet" method="post" style="margin:0;">
                            <input type="hidden" name="productName" value="<%= product.getProductName() %>">
                            <input type="hidden" name="productPrice" value="<%= product.getProductPrice() %>">
                            <button type="submit" name="productButton">+</button>
                        </form>
                    </td>
                </tr>
            <%
                    }
                } else {
            %>
                <tr>
                    <td colspan="3" align="center">
                        商品データが読み込めませんでした。<br>
                        (DB内のcategory_nameが英単語と一致しているか確認してください)
                    </td>
                </tr>
            <%
                }
            %>
        </table>
    </div>

    <hr>

    <div id="footer">
        <table border="0" width="100%">
            <tr>
                <td width="33%" align="left">
                    <button onclick="location.href='OrderHistoryServlet'">履歴・お会計</button>
                </td>
                
                <td width="34%" align="center">
                    <%
                        TableInfo tableInfo = (TableInfo) request.getAttribute("tableInfo");
                        int tId = (tableInfo != null) ? tableInfo.getTableId() : 1; 
                    %>
                    <strong><%= tId %>卓</strong>
                </td>
                
                <td width="33%" align="right">
                    <%
                        // セッション等から注文件数を取得（例：cartSize）
                        Integer cartSize = (Integer) session.getAttribute("cartSize");
                    %>
                    <button onclick="location.href='OrderListServlet'">
                        注文リスト 
                        <% if (cartSize != null && cartSize > 0) { %>
                            (items: <%= cartSize %>)
                        <% } %>
                    </button>
                </td>
            </tr>
        </table>
    </div>

</body>
</html>