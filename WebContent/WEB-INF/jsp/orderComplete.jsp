<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.TableInfo" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>注文完了画面</title>
</head>
<body>

	<div= "main">
		<h3>ご注文いただきありがとうございます。</h3>
	</div>

	<h3>
		お料理を準備いたしますので<br>しばらくお待ちください
	</h3>
</body>
<footer>
	<form action="ShowMenuServlet" method="get">
		<div="left">
			<button type="submit" name="Button" value="メニュー">メニュー</button>
		</div>
		<div="center">卓</div>
	</form>
</footer>
</html>