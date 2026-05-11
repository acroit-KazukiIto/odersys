<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.TableInfo" %>
<%
TableInfo t = (TableInfo)request.getAttribute("t");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>注文開始</title>
</head>
<body>
<h1><%= t %>卓</h1>
</body>
</html>