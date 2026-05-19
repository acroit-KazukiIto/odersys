package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.ProductInfo;

public class ShowMenuDAO {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/order_management";
    private static final String DB_USER = "order";
    private static final String DB_PASS = "1234";

    public void updateGuestCount(int guestCount) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
                String sql = "UPDATE table_sessions SET guestCount = ?";
                PreparedStatement pStmt = conn.prepareStatement(sql);
                pStmt.setInt(1, guestCount);
                pStmt.executeUpdate();
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    /**
     * product_id, product_name, product_price, category_nameをjoinを使って取得
     */
    public List<ProductInfo> findProductTable() {
        List<ProductInfo> productList = new ArrayList<>();
        String sql = "SELECT p.product_id, p.product_name, p.category_name, p.product_price, p.product_stock, p.product_display_flag " +
                     "FROM product p " +
                     "LEFT JOIN product_details pd ON p.product_id = pd.product_id " + // JOINを実行
                     "GROUP BY p.product_id"; 

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
                PreparedStatement pStmt = conn.prepareStatement(sql);
                ResultSet rs = pStmt.executeQuery();

                while (rs.next()) {
                    ProductInfo product = new ProductInfo(
                        rs.getInt("product_id"), 
                        rs.getString("product_name"),
                        rs.getString("category_name"),
                        rs.getInt("product_price"),
                        rs.getInt("product_stock"),
                        rs.getInt("product_display_flag")
                    );
                    productList.add(product);
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return productList;
    }
}