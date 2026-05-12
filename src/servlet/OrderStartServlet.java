package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.TableInfo;

@WebServlet("/OrderStartServlet")
public class OrderStartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, 
			HttpServletResponse response) 
					throws ServletException, IOException {
		//request.setCharacterEncoding("UTF-8");
		String tableNumber = request.getParameter("tableId");
		String sessionId = request.getParameter("sessionid");
		String sessionStatus = request.getParameter("sessionStatus");
		int tableId = 0;
		int sessionNum = 0;
		
		try {
			tableId = Integer.parseInt(tableNumber);
		} catch (NumberFormatException e) {
			System.out.println("数値が正しく入力されていません");
		}
		
		
		TableInfo tableInfo = new TableInfo(tableId, sessionNum, sessionStatus);
		tableInfo.setTableId(tableId);
		
		HttpSession session = request.getSession();
		
		request.setAttribute("tableInfo", tableInfo);
		
		RequestDispatcher dispatcher = 
				request.getRequestDispatcher
				("WEB-INF/jsp/orderStart.jsp");
		dispatcher.forward(request, response);
	}

}
