package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CheckOutDAO {
    private final String JDBC_URL = "jdbc:mysql://localhost:3306/order_management";
    private final String DB_USER = "order";
    private final String DB_PASS = "1234";

    // データベース接続を取得するメソッド
    private Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("JDBCドライバを読み込めませんでした");
        }
        return DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
    }

    public void executeCheckout(String tableNumber) throws SQLException {
        Connection conn = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);

            // order_detailsの会計フラグを更新
            updateOrderDetails(conn, tableNumber);

            // 現在のセッションを closed にする
            closeCurrentSession(conn, tableNumber);

            // 次回用の新しいセッション（url_token）を作成して挿入
            createNewSession(conn, tableNumber);

            // table_masterのステータスを更新
            updateTableMaster(conn, tableNumber);

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (conn != null) conn.close();
        }
    }

    private void updateOrderDetails(Connection conn, String tableNumber) throws SQLException {
    	System.out.println("会計フラグを立てます");
        String sql = 
        		"UPDATE order_details "
        		+ "SET accounting_flag = 1 "
        		+ "WHERE session_id = ? "
        		+ "AND accounting_flag = 0";
        
        try (PreparedStatement pStmt = conn.prepareStatement(sql)) {
            pStmt.setString(1, tableNumber);
            pStmt.executeUpdate();
        }
    }

    private void closeCurrentSession(Connection conn, String tableNumber) throws SQLException {
    	System.out.println("今のテーブルのセッションを終了します");
        String sql = 
        		"UPDATE table_sessions "
        		+ "SET session_status = 'closed', end_time = NOW() "
        		+ "WHERE session_id = ? "
        		+ "AND session_status = 'active'";
        
        try (PreparedStatement pStmt = conn.prepareStatement(sql)) {
            pStmt.setString(1, tableNumber);
            pStmt.executeUpdate();
        }
    }

    private void createNewSession(Connection conn, String tableNumber) throws SQLException {
    	System.out.println("新しいセッションURLを作成します");
        String sql = 
        		"INSERT INTO table_sessions (table_id, session_status, url_token, guest_count) "
        		+ "VALUES (?, 'inactive', CONCAT(UUID(), '-', SUBSTRING(MD5(RAND()), 1, 8)), 0);";
        
        try (PreparedStatement pStmt = conn.prepareStatement(sql)) {
            pStmt.setString(1, tableNumber);
            pStmt.executeUpdate();
        }
    }

    private void updateTableMaster(Connection conn, String tableNumber) throws SQLException {
    	System.out.println("table_masterの卓番の状態を更新します");
        String sql =
        		"UPDATE table_master "
        		+ "SET table_status = 'inactive', updated_at = CURRENT_TIMESTAMP "
        		+ "WHERE table_id = ?";
        
        try (PreparedStatement pStmt = conn.prepareStatement(sql)) {
            pStmt.setString(1, tableNumber);
            pStmt.executeUpdate();
        }
    }
}