package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CheckOutDAO {
	private final String JDBC_URL = "jdbc:mysql://localhost:3306/order_management";
	private final String DB_USER = "order";
	private final String DB_PASS = "1234";
	
	// order_detailsの合計フラグを更新
	public void updateByOrderDetails(String tableNumber) throws SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			String sql =
					"UPDATE order_details SET accounting_flag = 1 "
					+ "WHERE session_id = ? AND accounting_flag = 0";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, tableNumber);
			pStmt.executeUpdate();
		}
	}
	
	// table_sessionsのステータスと客数を更新
	public void updateByTableSession(String tableNumber) throws SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			String sql =
					"UPDATE table_sessions SET session_status = 'inactive', guest_count = 0, end_time = NOW() "
					+ "WHERE session_id = ? AND session_status = 'active'";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, tableNumber);
			pStmt.executeUpdate();
		}
	}
	
	// table_masterのステータスと更新日時を更新
	public void updateByTableMaster(String tableNumber) throws SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
			String sql =
					"UPDATE table_master "
					+ "SET table_status = 'inactive', updated_at = CURRENT_TIMESTAMP "
					+ "WHERE table_id = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, tableNumber);
			pStmt.executeUpdate();
		}
	}
}
