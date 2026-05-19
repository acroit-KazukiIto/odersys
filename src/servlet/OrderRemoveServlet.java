package servlet;

import java.io.IOException;

import dao.OrderRemoveDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.OrderRemoveInfo;

@WebServlet("/OrderRemoveServlet")
public class OrderRemoveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String text = request.getParameter("orderId");
		int num = Integer.parseInt(text);
		OrderRemoveInfo orInfo = new OrderRemoveInfo();
		OrderRemoveDAO orDAO = new OrderRemoveDAO();
		orDAO.deleteOrderDetails(num);

		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/orderList.jsp");
		dispatcher.forward(request, response);
	}

}
