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

@WebServlet("/ShowMenuServlet")
public class ShowMenuServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // 1. JSPのリンクやOrderStartから送られてくるパラメータを取得
        String category = request.getParameter("category");
        String guestCountStr = request.getParameter("guestCount");

        // 初回アクセス（カテゴリ未選択）の場合はデフォルトで「okonomiyaki」にする
        if (category == null || category.isEmpty()) {
            category = "okonomiyaki";
        }

        ShowMenuDAO dao = new ShowMenuDAO();

        // 2. 人数更新処理
        if (guestCountStr != null && !guestCountStr.isEmpty()) {
            try {
                int guestCount = Integer.parseInt(guestCountStr);
                dao.updateGuestCount(guestCount);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        // 3. DAOにカテゴリを渡して、絞り込んだリストを取得
        List<ProductInfo> productList = dao.findProductTable(category);

        // 4. JSPに渡すデータをセット
        request.setAttribute("productList", productList);
        // 現在選ばれているカテゴリも渡すと便利（任意）
        request.setAttribute("currentCategory", category);

        // 5. 遷移（フォルダ構成に合わせてパスを修正）
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/jsp/showMenu.jsp");
        rd.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}