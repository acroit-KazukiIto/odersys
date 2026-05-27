package servlet;

import java.io.IOException;
import java.util.List;

import dao.ToppingDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ItemDetailsChangeLogic;
import model.ItemDetailsInfo;
import model.OrderListInfo;

@WebServlet("/ItemDetailsChangeServlet")
public class ItemDetailsChangeServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String orderIdStr = request.getParameter("orderId");
        if (orderIdStr == null) {
            orderIdStr = request.getParameter("oid");
        }
        if (orderIdStr == null || orderIdStr.isEmpty()) {
            response.sendRedirect("OrderListServlet");
            return;
        }
        int orderId = Integer.parseInt(orderIdStr);
        ToppingDAO dao = new ToppingDAO();
        OrderListInfo ol = dao.findOrderInfo(orderId);
        List<ItemDetailsInfo> toppingList = dao.findToppingListByOrderId(orderId);
        ItemDetailsChangeLogic logic = new ItemDetailsChangeLogic();
        int subTotal = logic.calcSubTotal(ol.getProductPrice(), toppingList);

        request.setAttribute("ol", ol);
        request.setAttribute("toppingList", toppingList);
        request.setAttribute("subTotal", subTotal);
        request.getRequestDispatcher("/WEB-INF/jsp/itemDetailsChange.jsp")
                .forward(request, response);
    }

    //イベント
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String orderIdStr = request.getParameter("orderId");
        if (orderIdStr == null) {
            orderIdStr = request.getParameter("oid");
        }
        if (orderIdStr == null || orderIdStr.isEmpty()) {
            response.sendRedirect("OrderListServlet");
            return;
        }
        int orderId = Integer.parseInt(orderIdStr);

        String button = request.getParameter("Button");
        String mode = request.getParameter("mode");

        ToppingDAO dao = new ToppingDAO();
        OrderListInfo ol = dao.findOrderInfo(orderId);
        List<ItemDetailsInfo> toppingList = dao.findToppingListByOrderId(orderId);

        for (int i = 0; i < toppingList.size(); i++) {
            String qty = request.getParameter("oldQty_" + i);
            if (qty != null && !qty.isEmpty()) {
                toppingList.get(i).setToppingQuantity(Integer.parseInt(qty));
            }
        }

        ItemDetailsChangeLogic logic = new ItemDetailsChangeLogic();

        // ＋－ボタン
        if (button != null && (button.startsWith("+") || button.startsWith("-"))) {
            int index = Integer.parseInt(button.substring(1));
            String action = button.startsWith("+") ? "plus" : "minus";
            logic.calcToppingQuantity(toppingList, index, action);
            ItemDetailsInfo t = toppingList.get(index);
            int qty = t.getToppingQuantity();


            // ★DB反映
            // 0ならdelte
            if (qty <= 0) {
                dao.deleteTopping(orderId, t.getToppingId());
            // 1以上update
            } else {
                dao.updateToppingQuantity(orderId, t.getToppingId(), qty);
            }
            int subTotal = logic.calcSubTotal(ol.getProductPrice(), toppingList);
            request.setAttribute("ol", ol);
            request.setAttribute("toppingList", toppingList);
            request.setAttribute("subTotal", subTotal);
            request.getRequestDispatcher("/WEB-INF/jsp/itemDetailsChange.jsp")
                    .forward(request, response);
            return;
        }

        // updateボタン
        if ("update".equals(mode)) {
            for (ItemDetailsInfo t : toppingList) {
                int qty = t.getToppingQuantity();
                if (qty <= 0) {
                    dao.deleteTopping(orderId, t.getToppingId());
                } else {
                    dao.updateToppingQuantity(orderId, t.getToppingId(), qty);
                }
            }
            response.sendRedirect("OrderListServlet");
            return;
        }
        response.sendRedirect("OrderListServlet");
    }
}