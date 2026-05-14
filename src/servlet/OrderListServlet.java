package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
		
		//System.out.println("サーブレットgetの1番うえ");
		//リクエストパラメータの文字コード指定
		request.setCharacterEncoding("UTF-8");
		//リクエストパラメータの取得
		/*
		String productName = request.getParameter("productName");
		String toppingName = request.getParameter("toppingName");
		String productPrice = request.getParameter("productPrice");
		String toppingPrice = request.getParameter("toppingPrice");
		String toppingQuantity = request.getParameter("toppingQuantity");
		String subTotal = request.getParameter("subTotal");
		*/
		//仮パラメーター
		String productName = "お好み焼き";
		String toppingName = "ベビースター";
		String productPrice = "700";
		String toppingPrice = "200";
		String toppingQuantity = "3";
		String subTotal = "1300";
		
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
		
		//String log = "log";
		//request.setAttribute("log", log);
		
		request.setAttribute("ol", ol);
		//フォーワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/orderList.jsp");
		dispatcher.forward(request, response);
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("サーブレットgetの1番うえ");
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
			
		}else {
			
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
