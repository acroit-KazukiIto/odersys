package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ProductInfo;

@WebServlet("/ShowMenuServlet")
public class ShowMenuServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // 卓番を取得
        String tableNumber =
                request.getParameter("tableNumber");

        // 商品リストを作成
        List<ProductInfo> productList =
                new ArrayList<>();

    }

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // 商品の情報を取得
        String productName =
                request.getParameter("productName");

        String productPrice =
                request.getParameter("productPrice");

        // request保存
        request.setAttribute(
                "productName",
                productName);

        request.setAttribute(
                "productPrice",
                productPrice);

        // ItemDetailServletに送るもの
        RequestDispatcher dispatcher =
                request.getRequestDispatcher(
                        "ItemDetailServlet");

        dispatcher.forward(request, response);
    }
}