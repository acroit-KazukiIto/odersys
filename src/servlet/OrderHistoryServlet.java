package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dao.OrderHistoryDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.OrderHistoryInfo;
import model.OrderHistoryLogic;

@WebServlet("/OrderHistoryServlet")
public class OrderHistoryServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response)
					throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		
		// 前画面からの入力データ
		String[] productNames = request.getParameterValues("productName");
		String[] toppingNames = request.getParameterValues("toppingName");
		String[] subTotals = request.getParameterValues("subTotal");
		String[] orderQuantities = request.getParameterValues("orderQuantity");
		String tableNumber = request.getParameter("tableNumber");
		
		// DBからデータを取得し、入力を統合
		OrderHistoryDAO dao = new OrderHistoryDAO();
		List<OrderHistoryInfo> dbList = dao.findOrderDetails();
		List<OrderHistoryInfo> orderList = new ArrayList<>();
		
		if (productNames != null && dbList != null && productNames.length <= dbList.size()) {
			for (int i = 0; i < productNames.length; i++) {
				OrderHistoryInfo item = dbList.get(i);
				item.setProductName(productNames[i]);
				item.setToppingName(toppingNames[i]);
				
				item.setSubTotal(Integer.parseInt(subTotals[i]));
				
				item.setOrderQuantity(Integer.parseInt(orderQuantities[i]));
				orderList.add(item);
			}
		}
		
		// ロジック実行
		OrderHistoryLogic logic = new OrderHistoryLogic();
		int totalOrderPrice = logic.calcTotalOrderPrice(orderList);
		int totalOrderQuantity = logic.calcTotalOrderQuantity(orderList);
		int popupStatus = logic.showPopUp(orderList, action);
		
		// jspへの出力データセット
		request.setAttribute("orderList", orderList);
		request.setAttribute("tableNumber", tableNumber);
		request.setAttribute("totalOrderPrice", totalOrderPrice);
		request.setAttribute("totalOrderQuantity", totalOrderQuantity);
		request.setAttribute("popupStatus", popupStatus);
		
		// 画面遷移判定
		if ("yes".equals(action)) {
			// 会計完了画面へ遷移
			RequestDispatcher dispatcher = request.getRequestDispatcher("CheckOutServlet");
			dispatcher.forward(request, response);
		} else {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/orderHistory.jsp");
			dispatcher.forward(request, response);
		}
	}
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response)
					throws ServletException, IOException {
		doPost(request, response);
	}
}
