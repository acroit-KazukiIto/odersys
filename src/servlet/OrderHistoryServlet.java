package servlet;

import java.io.IOException;
import java.util.List;

import dao.OrderHistoryDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.OrderHistoryInfo;
import model.OrderHistoryLogic;

@WebServlet("/OrderHistoryServlet")
public class OrderHistoryServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, 
			HttpServletResponse response)
					throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        
        // 卓番号とセッションIDの取得
        String tableNumber = (String) session.getAttribute("tableNumber");
        String sessionId = session.getId();
        String action = request.getParameter("action");

        try {
            // 注文履歴情報の取得 (DBから最新情報を取得)
            OrderHistoryDAO dao = new OrderHistoryDAO();
            List<OrderHistoryInfo> orderList = dao.findOrderDetails(sessionId);

            // ロジック実行 (合計計算、ポップアップ判定)
            OrderHistoryLogic logic = new OrderHistoryLogic();
            int totalOrderPrice = logic.calcTotalOrderPrice(orderList);
            int totalOrderQuantity = logic.calcTotalOrderQuantity(orderList);
            int popupStatus = logic.showPopUp(orderList, action);

            // お会計確定処理 (「はい」が押された場合)
            if ("yes".equals(action)) {
                // DBの会計状態を更新
                dao.updateAccountingFlag(sessionId);
                
                // リクエストへ結果を保存し、セッションを削除
                request.setAttribute("tableNumber", tableNumber);
                request.setAttribute("totalOrderPrice", totalOrderPrice);
                session.invalidate(); 

                // お会計確定画面へ遷移
                RequestDispatcher dispatcher = request.getRequestDispatcher("CheckOutServlet");
                dispatcher.forward(request, response);
                return;
            }

            // 通常表示処理
            request.setAttribute("orderList", orderList);
            request.setAttribute("tableNumber", tableNumber);
            request.setAttribute("totalOrderPrice", totalOrderPrice);
            request.setAttribute("totalOrderQuantity", totalOrderQuantity);
            request.setAttribute("popupStatus", popupStatus);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/orderHistory.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            // 例外発生時はエラー画面へ
            // response.sendRedirect("/WEB-INF/error.jsp");
            System.out.println("error");
        }
    }
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response)
					throws ServletException, IOException {
        doPost(request, response);
    }
}