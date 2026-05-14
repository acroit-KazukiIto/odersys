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
	
	// セッションIDに基づいて注文履歴を取得する
	public List<OrderHistoryInfo> findOrderDetails(int tableId) {
		List<OrderHistoryInfo> list = new ArrayList<>();
		// order_detailsからorder_flag, order_id, order_price等を取得
		String sql = 
				"SELECT order_id, order_flag, product_name, topping_name, topping_count, quantity, subtotal"  + "FROM order_details WHERE table_id = ? ORDER BY order_id ASC";
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, tableId);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					OrderHistoryInfo orderHistoryInfo = new OrderHistoryInfo();
					orderHistoryInfo.setOrderId(rs.getInt("orderId"));
					orderHistoryInfo.setOrderFlag(rs.getInt("orderFlag"));
					orderHistoryInfo.setProductName(rs.getString("productName"));
					orderHistoryInfo.setToppingName(rs.getString("toppingName"));
					orderHistoryInfo.setToppingQuantity(rs.getInt("toppingCount"));
					orderHistoryInfo.setOrderQuantity(rs.getInt("quantity"));
					orderHistoryInfo.setSubTotal(rs.getInt("subTotal"));
					list.add(orderHistoryInfo);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
