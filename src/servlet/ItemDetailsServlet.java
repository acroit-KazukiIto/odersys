package servlet;

import java.io.IOException;
import java.util.List;

import dao.OrderStartDAO;
import dao.ToppingListDAO;
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
        request.setCharacterEncoding("UTF-8");
        String productId = request.getParameter("productId");
        String name = request.getParameter("productName");
        String category = request.getParameter("productCategory");
        int price = (request.getParameter("productPrice") != null) ? Integer.parseInt(request.getParameter("productPrice")) : 0;
        ToppingListDAO dao = new ToppingListDAO();
        //dao
        List<ItemDetailsInfo> tList = dao.findToppingList(category);
        //request
        request.setAttribute("productId", productId);
        request.setAttribute("selectedPName", name);
        request.setAttribute("selectedPPrice", price);
        request.setAttribute("currentCategory", category);
        request.setAttribute("subTotal", price);
        request.setAttribute("toppingList", tList);

        request.getRequestDispatcher("WEB-INF/jsp/itemDetails.jsp").forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        //卓番のためのsession
        HttpSession session = request.getSession();
        String button = request.getParameter("Button");
        String productIdStr = request.getParameter("productId");
        String name = request.getParameter("productName");
        String priceStr = request.getParameter("productPrice");
        String subTotalStr = request.getParameter("subTotal");
        String category = request.getParameter("productCategory");

        int productId = Integer.parseInt(productIdStr);
        int price = Integer.parseInt(priceStr);
        int subTotal = Integer.parseInt(subTotalStr);

        // tableNumber取得
        String tableNumber = (String) session.getAttribute("tableNumber");
        int tableId = 0;
        if (tableNumber != null) {
            tableId = Integer.parseInt(tableNumber);
        }
        // session_id取得
        OrderStartDAO orderDao = new OrderStartDAO();
        int sessionId = orderDao.findSessionId(tableId);
        ToppingListDAO dao = new ToppingListDAO();

        List<ItemDetailsInfo> tList = dao.findToppingList(category);
        for (int i = 0; i < tList.size(); i++) {
            String qty = request.getParameter("oldQty_" + i);
            if (qty != null) {
                tList.get(i).setToppingQuantity(Integer.parseInt(qty));
            }
        }
        
        // ＋－ボタン
        if (button.startsWith("+") || button.startsWith("-")) {
            ItemDetailsLogic logic = new ItemDetailsLogic();
            String action = button.startsWith("+") ? "plus" : "minus";
            int index = Integer.parseInt(button.substring(1));
            logic.calcToppingQuantity(tList, index, action);
            subTotal = logic.calcSubTotal(price, tList);

            request.setAttribute("productId", productIdStr);
            request.setAttribute("selectedPName", name);
            request.setAttribute("selectedPPrice", price);
            request.setAttribute("currentCategory", category);
            request.setAttribute("subTotal", subTotal);
            request.setAttribute("toppingList", tList);
            request.getRequestDispatcher("WEB-INF/jsp/itemDetails.jsp").forward(request, response);
            return;
        }

        // 追加ボタン
        if ("追加".equals(button)) {
        	//product_detail
            boolean ok = dao.insertProductDetail(productId);
            if (ok) {
                int orderId = dao.getLastOrderId();
                // order_details
                dao.insertOrderDetail(orderId, 1, subTotal, sessionId, 0, 0, 0, productId, 0);
                // multiple_toppings
                for (ItemDetailsInfo t : tList) {
                    if (t.getToppingQuantity() > 0) {
                        dao.insertMutipleToppings(t.getToppingId(), t.getToppingQuantity(), orderId);
                    }
                }
                //上手く行ったら
                response.sendRedirect("OrderListServlet");
                return;
            }
        }
        //失敗だたら
        response.sendRedirect("ShowMenuServlet");
    }
}