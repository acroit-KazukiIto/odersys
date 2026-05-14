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
		String sql = "UPDATE order_details SET accounting_flag = 1 WHERE table_number = ? AND accounting_flag = 0";
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
				PreparedStatement pStmt = conn.prepareStatement(sql)) {
			pStmt.setString(1, tableNumber);
			pStmt.executeUpdate();
		}
	}
	
	// table_sessionsのステータスと客数を更新
	public void updateByTableSession(String tableNumber) throws SQLException {
		String sql = "UPDATE table_sessions SET session_status = 'inactive', guest_count = 0 WHERE table_number = ? AND session_status = 'OPEN'";
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
				PreparedStatement pStmt = conn.prepareStatement(sql)) {
			pStmt.setString(1, tableNumber);
			pStmt.executeUpdate();
		}
	}
	
	// table_masterのステータスと更新日時を更新
	public void updateByTableMaster(String tableNumber) throws SQLException {
		String sql = "UPDATE table_master SET table_status = 'inactive', updated_at = CURRENT_TIMESTAMP WHERE table_number = ?";
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
				PreparedStatement pStmt = conn.prepareStatement(sql)) {
			pStmt.setString(1, tableNumber);
			pStmt.executeUpdate();
		}
	}
}
