package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.OrderHistoryInfo;

public class OrderHistoryDAO {
    private final String JDBC_URL = "jdbc:mysql://localhost:3306/order_management";
    private final String DB_USER = "order";
    private final String DB_PASS = "1234";

    public List<OrderHistoryInfo> findOrderDetails(String sessionId) {
        List<OrderHistoryInfo> list = new ArrayList<>();
        
        String sql = 
        		"SELECT od.order_id, p.product_name, t.topping_name, od.product_quantity, od.order_price, od.order_flag, mt.topping_quantity "
        		+ "FROM order_details od "
        		+ "JOIN product p ON od.product_id = p.product_id "
        		+ "LEFT JOIN multiple_toppings mt ON od.order_id = mt.order_id "
        		+ "LEFT JOIN topping t ON mt.topping_id = t.topping_id "
        		+ "WHERE od.session_id = ? "
        		+ "ORDER BY od.order_id ASC";
        
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
        	throw new IllegalStateException("JDBCドライバを読み込めませんでした");
        }
        
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             PreparedStatement pStmt = conn.prepareStatement(sql)) {
            
            pStmt.setString(1, sessionId);
            ResultSet rs = pStmt.executeQuery();
            
            while (rs.next()) {
                OrderHistoryInfo info = new OrderHistoryInfo();
                info.setOrderId(rs.getInt("order_id"));
                info.setProductName(rs.getString("product_name"));
                info.setToppingName(rs.getString("topping_name"));
                info.setToppingQuantity(rs.getInt("topping_quantity"));
                info.setOrderQuantity(rs.getInt("product_quantity"));
                info.setSubTotal(rs.getInt("order_price"));
                info.setOrderFlag(rs.getInt("order_flag"));
                list.add(info);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 会計状態を「会計完了」に更新する
    public void updateAccountingFlag(String sessionId) {
        String sql = "UPDATE order_details SET accounting_flag = 1 WHERE session_id = ?";
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             PreparedStatement pStmt = conn.prepareStatement(sql)) {
            pStmt.setString(1, sessionId);
            pStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}