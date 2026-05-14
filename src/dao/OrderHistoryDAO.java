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
	
	// order_idを元にorder_flag等の情報を取得
	public List<OrderHistoryInfo> findOrderDetails() {
		List<OrderHistoryInfo> list = new ArrayList<>();
		String sql = "SELECT order_id, order_flag FROM order_details ORDER BY order_id ASC";
		
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			PreparedStatement pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();
			
			while (rs.next()) {
				OrderHistoryInfo orderHistoryInfo = new OrderHistoryInfo();
				orderHistoryInfo.setOrderId(rs.getInt("orderId"));
				orderHistoryInfo.setOrderFlag(rs.getInt("orderFlag"));
				list.add(orderHistoryInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
		return list;
	}
}
