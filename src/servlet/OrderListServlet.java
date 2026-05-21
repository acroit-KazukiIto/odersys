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
		/*
		try {
			olList = 
			Integer oli = olList.size();
			OrderListInfo oli2 = olList.get(2);
			System.out.println("ol出力" + oli + oli2);

			if(oli == 0) {
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/orderListNull.jsp");
				dispatcher.forward(request, response);
			}
			/*
			for(OrderListInfo ol : olList) {
				int debug = ol.getOrderId();

				request.setAttribute("ol", ol);
			}
			*/


		
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/orderList.jsp");
		dispatcher.forward(request, response);
		
		
		


	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("サーブレットgetの1番うえ");
		request.setCharacterEncoding("UTF-8");
		String Button = request.getParameter("Button");
		OrderListDAO olDAO = new OrderListDAO();
		
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
		}else if("+".equals(Button)) {
			//プラス処理
			OrderListLogic logic = new OrderListLogic();
			logic.calcOrderQuantity(1);
			//データ取得処理
			try {
				List<OrderListInfo> olList = olDAO.findorderDetailsByorderFlag();
				request.setAttribute("olList", olList);
			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/orderList.jsp");
			dispatcher.forward(request, response);

		}else if("-".equals(Button)) {
			//マイナス処理
			OrderListLogic logic = new OrderListLogic();
			logic.calcOrderQuantity(-1);
			//データ取得処理
			try {
				List<OrderListInfo> olList = olDAO.findorderDetailsByorderFlag();
				request.setAttribute("olList", olList);
			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/orderList.jsp");
			dispatcher.forward(request, response);
		}
		
		



		/*
		System.out.println(productName); 
		System.out.println(toppingName);
		System.out.println(productPrice);
		System.out.println(toppingPrice);
		System.out.println(toppingQuantity);
		System.out.println(subTotal);
		 */

		//おそらくトッピング選択の段階で使われると思う。
		//String productTopping  = request.getParameter("productTopping");


		//ol.setOrderList(orderList);
		//おそらくトッピング選択の段階で使われると思う。
		//ol.setProductTopping(Integer.parseInt(productTopping)); 
		//フォーワード


	}

}
