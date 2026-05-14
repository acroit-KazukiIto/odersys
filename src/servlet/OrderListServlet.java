package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.OrderListDao;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.OrderList;
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
				
		OrderList ol = new OrderList();
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
		String Items;
		System.out.println("サーブレットpostの1番うえ");
		
		//リクエストスコープからインスタンス取り出し
		//ItemDetails item = (ItemDetails)request.getAttribute("item");
		//リクエストパラメータの文字コード指定
		request.setCharacterEncoding("UTF-8");
		//リクエストパラメータの取得
		//showMenuから
		String productName = request.getParameter("productName");
		String toppingName = request.getParameter("toppingName");
		String productPrice = request.getParameter("productPrice");
		String toppingPrice = request.getParameter("toppingPrice");
		String toppingQuantity = request.getParameter("toppingQuantity");
		String subTotal = request.getParameter("subTotal");
		//orderListから
		String Button = request.getParameter("Button");
		
		if("+".equals(Button)) {
			//プラス処理
			OrderListLogic logic = new OrderListLogic();
			logic.calcOrderQuantity(1);
		}else if("-".equals(Button)) {
			//マイナス処理
			OrderListLogic logic = new OrderListLogic();
			logic.calcOrderQuantity(-1);
			
		}else if("メニュー".equals(Button)) {
			//ShowMenuServletに遷移
			RequestDispatcher rs = request.getRequestDispatcher("/ShowMenuServlet");
			rs.forward(request, response);
			
		}else if("注文する".equals(Button)) {
			//ShowMenuServletに遷移
			RequestDispatcher rs = request.getRequestDispatcher("/Servlet");
			rs.forward(request, response);
			
		}else if("変更".equals(Button)){
			RequestDispatcher rs = request.getRequestDispatcher("/OrderRemoveServlet");
			rs.forward(request, response);
		}else if("追加".equals(Button)) {
			OrderListDao olDAO = new OrderListDao();
			try {
				olDAO.insertOrderDetails();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		List<String>List = new ArrayList<String>(); 
		
		List.add("productName");
		List.add("toppingName");
		List.add("productPrice");
		List.add("toppingPrice");
		List.add("toppingQuantity");
		List.add("subTotal");
		
		
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
		
		//入力値をプロパティに設定
		OrderList ol = new OrderList();
		ol.setProductName(productName);
		ol.setToppingName(toppingName);
		ol.setProductPrice(Integer.parseInt(productPrice));
		ol.setToppingPrice(Integer.parseInt(toppingPrice));
		ol.setToppingQuantity(Integer.parseInt(toppingQuantity));
		ol.setSubTotal(Integer.parseInt(subTotal));
		//ol.setOrderList(orderList);
		
		 
		//ol.setOrderList(orderList);
		//おそらくトッピング選択の段階で使われると思う。
		//ol.setProductTopping(Integer.parseInt(productTopping)); 
		request.setAttribute("orderList", ol);
		//フォーワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/orderList.jsp");
		dispatcher.forward(request, response);
		
	}

}
