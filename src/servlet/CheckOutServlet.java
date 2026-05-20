package servlet;

import java.io.IOException;
import java.sql.SQLException;

import dao.CheckOutDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.CheckOutInfo;

@WebServlet("/CheckOutServlet")
public class CheckOutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	System.out.println("doPostに入りました");
        // 入力データの取得
        String tableNumber = request.getParameter("tableNumber");
        String totalPriceStr = request.getParameter("totalOrderPrice");
        int totalOrderPrice = Integer.parseInt(totalPriceStr);
        System.out.println("入力データの取得");

        // データベースの更新
        CheckOutDAO dao = new CheckOutDAO();
        try {
            dao.updateByOrderDetails(tableNumber);
            dao.updateByTableSession(tableNumber);
            dao.updateByTableMaster(tableNumber);
			System.out.println("データベースの更新がされました");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("✕");
        }

        // jspへの出力データのセット
        CheckOutInfo info = new CheckOutInfo(tableNumber, totalOrderPrice);
        request.setAttribute("checkOutInfo", info);
        System.out.println("jspへの出力データをセットを行いました");

        // jspへフォワード
        request.getRequestDispatcher("/WEB-INF/jsp/checkOut.jsp").forward(request, response);
        System.out.println("jspへフォワードしました");
    }

    protected void doGet(HttpServletRequest request,
    		HttpServletResponse response)
    				throws ServletException, IOException {
    	System.out.println("doPostに入りました");
        doPost(request, response);
    }
}