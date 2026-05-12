package servlet;

import java.io.IOException;

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
		String productTopping  = request.getParameter("productTopping");
		
		//入力値をプロパティに設定
		OrderList ol = new OrderList();
		ol.setProductName(productName);
		ol.setToppingName(toppingName);
		ol.setProductPrice(productPrice);
		ol.setToppingPrice(toppingPrice);
		ol.();
		ol.();
		ol.();
		
		//フォーワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/orderList.jsp");
		dispatcher.forward(request, response);
		
	}

}
