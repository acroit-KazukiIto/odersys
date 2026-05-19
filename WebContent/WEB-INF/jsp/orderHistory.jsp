<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>注文履歴</title>
<style>
  /* 基本レイアウト */
  body { margin: 0; padding: 0; font-family: "Helvetica Neue", Arial, "Hiragino Kaku Gothic ProN", "Hiragino Sans", Meiryo, sans-serif; background-color: #FDF5E6; }
  .container { width: 100%; min-height: 100vh; display: flex; flex-direction: column; }
  .content { flex-grow: 1; overflow-y: auto; padding-bottom: 120px; }
  
  /* テーブルスタイル */
  .order-table { width: 100%; border-collapse: collapse; background-color: rgba(255,255,255,0.5); }
  .order-table th { background-color: #F5DEB3; padding: 15px; border-bottom: 2px solid #333; }
  .order-table td { padding: 15px; border-bottom: 1px solid #ddd; }
  
  /* 合計エリア */
  .total-area { text-align: right; padding: 30px; border-top: 2px solid #333; }
  .total-price { font-size: 2.5rem; font-weight: bold; }

  /* フッター */
  .footer { position: fixed; bottom: 0; width: 100%; height: 100px; background-color: white; border-top: 1px solid #ccc; display: flex; z-index: 50; }
  .footer-btn { flex: 1; border: none; cursor: pointer; display: flex; flex-direction: column; align-items: center; justify-content: center; text-decoration: none; color: inherit; }
  .btn-menu { background: #A0FFA0; }
  .btn-checkout { background: #FF8C60; width: 100%; height: 100%; font-size: 1rem; border: none; cursor: pointer; }
  .table-num { flex: 1; display: flex; align-items: center; justify-content: center; font-size: 2rem; font-weight: bold; border-left: 1px solid #ccc; }

  /* --- ポップアップのCSS --- */
  .modal-overlay {
    display: none; /* 初期状態は非表示 */
    position: fixed; top: 0; left: 0; width: 100%; height: 100%;
    background: rgba(0,0,0,0.7); z-index: 1000;
    justify-content: center; align-items: center;
    opacity: 0; transition: opacity 0.3s ease;
  }
  .modal-overlay.active { display: flex; opacity: 1; }
  
  .modal-content {
    background: white; width: 85%; max-width: 450px; padding: 40px 20px;
    border-radius: 15px; text-align: center; box-shadow: 0 10px 25px rgba(0,0,0,0.3);
    transform: translateY(-20px); transition: transform 0.3s ease;
  }
  .modal-overlay.active .modal-content { transform: translateY(0); }

  .modal-title { font-size: 1.4rem; font-weight: bold; margin-bottom: 30px; line-height: 1.5; }
  .btn-group { display: flex; flex-direction: column; gap: 15px; align-items: center; }
  
  .btn-base { width: 80%; padding: 15px; font-size: 1.1rem; font-weight: bold; border-radius: 8px; cursor: pointer; border: 2px solid #333; }
  .btn-close { background: white; color: #333; }
  .btn-yes { background: #ff4757; color: white; border: none; }
  .btn-no { background: #eee; color: #333; }
</style>
</head>
<body>
<div class="container">
  <div class="content">
    <c:choose>
      <c:when test="${empty orderList}">
        <div style="padding: 100px 20px; text-align: center;">
          <div style="background: #FFD700; padding: 60px; border-radius: 15px; font-size: 1.5rem; font-weight: bold;">
            注文履歴がありません
          </div>
        </div>
      </c:when>
      <c:otherwise>
        <table class="order-table">
          <thead>
            <tr>
              <th>商品名</th>
              <th>数量</th>
              <th>金額(税込)</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach var="item" items="${orderList}">
              <tr>
                <td>
                  <strong>${item.productName}</strong><br>
                  <c:if test="${not empty item.toppingName}">
                    <span style="color: #666; font-size: 0.9rem;">・${item.toppingName}✕${item.toppingQuantity}</span>
                  </c:if>
                </td>
                <td align="center">${item.orderQuantity}</td>
                <td align="right">${item.subTotal}円</td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
        <div class="total-area">
          <u>${totalOrderQuantity}点</u><br>
          <span class="total-price">${totalOrderPrice}円(税込)</span>
        </div>
      </c:otherwise>
    </c:choose>
  </div>

  <!-- フッター -->
  <div class="footer">
    <div class="footer-btn btn-menu" onclick="location.href='ShowMenuServlet'">
      <span style="font-size: 2rem;">↩</span>
      <strong>メニュー</strong>
    </div>

    <div class="table-num">${tableNumber}卓</div>

    <div style="flex: 1; border-left: 1px solid #ccc;">
      <c:if test="${not empty orderList}">
        <form action="OrderHistoryServlet" method="post" style="height:100%;">
          <input type="hidden" name="tableNumber" value="${tableNumber}">
          <input type="hidden" name="totalOrderPrice" value="${totalOrderPrice}">
          <button type="submit" name="action" value="checkOut" class="btn-checkout">
            <div style="border: 2px solid black; border-radius: 50%; width: 30px; height: 30px; margin: 0 auto 5px; display: flex; align-items: center; justify-content: center;">￥</div>
            <strong>お会計</strong>
          </button>
        </form>
      </c:if>
    </div>
  </div>
</div>

<!-- --- ポップアップ --- -->
<c:if test="${popupStatus == 1 || popupStatus == 2}">
  <div id="modalOverlay" class="modal-overlay active">
    <div class="modal-content">
      <c:choose>
        <%-- 未提供あり --%>
        <c:when test="${popupStatus == 1}">
          <div class="modal-title">未提供の注文があります。</div>
          <div class="btn-group">
            <button type="button" class="btn-base btn-close" onclick="closeModal()">閉じる</button>
          </div>
        </c:when>

        <%-- お会計確認(提供済) --%>
        <c:when test="${popupStatus == 2}">
          <div class="modal-title">お会計に進みます。<br>よろしいですか？</div>
          <form action="OrderHistoryServlet" method="post" class="btn-group">
            <input type="hidden" name="tableNumber" value="${tableNumber}">
            <input type="hidden" name="totalOrderPrice" value="${totalOrderPrice}">
            <button type="submit" name="action" value="yes" class="btn-base btn-yes">はい</button>
            <button type="button" class="btn-base btn-no" onclick="closeModal()">いいえ</button>
          </form>
        </c:when>
      </c:choose>
    </div>
  </div>
</c:if>

<script>
  // ポップアップを閉じる関数
  function closeModal() {
    const modal = document.getElementById('modalOverlay');
    if (modal) {
      modal.style.opacity = '0';
      setTimeout(() => {
        modal.classList.remove('active');
      }, 300);
    }
  }
</script>

</body>
</html>