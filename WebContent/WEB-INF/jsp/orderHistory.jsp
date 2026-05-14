<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="sql" uri="jakarta.tags.sql" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>注文履歴</title>
</head>
<body style="background-color: #fgf5e6; margin: 0; font-family: sans-serif;">

  
  <div style="padding: 10px;">
    <c:choose>
      <c:when test="${empty orderList}">
        <div style="background-color: #ffd700; padding: 50px; text-align: center; margin: 20px;">
        注文履歴がありません
        </div>
      </c:when>
      <c:otherwise>
        <table border="1" style="width: 100%; border-collapse: collapse; background-color: white;">
          <tr style="background-color: #eee;">
            <th>商品名</th>
            <th>数量</th>
            <th>金額(税込)</th>
          </tr>
          <c:forEach var="item" item="${orderList}">
            <tr>
              <td>
                <b>${item.productName}</b>
                <small>・${item.toppingName}</small>
              </td>
              <td align="center">${item.orderQuantity}</td>
              <td align="right">${item.subTotal}円</td>
            </tr>
          </c:forEach>
        </table>
        
        <!-- 合計表示 -->
        <div style="text-align: right; margin-top: 10px;">
          <b><u>${totalOrderQuantity}点</u></b><br>
          <b style="font-size: 1.2em;">${totalOrderPrice}円(税込)</b>
        </div>
      </c:otherwise>
    </c:choose>
  </div>

  <!-- ポップアップの仮表示 -->
  <!-- 未提供の場合 -->
  <c:if test="${popupStatus == 1}">
    <div style="border: 2px solid black; background: white; position: absolute; top: 30%; left: 10%; right: 10%; padding: 20px; text-align: center;">
      未提供の注文があります。<br><br>
      <from action="OrderHistoryServlet" method="post">
        <c:forEach var="item" items="${orderList}">
          <input type="hidden" name="productName" value="${item.productName}">
          <input type="hidden" name="toppingName" value="${item.toppingName}">
          <input type="hidden" name="subTotal" value="${item.subTotal}">
          <input type="hidden" name="orderQuantity" value="${item.orderQuantity}">
        </c:forEach>
        <input type="hidden" name="tableNumber" value="${item.tableNumber}">
        <button type="submit" name="action" value="close">閉じる</button>
      </from>
    </div>
  </c:if>

  <!-- 提供済の場合 -->
  <c:if test="${popupStatus == 1}">
    <div style="border: 2px solid black; background: white; position: absolute; top: 30%; left: 10%; right: 10%; padding: 20px; text-align: center;">
      お会計に進みます。<br>よろしいですか？<br><br>
      <form action="OrderHistoryServlet" method="post">
        <c:forEach var="item" items="${orderList}">
          <input type="hidden" name="productName" value="${item.productName}">
          <input type="hidden" name="toppingName" value="${item.toppingName}">
          <input type="hidden" name="subTotal" value="${item.subTotal}">
          <input type="hidden" name="orderQuantity" value="${item.orderQuantity}">
        </c:forEach>
        <input type="hidden" name="tableNumber" value="${tableNumber}">
        <input type="hidden" name="totalOrderPrice" value="${totalOrderPrice}">
                
        <button type="submit" name="action" value="no" style="display: block; width: 100%; margin-bottom: 5px;">いいえ</button>
        <button type="submit" name="action" value="yes" style="display: block; width: 100%; background: red; color: white;">はい</button>
      </form>
    </div>
  </c:if>

  <!-- フッターボタン -->
  <div style="position: fixed; bottom: 0; width: 100%; height: 60px; display: table; border-top: 1px solid #ccc;">
    <div style="display: table-row;">
    
      <!-- メニューボタン -->
      <div style="display: table-cell; width: 33%; background: #90ee90; text-align: center; vertical-align: middle;">
        <form action="ShowMenuServlet" method="post">
          <button type="submit" style="background:none; border:none; font-size: 16px;">
            ←<br><small>メニュー</small>
          </button>
        </form>
      </div>
    
      <!-- 卓番号 -->
      <div style="display: table-cell; width: 34%; background: white; text-align: center; vertical-align: middle; font-size: 20px; font-weight: bold;">
        ${tableNumber}卓
      </div>
    
      <!-- お会計ボタン -->
      <div style="display: table-cell; width: 33%; background: #ffa07a; text-align: center; vertical-align: middle;">
        <c:if test="${not empty orderList}">
          <from action="OrderHistoryServlet" method="post">
            <c:forEach var="item" items="${orderList}">
              <input type="hidden" name="productName" value="${item.productName}">
              <input type="hidden" name="toppingName" value="${item.toppingName}">
              <input type="hidden" name="subTotal" value="${item.subTotal}">
              <input type="hidden" name="orderQuantity" value="${item.orderQuantity}">
            </c:forEach>
            <input type="hidden" name="tableNumber" value="${tableNumber}">
            <button type="submit" name="checkOut" style="background:none; border:none; font-size: 16px;">
              ￥<br><small>お会計</small>
            </button>
          </from>
        </c:if>
      </div>
      
    </div>
  </div>
</body>
</html>