package servlet;

import java.io.IOException;
import java.util.List;

import dao.ToppingListDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ItemDetailsInfo;
import model.ItemDetailsLogic;

@WebServlet("/ItemDetailsServlet")
public class ItemDetailsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String productId = request.getParameter("productId");
        String name = request.getParameter("productName");
        String category = request.getParameter("productCategory");
        String priceStr = request.getParameter("productPrice");

        int price = (priceStr != null) ? Integer.parseInt(priceStr) : 0;

        ToppingListDAO dao = new ToppingListDAO();
        List<ItemDetailsInfo> tList = dao.findToppingList(category);

        request.setAttribute("productId", productId);
        request.setAttribute("selectedPName", name);
        request.setAttribute("selectedPPrice", price);
        request.setAttribute("currentCategory", category);
        request.setAttribute("subTotal", price);
        request.setAttribute("toppingList", tList);

        request.getRequestDispatcher("WEB-INF/jsp/itemDetails.jsp")
                .forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String button = request.getParameter("Button");
        String mode = request.getParameter("mode");

        String productIdStr = request.getParameter("productId");
        String name = request.getParameter("productName");
        String priceStr = request.getParameter("productPrice");
        String subTotalStr = request.getParameter("subTotal");
        String category = request.getParameter("productCategory");

        // 🔥 null防止
        if (productIdStr == null || priceStr == null || subTotalStr == null) {
            response.sendRedirect("ShowMenuServlet");
            return;
        }

        int productId = Integer.parseInt(productIdStr);
        int price = Integer.parseInt(priceStr);
        int subTotal = Integer.parseInt(subTotalStr);

        ToppingListDAO dao = new ToppingListDAO();
        List<ItemDetailsInfo> tList = dao.findToppingList(category);

        // トッピング数量復元
        for (int i = 0; i < tList.size(); i++) {
            String qtyStr = request.getParameter("oldQty_" + i);
            if (qtyStr != null) {
                tList.get(i).setToppingQuantity(Integer.parseInt(qtyStr));
            }
        }

        // =========================
        // ＋ / － ボタン処理
        // =========================
        if (button != null && (button.startsWith("+") || button.startsWith("-"))) {

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

            request.getRequestDispatcher("WEB-INF/jsp/itemDetails.jsp")
                    .forward(request, response);
            return;
        }

        // =========================
        // 追加処理（mode）
        // =========================
        if ("add".equals(mode)) {

            boolean ok = dao.insertProductDetail(productId);

            if (ok) {
                int orderId = dao.getLastOrderId();

                dao.insertOrderDetail(orderId, 1, subTotal, 1, 0, 0, 0, productId, 0);

                for (ItemDetailsInfo t : tList) {
                    if (t.getToppingQuantity() > 0) {
                        dao.insertMutipleToppings(
                                t.getToppingId(),
                                t.getToppingQuantity(),
                                orderId
                        );
                    }
                }

                response.sendRedirect("OrderListServlet");
                return;
            }
        }

        response.sendRedirect("ShowMenuServlet");
    }
}