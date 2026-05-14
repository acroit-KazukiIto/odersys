package servlet;

import java.io.IOException;
import java.util.List;

import dao.ShowMenuDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.ProductInfo;

@WebServlet("/ShowMenuServlet")
public class ShowMenuServlet extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String tableNumber = request.getParameter("tableNumber");
        if (tableNumber != null) {
            session.setAttribute("tableNumber", tableNumber);
        }

        String guestCountStr = request.getParameter("guestCount");
        ShowMenuDAO dao = new ShowMenuDAO();
        if (guestCountStr != null) {
            dao.updateGuestCount(Integer.parseInt(guestCountStr));
        }

        //  商品データ取得
        List<ProductInfo> productList = dao.findProductTable();
        session.setAttribute("productList", productList);

        // カテゴリ
        String category = request.getParameter("category");
        if (category == null) {
            category = "お好み焼き"; 
        }
        request.setAttribute("currentCategory", category);

        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/showMenu.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); 
        doGet(request, response);
    }
}