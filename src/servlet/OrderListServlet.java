package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import dao.OrderListDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.OrderListInfo;
import model.OrderListLogic;

@WebServlet("/OrderListServlet")
public class OrderListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;



	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("サーブレットpostの1番うえ");
		OrderListLogic logic = new OrderListLogic();
		request.setCharacterEncoding("UTF-8");
		OrderListDAO olDAO = new OrderListDAO();
		
		//データ取得処理
		try {
			List<OrderListInfo> olList = olDAO.findorderDetailsByorderFlag();
			request.setAttribute("olList", olList);
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}		
		logic.calcSubTotal();
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/orderList.jsp");
		dispatcher.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("サーブレットgetの1番うえ");
		request.setCharacterEncoding("UTF-8");
		String Button = request.getParameter("Button");
		OrderListDAO olDAO = new OrderListDAO();
		OrderListLogic logic = new OrderListLogic();
		

		//イベント処理
		if("追加".equals(Button)){
			
			//データ取得処理
			try {
				List<OrderListInfo> olList = olDAO.findorderDetailsByorderFlag();
				request.setAttribute("olList", olList);
			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			logic.calcSubTotal();
		}else if("+".equals(Button)) {
			//プラス処理
			logic.calcOrderQuantity(1);
			//データ取得処理
			try {
				List<OrderListInfo> olList = olDAO.findorderDetailsByorderFlag();
				request.setAttribute("olList", olList);
			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			logic.calcSubTotal();
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/orderList.jsp");
			dispatcher.forward(request, response);

		}else if("-".equals(Button)) {
			//マイナス処理
			logic.calcOrderQuantity(-1);
			//データ取得処理
			try {
				List<OrderListInfo> olList = olDAO.findorderDetailsByorderFlag();
				request.setAttribute("olList", olList);
			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			logic.calcSubTotal();
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/orderList.jsp");
			dispatcher.forward(request, response);
		}		
			
	}

}
