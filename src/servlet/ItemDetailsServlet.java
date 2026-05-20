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

    /**
     * 1. 詳細画面でボタン（＋、－、追加、メニュー）が押されたときの処理
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        
        // JSPの name="Button" に合わせて正確に取得
        String checkAction = request.getParameter("Button");
        List<ItemDetailsInfo> toppingList = (List<ItemDetailsInfo>) session.getAttribute("toppingList");
        Integer productPrice = (Integer) session.getAttribute("selectedPPrice");
        
        System.out.println("【デバッグ:doPost】クリックされたボタンの値: " + checkAction);

        if (checkAction != null) {

            // 「追加」ボタンが押されたとき（DBへ注文詳細を登録） ---
            if ("追加".equals(checkAction)) {
                Integer productId = (Integer) session.getAttribute("selectedProductId");
                System.out.println("【デバッグ:doPost】インサートを試みるproductId: " + productId);

                if (productId != null) {
                    ToppingListDAO dao = new ToppingListDAO();
                    boolean isSuccess = dao.insertProductDetail(productId);

                    if (isSuccess) {
                        System.out.println("【デバッグ:doPost】DB登録成功！OrderListServletへ遷移します。");
                        response.sendRedirect("OrderListServlet");
                        return; 
                    } else {
                        System.out.println("【エラー:doPost】DBインサートに失敗しました。");
                        response.sendRedirect("ItemDetailsServlet");
                        return;
                    }
                } else {
                    System.out.println("【エラー:doPost】selectedProductIdがセッションにありませんでした。");
                    response.sendRedirect("ItemDetailsServlet");
                    return;
                }
            }
            
            // 「メニュー」ボタンが押されたとき
            if ("メニュー".equals(checkAction)) {
                response.sendRedirect("ShowMenuServlet");
                return;
            }

            // トッピングの「＋」「－」ボタンが押されたとき ---
            ItemDetailsLogic logic = new ItemDetailsLogic();
            if (checkAction.startsWith("+")) {
                int index = Integer.parseInt(checkAction.substring(1));
                logic.calcToppingQuantity(toppingList, index, "plus");
            } else if (checkAction.startsWith("-")) {
                int index = Integer.parseInt(checkAction.substring(1));
                logic.calcToppingQuantity(toppingList, index, "minus");
            }

            // 計算後の新しい小計を計算して更新
            if (productPrice != null && toppingList != null) {
                session.setAttribute("subTotal", logic.calcSubTotal(productPrice, toppingList));
            }
        }

        // POST処理が終わったらリダイレクトでdoGetへ飛ばす
        response.sendRedirect("ItemDetailsServlet");
    }

    /**
     * 2. 詳細画面を表示する（最初にメニューから来た時 ＆ プラスマイナス後のリダイレクト時）
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        // メニュー画面から送られてきたパラメータを取得
        String reqId = request.getParameter("productId");
        String reqName = request.getParameter("productName");
        String reqPrice = request.getParameter("productPrice");
        String category = request.getParameter("productCategory");

        System.out.println("===== 【デバッグ:doGet】開始 =====");
        System.out.println("URLパラメータ [productId]: " + reqId);
        System.out.println("URLパラメータ [productName]: " + reqName);
        System.out.println("URLパラメータ [productPrice]: " + reqPrice);
        System.out.println("URLパラメータ [productCategory]: " + category);

        // メニュー画面から新しくデータが送られてきた場合のみ、セッションの情報をカチッと更新する
        if (reqId != null && reqName != null && reqPrice != null) {
            System.out.println("👉 メニュー画面からの遷移を検知。セッション情報を新規登録・上書きします。");
            int productId = Integer.parseInt(reqId);
            int productPrice = Integer.parseInt(reqPrice);

            session.setAttribute("selectedProductId", productId);
            session.setAttribute("selectedPName", reqName);
            session.setAttribute("selectedPPrice", productPrice);
            session.setAttribute("subTotal", productPrice);
        } else {
            System.out.println("👉 リダイレクト、または画面更新を検知。既存のセッション数値を維持します。");
        }

        // 現在セッションに格納されている値を最終確認
        System.out.println("現在のセッション [selectedPName]: " + session.getAttribute("selectedPName"));
        System.out.println("現在のセッション [selectedPPrice]: " + session.getAttribute("selectedPPrice"));
        System.out.println("現在のセッション [selectedProductId]: " + session.getAttribute("selectedProductId"));
        System.out.println("==================================");

        // カテゴリ情報の保存とトッピング一覧の取得処理
        if (category == null) {
            category = (String) session.getAttribute("savedCategory");
        } else {
            session.setAttribute("savedCategory", category.trim());
        }
        
        if (category != null) {
            ToppingListDAO dao = new ToppingListDAO();
            List<ItemDetailsInfo> toppingList = dao.findToppingList(category.trim());
            // セッションに既存のトッピングリストがない場合のみ新規セット（数量リセット防止）
            if (session.getAttribute("toppingList") == null || reqId != null) {
                session.setAttribute("toppingList", toppingList);
            }
        }
        
        request.getRequestDispatcher("WEB-INF/jsp/itemDetails.jsp").forward(request, response);
    }
}