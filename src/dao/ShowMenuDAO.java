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

    private Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
    }

    // 引数にcategoryを追加し、SQLのWHERE句で絞り込むように変更
    public List<ProductInfo> findProductTable(String category) {
        List<ProductInfo> list = new ArrayList<>();
        String sql = "SELECT * FROM product WHERE category_name = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, category);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ProductInfo p = new ProductInfo();
                    p.setProductId(rs.getInt("product_id"));
                    p.setProductName(rs.getString("product_name"));
                    p.setCategoryName(rs.getString("category_name"));
                    p.setProductPrice(rs.getInt("product_price"));
                    p.setProductStock(rs.getInt("product_stock"));
                    p.setProductDisplayFlag(rs.getInt("product_display_flag"));
                    list.add(p);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void updateGuestCount(int guestCount) {
        String sql = "UPDATE table_sessions SET guest_count = ? WHERE session_id = 1";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, guestCount);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}