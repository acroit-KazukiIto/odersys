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
		System.out.println("サーブレットgetの1番うえ");
		request.setCharacterEncoding("UTF-8");
		
		//仮の画面表示
		String productName = "(仮)お好み焼き";
		String toppingName = "ベビースター";
		String productPrice = "700";
		String toppingPrice = "200";
		String toppingQuantity = "3";
		String subTotal = "1300";
		String Button = request.getParameter("Button");
				
		OrderListInfo ol = new OrderListInfo();
		ol.setProductName(productName);
		ol.setToppingName(toppingName);
		ol.setProductPrice(Integer.parseInt(productPrice));
		ol.setToppingPrice(Integer.parseInt(toppingPrice));
		ol.setToppingQuantity(Integer.parseInt(toppingQuantity));
		ol.setSubTotal(Integer.parseInt(subTotal));
		
		
		

		if("+".equals(Button)) {
			//プラス処理
			OrderListLogic logic = new OrderListLogic();
			logic.calcOrderQuantity(1);
		}else if("-".equals(Button)) {
			//マイナス処理
			OrderListLogic logic = new OrderListLogic();
			logic.calcOrderQuantity(-1);
			
		}
		
		
		request.setAttribute("ol", ol);
		//フォーワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/orderList.jsp");
		dispatcher.forward(request, response);
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("サーブレットpostの1番うえ");
		
		OrderListInfo ol = new OrderListInfo();
		
		
		OrderListDAO olDAO = new OrderListDAO();
		try {
			List<OrderListInfo> olInfo = olDAO.findorderDetailsByorderFlag();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		
		request.setAttribute("ol", ol);
		request.setCharacterEncoding("UTF-8");
		//orderListから
		String Button = request.getParameter("Button");
		
		
		//イベント処理
		if("+".equals(Button)) {
			//プラス処理
			OrderListLogic logic = new OrderListLogic();
			logic.calcOrderQuantity(1);
		}else if("-".equals(Button)) {
			//マイナス処理
			OrderListLogic logic = new OrderListLogic();
			logic.calcOrderQuantity(-1);
			
		}else if("メニュー".equals(Button)) {
			System.out.println("メニューボタン");
			//ShowMenuServletに遷移
			RequestDispatcher rs = request.getRequestDispatcher("ShowMenuServlet");
			rs.forward(request, response);
			
		}else if("注文する".equals(Button)) {
			//ShowMenuServletに遷移
			RequestDispatcher rs = request.getRequestDispatcher("OrderCompleteServlet");
			rs.forward(request, response);
			
		}else if("変更".equals(Button)){
			RequestDispatcher rs = request.getRequestDispatcher("OrderRemoveServlet");
			rs.forward(request, response);
		}else if("追加".equals(Button)) {
			RequestDispatcher rs = request.getRequestDispatcher("OrderCompleteServlet");
			rs.forward(request, response);
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
		request.setAttribute("orderList", ol);
		//フォーワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/orderList.jsp");
		dispatcher.forward(request, response);
		
	}

}
