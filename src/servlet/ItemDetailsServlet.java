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

    // 商品詳細画面表示
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String productId = request.getParameter("productId");
        String name = request.getParameter("productName");
        String category = request.getParameter("productCategory");

        if (category == null) {
            category = request.getParameter("categoryName");
        }

        int price = 0;

        String priceStr = request.getParameter("productPrice");

        if (priceStr != null && !priceStr.isEmpty()) {
            price = Integer.parseInt(priceStr);
        }

        // トッピング取得
        List<ItemDetailsInfo> tList =
                new ToppingListDAO().findToppingList(category);

        // request保存
        request.setAttribute("productId", productId);
        request.setAttribute("selectedPName", name);
        request.setAttribute("selectedPPrice", price);
        request.setAttribute("currentCategory", category);
        request.setAttribute("subTotal", price);

        if (tList.isEmpty()) {
            request.setAttribute("toppingList", null);
        } else {
            request.setAttribute("toppingList", tList);
        }

        request.getRequestDispatcher("WEB-INF/jsp/itemDetails.jsp").forward(request, response);
    }

    // ボタン処理
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String buttonName = request.getParameter("Button");

        // 追加ボタン
        if ("追加".equals(buttonName)) {

            String productIdStr =
                    request.getParameter("productId");

            if (productIdStr == null
                    || productIdStr.equals("null")
                    || productIdStr.isEmpty()) {

                response.sendRedirect("ShowMenuServlet");
                return;
            }

            int productId =
                    Integer.parseInt(productIdStr);

            ToppingListDAO dao =
                    new ToppingListDAO();

            boolean isSuccess =
                    dao.insertProductDetail(productId);

            if (isSuccess) {

                response.sendRedirect("OrderListServlet");
                return;
            }

            // INSERT失敗
            response.sendRedirect("ShowMenuServlet");
            return;
        }

        // ＋－ボタン処理
        String productId = request.getParameter("productId");
        String name = request.getParameter("productName");
        String priceStr = request.getParameter("productPrice");
        String category = request.getParameter("productCategory");

        int price = 0;

        if (priceStr != null && !priceStr.isEmpty()) {
            price = Integer.parseInt(priceStr);
        }

        // トッピング取得
        List<ItemDetailsInfo> tList =
                new ToppingListDAO().findToppingList(category);

        // 前回数量復元
        if (!tList.isEmpty()) {

            for (int i = 0; i < tList.size(); i++) {

                String qtyStr =
                        request.getParameter("oldQty_" + i);

                if (qtyStr != null
                        && !qtyStr.isEmpty()) {

                    tList.get(i).setToppingQuantity(
                            Integer.parseInt(qtyStr));
                }
            }

            // + -
            if (buttonName != null
                    && (buttonName.startsWith("+")
                    || buttonName.startsWith("-"))) {

                ItemDetailsLogic logic =
                        new ItemDetailsLogic();

                String action =
                        buttonName.startsWith("+")
                        ? "plus"
                        : "minus";

                int index =
                        Integer.parseInt(
                                buttonName.substring(1));

                logic.calcToppingQuantity(
                        tList,
                        index,
                        action);
            }

            // 小計計算
            int total =
                    new ItemDetailsLogic()
                    .calcSubTotal(price, tList);

            request.setAttribute("subTotal", total);
            request.setAttribute("toppingList", tList);

        } else {

            request.setAttribute("subTotal", price);
            request.setAttribute("toppingList", null);
        }

        // request保存
        request.setAttribute("productId", productId);
        request.setAttribute("selectedPName", name);
        request.setAttribute("selectedPPrice", price);
        request.setAttribute("currentCategory", category);

        request.getRequestDispatcher("WEB-INF/jsp/itemDetails.jsp").forward(request, response);
    }
}