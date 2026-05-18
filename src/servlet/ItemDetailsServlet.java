package servlet;

import java.io.IOException;

import dao.ToppingListDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/ItemDetailsServlet")
public class ItemDetailsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();

        String checkAction = request.getParameter("action");
        
        if ("insert".equals(checkAction) || "submit_insert".equals(checkAction)) {
            
            Integer productId = (Integer) session.getAttribute("selectedProductId");
            Integer orderId = (Integer) session.getAttribute("orderId");
            if (orderId == null) {
                orderId = 115; 
            }

            if (productId != null) {
                ToppingListDAO dao = new ToppingListDAO();
                
                // product_details に1行追加
                boolean isSuccess = dao.insertProductDetail(orderId, productId);

                if (isSuccess) {
                    response.sendRedirect("OrderListServlet");
                    return; 
                } else {
                    response.sendRedirect("itemDetails.jsp");
                    return;
                }
            } else {
                System.out.println("【エラー】selectedProductIdがセッションにありません。");
                response.sendRedirect("itemDetails.jsp");
                return;
            }
        }

        
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}