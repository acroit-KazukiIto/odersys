<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>注文履歴</title>
</head>
<body bgcolor="#FDF5E6" style="margin: 0; padding: 0; font-family: sans-serif;">
  <div style="width: 100%; min-height: 100vh; display: flex; flex-direction: column;">
    <div style="flex-grow: 1; overflow-y: auto; padding-bottom: 100px; width: 100%;">
      <c:choose>
      
        <!-- 注文履歴なし -->
        <c:when test="${empty orderList}">
          <div style="display: flex; justify-content: center; align-items: center; padding: 50px 0;">
            <div style="background-color: #FFD700; width: 90%; padding: 80px 20px; border-radius: 15px; text-align: center;">
              <font size="6"><b>注文履歴がありません</b></font>
            </div>
          </div>
        </c:when>
        
        <!-- 注文履歴あり -->
        <c:otherwise>
          <table width="100%" border="1" cellpadding="20" cellspacing="0" style="border-collapse: collapse; background-color: rgba(255,255,255,0.2);">
            <tr bgcolor="#F5DEB3" style="border-bottom: 3px solid black;">
              <th align="center" style="font-size: 1.2em;">商品名</th>
              <th align="center" style="font-size: 1.2em;">数量</th>
              <th align="center" style="font-size: 1.2em;">金額(税込)</th>
            </tr>
            <c:forEach var="item" items="${orderList}">
              <tr style="border-bottom: 1px solid #ccc;">
                <td align="left">
                  <font size="4"><b>${item.productName}</b></font><br>
                  <c:if test="${not empty item.toppingName}">
                    <font size="3" color="#555">・${item.toppingName}✕${item.toppingQuantity}</font>
                  </c:if>
                </td>
                <td align="center"><font size="4">${item.orderQuantity}</font></td>
                <td align="right"><font size="4">${item.subTotal}円</font></td>
              </tr>
            </c:forEach>
          </table>

          <!-- 合計表示 -->
          <div style="text-align: right; padding: 30px; border-top: 2px solid black;">
            <font size="5"><u><b>${totalOrderQuantity}点</b></u></font><br>
            <font size="7"><b>${totalOrderPrice}円(税込)</b></font>
          </div>
        </c:otherwise>
      </c:choose>
    </div>

    <!-- フッター -->
    <div style="position: fixed; bottom: 0; width: 100%; height: 100px; background-color: white; border-top: 1px solid #ccc; display: flex; z-index: 50;">
            
      <!-- メニューボタン -->
      <div style="width: 33.3%; background: #A0FFA0; display: flex; align-items: center; justify-content: center; flex-shrink: 0;">
        <form action="ShowMenuServlet" method="post" style="margin:0; width:100%; height:100%;">
          <button type="submit"
            style="background:none; border:none; width:100%; height:100%; cursor:pointer; display: flex; flex-direction: column; align-items: center; justify-content: center;">
              <font size="7">↩</font>
              <font size="3"><b>メニュー</b></font>
          </button>
        </form>
      </div>

      <!-- 卓番号 -->
      <div style="width: 33.4%; background: white; display: flex; align-items: center; justify-content: center; border-left: 1px solid #ccc;">
        <font size="7"><b>${tableNumber}卓</b></font>
      </div>
      
      <!-- お会計ボタン -->
      <div style="width: 33.3%; display: flex; border-left: 1px solid #ccc;">
        <c:choose>
        
          <!-- 履歴がある場合、お会計ボタンを表示 -->
          <c:when test="${not empty orderList}">
            <div style="width: 100%; height: 100%; background: #FF8C60; display: flex; align-items: center; justify-content: center;">
              <form action="OrderHistoryServlet" method="post" style="margin:0; width:100%; height:100%;">
                <c:forEach var="item" items="${orderList}">
                  <input type="hidden" name="productName" value="${item.productName}">
                  <input type="hidden" name="toppingName" value="${item.toppingName}">
                  <input type="hidden" name="toppingQuantity" value="${item.toppingQuantity}">
                  <input type="hidden" name="subTotal" value="${item.subTotal}">
                  <input type="hidden" name="orderQuantity" value="${item.orderQuantity}">
                </c:forEach>
                <input type="hidden" name="tableNumber" value="${tableNumber}">
                <input type="hidden" name="totalOrderPrice" value="${totalOrderPrice}">
                <button type="submit" name="action" value="checkOut"
                  style="background:none; border:none; width:100%; height:100%; cursor:pointer; display: flex; flex-direction: column; align-items: center; justify-content: center;">
                    <div style="border: 3px solid white; border-radius: 50%; width: 40px; height: 40px; display: flex; align-items: center; justify-content: center; margin-bottom: 5px;">
                      <font color="white" size="5"><b>￥</b></font>
                    </div>
                    <font size="3" color="black"><b>お会計</b></font>
                </button>
              </form>
            </div>
          </c:when>
          
          <!-- 履歴がない場合、空の領域（場所だけ確保） -->
          <c:otherwise>
            <div style="width: 100%; height: 100%; background: white;"></div>
          </c:otherwise>
        </c:choose>
      </div>

    </div>
  </div>

  <!-- ポップアップ -->
  <c:if test="${popupStatus == 1 || popupStatus == 2}">
    <div style="position: fixed; top: 0; left: 0; width: 100%; height: 100%; background: rgba(0,0,0,0.6); z-index: 100; display: flex; justify-content: center; align-items: center;">
      <div style="background: white; width: 50%; min-width: 300px; padding: 60px 20px; border-radius: 10px; text-align: center; box-shadow: 0 0 30px rgba(0,0,0,0.5);">
        <c:choose>
        
          <!-- 未提供ポップアップ -->
          <c:when test="${popupStatus == 1}">
            <font size="5"><b>未提供の注文があります。</b></font><br><br><br>
            <!-- 閉じるボタン -->
            <form action="OrderHistoryServlet" method="post">
              <button type="submit" name="action" value="close"
                style="width: 200px; padding: 15px; background: white; border: 2px solid black; font-size: 1.2em; font-weight: bold; cursor: pointer;">
                  閉じる
              </button>
            </form>
          </c:when>
          
          <!-- お会計確認(提供済)ポップアップ -->
          <c:when test="${popupStatus == 2}">
            <font size="5"><b>お会計に進みます。<br>よろしいですか？</b></font><br><br><br>
            <form action="OrderHistoryServlet" method="post">
              <input type="hidden" name="tableNumber" value="${tableNumber}">
              <input type="hidden" name="totalOrderPrice" value="${totalOrderPrice}">
              <!-- いいえボタン -->
              <button type="submit" name="action" value="no"
                style="width: 200px; padding: 15px; background: white; border: 2px solid black; font-size: 1.2em; font-weight: bold; cursor: pointer; margin-bottom: 20px;">
                  いいえ
              </button><br>
              <!-- はいボタン -->
              <button type="submit" name="action" value="yes"
                style="width: 200px; padding: 15px; background: red; color: white; border: 2px solid black; font-size: 1.2em; font-weight: bold; cursor: pointer;">
                  はい
              </button>
            </form>
          </c:when>
          
        </c:choose>
      </div>
    </div>
  </c:if>
</body>
</html>