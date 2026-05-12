package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.TableInfo;

@WebServlet("/OrderStartServlet")
public class OrderStartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, 
			HttpServletResponse response) 
					throws ServletException, IOException {
		String tableNumber = request.getParameter("tableNumber");
		
		TableInfo tableInfo = new TableInfo();
		tableInfo.setTableId(Integer.parseInt(tableNumber));
		
		
		
		request.setAttribute("tableInfo", tableInfo);
		
		RequestDispatcher dispatcher = 
				request.getRequestDispatcher
				("WEB-INF/jsp/orderStart.jsp");
		dispatcher.forward(request, response);
	}

}
