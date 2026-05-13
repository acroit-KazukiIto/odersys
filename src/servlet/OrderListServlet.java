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

@WebServlet("/OrderListServlet")
public class OrderListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//リクエストパラメータの文字コード指定
		request.setCharacterEncoding("UTF-8");
		//リクエストパラメータの取得
		String productName = request.getParameter("productName");
		String toppingName = request.getParameter("toppingName");
		String productPrice = request.getParameter("productPrice");
		String toppingPrice = request.getParameter("toppingPrice");
		String toppingQuantity = request.getParameter("toppingQuantity");
		String subTotal = request.getParameter("subTotal");
		
		List<String>orderList = new ArrayList<String>(); 
		orderList.add("productName");
		orderList.add("toppingName");
		orderList.add("productPrice");
		orderList.add("toppingPrice");
		orderList.add("toppingQuantity");
		orderList.add("subTotal");
			
		
		
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
		ol.setOrderList(orderList);
		
		 
		//ol.setOrderList(orderList);
		//おそらくトッピング選択の段階で使われると思う。
		//ol.setProductTopping(Integer.parseInt(productTopping)); 
		request.setAttribute("orderList", orderList);
		
		//フォーワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/orderList.jsp");
		dispatcher.forward(request, response);
		
	}

}
