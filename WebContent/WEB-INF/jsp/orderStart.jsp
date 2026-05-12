<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.TableInfo" %>
<%
TableInfo tableInfo = (TableInfo)request.getAttribute("tableInfo");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>注文開始</title>
</head>
<body>
<h1>
<div style="text-align: center;">
<%= tableInfo.getTableId() %>卓
</div>
</h1>
<p>
<div style="text-align: center;">
いらっしゃいませ！
</div><br>
<div style="text-align: center;">
人数を設定してください
</div><br>
<div style="text-align: center;">
<div class="spinner-container">
<span class="spinner-sub disabled">－</span>
<input class="spinner" type="number" min="1" max="9" value="0">
<span class="spinner-add">＋</span>
</div>
</div><br>
</p>
</body>
</html>