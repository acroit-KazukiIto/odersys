package servlet;

import java.io.IOException;
import java.util.List;

import dao.ToppingListDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.ItemDetailsInfo;

@WebServlet("/ItemDetailsServlet")
public class ItemDetailsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        // ShowMenuからの情報取得
        String name = request.getParameter("productName");
        String price = request.getParameter("productPrice");
        if(name != null) session.setAttribute("selectedPName", name);
        if(price != null) session.setAttribute("selectedPPrice", Integer.parseInt(price));

        // トッピングリスト取得（初回のみ）
        ToppingListDAO dao = new ToppingListDAO();
        List<ItemDetailsInfo> toppingList = dao.findToppingTable();
        session.setAttribute("toppingList", toppingList);

        calcSubTotal(request);
        request.getRequestDispatcher("WEB-INF/jsp/itemDetails.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        List<ItemDetailsInfo> toppingList = (List<ItemDetailsInfo>) session.getAttribute("toppingList");

        if (action.startsWith("plus") || action.startsWith("minus")) {
            int index = Integer.parseInt(action.substring(action.indexOf("_") + 1));
            ItemDetailsInfo target = toppingList.get(index);
            int qty = target.getToppingQuantity();

            if (action.startsWith("plus") && qty < target.getToppingStock()) {
                target.setToppingQuantity(qty + 1);
            } else if (action.startsWith("minus") && qty > 0) {
                target.setToppingQuantity(qty - 1);
            }
            calcSubTotal(request);
            request.getRequestDispatcher("WEB-INF/jsp/itemDetails.jsp").forward(request, response);
        }
    }

    // 小計計算 Logic
    private void calcSubTotal(HttpServletRequest request) {
        HttpSession session = request.getSession();
        int basePrice = (Integer) session.getAttribute("selectedPPrice");
        List<ItemDetailsInfo> list = (List<ItemDetailsInfo>) session.getAttribute("toppingList");
        
        int total = basePrice;
        for (ItemDetailsInfo t : list) {
            total += (t.getToppingPrice() * t.getToppingQuantity());
        }
        session.setAttribute("subTotal", total);
    }
}