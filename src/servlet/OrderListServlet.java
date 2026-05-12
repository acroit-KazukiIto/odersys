package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/OrderListServlet")
public class OrderListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//リクエストパラメータの取得
		String productName = request.getParameter("productName");
		String toppingName = request.getParameter("toppingName");
		int productPrice = Integer.parseInt(productPrice);
		int toppingPrice = Integer.parseInt(toppingPrice);
		int  = request.getParameter();
		int  = request.getParameter();
		int  = request.getParameter();
	}

}
