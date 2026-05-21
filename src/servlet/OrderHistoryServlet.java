package servlet;

import java.io.IOException;
import java.sql.SQLException;
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
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response)
					throws ServletException, IOException {
		System.out.println("doGetの上");
        doPost(request, response);
    }
	
	protected void doPost(HttpServletRequest request, 
			HttpServletResponse response)
					throws ServletException, IOException {
		System.out.println("doPostの上");
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        
        String tableNumber = (String) session.getAttribute("tableNumber");
        //String sessionId = session.getId();
        String action = request.getParameter("action");
        OrderHistoryDAO dao = new OrderHistoryDAO();

        try {
            // 注文履歴情報の取得
            List<OrderHistoryInfo> orderHistoryList = dao.findOrderDetails();

            // ロジック実行 (合計計算、ポップアップ判定)
            OrderHistoryLogic logic = new OrderHistoryLogic();
            int totalOrderPrice = logic.calcTotalOrderPrice(orderHistoryList);
            int totalOrderQuantity = logic.calcTotalOrderQuantity(orderHistoryList);
            int popupStatus = logic.showPopUp(orderHistoryList, action);

            // お会計確定処理 (「はい」が押された場合)
            if ("yes".equals(action)) {
                // DBの会計状態を更新
            	System.out.println("はいボタンが押されました");
                dao.updateAccountingFlag();
                
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
            request.setAttribute("orderHistoryList", orderHistoryList);
            request.setAttribute("tableNumber", tableNumber);
            request.setAttribute("totalOrderPrice", totalOrderPrice);
            request.setAttribute("totalOrderQuantity", totalOrderQuantity);
            request.setAttribute("popupStatus", popupStatus);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/orderHistory.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            // 例外発生時はエラー画面へ
            response.sendRedirect("/WEB-INF/error.jsp");
            System.out.println("error");
        }
    }
}