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
import model.ProductInfo;

@WebServlet("/showMenu")
public class ShowMenuServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // guestCountをrequestから取得する
        String guestCountStr = request.getParameter("guestCount");
        
        ShowMenuDAO dao = new ShowMenuDAO();

        // guestCountが送られてきた場合DB更新を実行
        if (guestCountStr != null && !guestCountStr.isEmpty()) {
            try {
                int guestCount = Integer.parseInt(guestCountStr);
                // table_sessionsのguestCountを更新）
                dao.updateGuestCount(guestCount);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        //データベースのproductテーブルから情報をリストとして取得
        List<ProductInfo> productList = dao.findProductTable();

        //取得したproductListをrequestにセット
        request.setAttribute("productList", productList);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/showMenu.jsp");
        rd.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}