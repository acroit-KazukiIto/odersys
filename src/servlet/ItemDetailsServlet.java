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
    private static final long serialVersionUID = 1L;
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String productId = request.getParameter("productId");
        String name = request.getParameter("productName");
        String category = request.getParameter("productCategory");
        int price = (request.getParameter("productPrice") != null) ? Integer.parseInt(request.getParameter("productPrice")) : 0;
        //dao
        ToppingListDAO dao = new ToppingListDAO();

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

    //イベント
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String buttonName = request.getParameter("Button");
        String productIdStr = request.getParameter("productId");
        String name = request.getParameter("productName");
        String priceStr = request.getParameter("productPrice");
        String subTotalStr = request.getParameter("subTotal");
        String category = request.getParameter("productCategory");
        
        int productId = Integer.parseInt(productIdStr);
        int price = Integer.parseInt(priceStr);
        int subTotal = Integer.parseInt(subTotalStr);
        //dao
        ToppingListDAO dao = new ToppingListDAO();
        List<ItemDetailsInfo> tList = dao.findToppingList(category);
        for (int i = 0; i < tList.size(); i++) {
            String qtyStr = request.getParameter("oldQty_" + i);
            if (qtyStr != null) {
                tList.get(i).setToppingQuantity(Integer.parseInt(qtyStr));
            }
        }

        //bottun 表示
        if (buttonName.startsWith("+") || buttonName.startsWith("-")) {
            ItemDetailsLogic logic = new ItemDetailsLogic();
            String action = buttonName.startsWith("+") ? "plus" : "minus";
            int index = Integer.parseInt(buttonName.substring(1));
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

        if ("追加".equals(buttonName)) {
        	//product_details insert
            boolean productSuccess = dao.insertProductDetail(productId);
            if (productSuccess) {
                int orderId = dao.getLastOrderId();
                int toppingId = 0;
                //order_details insert
                dao.insertOrderDetail(orderId, 1, subTotal, 1, 0, 0, 0, productId, toppingId);
                for (ItemDetailsInfo t : tList) {
                    if (t.getToppingQuantity() > 0) {
                        dao.insertMutipleToppings(t.getToppingId(), t.getToppingQuantity(), orderId);
                    }
                }
                response.sendRedirect("OrderListServlet");
                return;
            }
        }
        response.sendRedirect("ShowMenuServlet");
    }
}