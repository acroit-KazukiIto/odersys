package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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
		System.out.println("サーブレットgetの1番うえ");
		request.setCharacterEncoding("UTF-8");
		String Button = request.getParameter("Button");


		//イベント処理
		if("追加".equals(Button)){

		}else if("+".equals(Button)) {
			//プラス処理
			OrderListLogic logic = new OrderListLogic();
			logic.calcOrderQuantity(1);
			
			OrderListDAO olDAO = new OrderListDAO();
			
			//データ取得処理
			List<OrderListInfo> olList;
			try {
				olList = olDAO.findorderDetailsByorderFlag();
				for(OrderListInfo ol : olList) {
					String debug = ol.getProductName();
					System.out.println("ol出力" + debug);
					request.setAttribute("ol", ol);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}	
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/orderList.jsp");
			dispatcher.forward(request, response);
			
		}else if("-".equals(Button)) {
			//マイナス処理
			OrderListLogic logic = new OrderListLogic();
			logic.calcOrderQuantity(-1);
			

			OrderListDAO olDAO = new OrderListDAO();
			//データ取得処理
			List<OrderListInfo> olList;
			try {
				olList = olDAO.findorderDetailsByorderFlag();
				for(OrderListInfo ol : olList) {
					String debug = ol.getProductName();
					System.out.println("ol出力" + debug);
					request.setAttribute("ol", ol);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}	
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/orderList.jsp");
			dispatcher.forward(request, response);
			
		}


		
		//フォーワード
		

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("サーブレットpostの1番うえ");

		request.setCharacterEncoding("UTF-8");
		OrderListDAO olDAO = new OrderListDAO();
		//データ取得処理
		List<OrderListInfo> olList = new ArrayList<>();
				try {
			olList = olDAO.findorderDetailsByorderFlag();
			Integer oli = olList.size();
			System.out.println("ol出力" + oli);
			
			if(oli == 0) {
				RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/orderListNull.jsp");
				dispatcher.forward(request, response);
			}
			for(OrderListInfo ol : olList) {
				int debug = ol.getOrderId();
				
				request.setAttribute("ol", ol);
			}
			

			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/orderList.jsp");
		dispatcher.forward(request, response);

		

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
