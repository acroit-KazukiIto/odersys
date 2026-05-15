package servlet;

import java.io.IOException;
import java.util.List;

import dao.ToppingListDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.ItemDetailsInfo;
import model.ItemDetailsLogic;

@WebServlet("/ItemDetailsServlet")
public class ItemDetailsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        // メニュー画面からのパラメータ取得
        String name = request.getParameter("productName");
        String priceStr = request.getParameter("productPrice");

        // 初回アクセス（メニューから来たとき）だけDAOを実行
        if (name != null && priceStr != null) {
            try {
                int price = Integer.parseInt(priceStr);
                session.setAttribute("selectedPName", name);
                session.setAttribute("selectedPPrice", price);
                session.setAttribute("subTotal", price);
                
                // DBからトッピングを取得a
                ToppingListDAO dao = new ToppingListDAO();
                List<ItemDetailsInfo> toppingList = dao.findToppingTable();
                session.setAttribute("toppingList", toppingList);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/itemDetails.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();

        // ＋ーボタンの処理
        String actionParam = request.getParameter("action");
        List<ItemDetailsInfo> toppingList = (List<ItemDetailsInfo>) session.getAttribute("toppingList");
        Integer productPrice = (Integer) session.getAttribute("selectedPPrice");

        if (actionParam != null && actionParam.contains("_") && toppingList != null) {
            String[] parts = actionParam.split("_");
            String action = parts[0];
            int index = Integer.parseInt(parts[1]);

            ItemDetailsLogic logic = new ItemDetailsLogic();
            logic.calcToppingQuantity(toppingList, index, action);
            
            if (productPrice != null) {
                session.setAttribute("subTotal", logic.calcSubTotal(productPrice, toppingList));
            }
        }

        // doPostの後も同じJSPを表示
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/itemDetails.jsp");
        dispatcher.forward(request, response);
    }
}