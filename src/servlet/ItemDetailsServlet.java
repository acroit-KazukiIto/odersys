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
import model.ItemDetailsLogic;

@WebServlet("/ItemDetailsServlet")
public class ItemDetailsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        String checkAction = request.getParameter("action");
        List<ItemDetailsInfo> toppingList = (List<ItemDetailsInfo>) session.getAttribute("toppingList");
        Integer productPrice = (Integer) session.getAttribute("selectedPPrice");
        
        System.out.println("【デバッグ】doPostに届いたaction: " + checkAction);

        if (checkAction != null) {
            // ====================================================
            // A. 「追加」ボタンが押されたときの処理
            // ====================================================
            if ("insert".equals(checkAction) || "submit_insert".equals(checkAction)) {
                
                Integer productId = (Integer) session.getAttribute("selectedProductId");
                System.out.println("【デバッグ】読み込んだproductId: " + productId);

                if (productId != null) {
                    ToppingListDAO dao = new ToppingListDAO();
                    boolean isSuccess = dao.insertProductDetail(productId);

                    if (isSuccess) {
                        System.out.println("【デバッグ】自動連番で新しい行を追加しました。商品ID: " + productId);
                        response.sendRedirect("OrderListServlet");
                        return; 
                    } else {
                        System.out.println("【エラー】データベースへのインサートに失敗しました。");
                        response.sendRedirect("itemDetails.jsp");
                        return;
                    }
                } else {
                    System.out.println("【エラー】selectedProductIdがセッションにありません。");
                    response.sendRedirect("itemDetails.jsp");
                    return;
                }
            }
            
            // ====================================================
            // B. トッピング「＋」「－」ボタンの計算処理
            // ====================================================
            ItemDetailsLogic logic = new ItemDetailsLogic();
            
            if (checkAction.startsWith("plus_")) {
                int index = Integer.parseInt(checkAction.substring(5));
                logic.calcToppingQuantity(toppingList, index, "plus");
                
            } else if (checkAction.startsWith("minus_")) {
                int index = Integer.parseInt(checkAction.substring(6));
                logic.calcToppingQuantity(toppingList, index, "minus");
            }

            // トッピング計算後の新しい小計を計算する
            if (productPrice != null && toppingList != null) {
                session.setAttribute("subTotal", logic.calcSubTotal(productPrice, toppingList));
            }
        }

        // 計算が終わった後は詳細画面のJSPへ戻す
        request.getRequestDispatcher("/WEB-INF/jsp/itemDetails.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        // メニュー画面から渡ってきたカテゴリ名や各種情報を一時取得
        String category = request.getParameter("productCategory");
        
        if (category == null) {
            category = (String) session.getAttribute("savedCategory");
        }
        
        // ★【重要】最初の表示のときに、データベースからトッピング一覧をロードしてセッションに入れる！
        if (category != null) {
            ToppingListDAO dao = new ToppingListDAO();
            List<ItemDetailsInfo> toppingList = dao.findToppingList(category.trim());
            session.setAttribute("toppingList", toppingList);
        }
        
        // JSPのフォルダ位置に合わせてフォワード
        request.getRequestDispatcher("/WEB-INF/jsp/itemDetails.jsp").forward(request, response);
    }
}