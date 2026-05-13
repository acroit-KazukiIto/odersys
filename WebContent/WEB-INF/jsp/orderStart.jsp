<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // Servletから送られてきたデータを取得
    Integer tableNumber = (Integer) request.getAttribute("tableNumber");
    Integer guestCount = (Integer) request.getAttribute("guestCount");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>注文開始</title>
</head>
<body>
    <div style="text-align: center;">
        <h1><%= tableNumber %>卓</h1>
        <h3>いらっしゃいませ！</h3>
        <h3>人数を設定してください</h3>
        
        <form action="OrderStartServlet" method="post">
            <input type="hidden" name="tableId" value="<%= tableNumber %>">
            <input type="hidden" name="guestCount" value="<%= guestCount %>">

            <div>
                <button type="submit" name="action" value="minus">－</button>
                <span style="font-size: 2em; margin: 0 20px;"><%= guestCount %></span>
                <button type="submit" name="action" value="plus">＋</button>
            </div>
            <br><br>
            <div>
                <button type="submit" name="action" value="start" style="padding: 10px 20px;">注文開始</button>
            </div>
        </form>
    </div>
</body>
</html>