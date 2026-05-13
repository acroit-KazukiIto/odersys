package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.ProductInfo;

public class ShowMenuDAO {

    // 接続情報
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/order_management";
    private static final String DB_USER = "order";
    private static final String DB_PASS = "1234";

    // データベースからもらう
    private Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
    }

    // データベースから商品一覧の取得する//
    
    public List<ProductInfo> findProductTable() {
        List<ProductInfo> list = new ArrayList<>();
        String sql = "SELECT * FROM product";

        try (Connection conn = getConnection(); // 内部メソッド
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //データベースの table_sessions の guestCount を更新する//
    public void updateGuestCount(int guestCount) {
        String sql = "UPDATE table_sessions SET guest_count = ? WHERE session_id = 1";

        try (Connection conn = getConnection(); // 内部メソッド
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, guestCount);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}