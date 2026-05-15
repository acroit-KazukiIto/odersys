package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderCompleteDAO {
	//DB接続情報
	private final String JDBC_URL = "jdbc:mysql://localhost:3306/order_management";
	private final String DB_USER = "order";
	private final String DB_PASS = "1234";


	public void updateProduct() throws SQLException{
		//JDBCドライバを読み込む
		try {
			Class.forName("mysql-connector-j-9.3.0");
		}catch(ClassNotFoundException e){
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){
			String sql = ""; 
			PreparedStatement pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();

		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateTopping() throws SQLException{
		//JDBCドライバを読み込む
		try {
			Class.forName("mysql-connector-j-9.3.0");
		}catch(ClassNotFoundException e){
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){
			String sql = "";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();

		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateOrderDetails() throws SQLException {
		//JDBCドライバを読み込む
		try {
			Class.forName("mysql-connector-j-9.3.0");
		}catch(ClassNotFoundException e){
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		//DB接続
		try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){

			//order_details更新のsql
			String sql = "UPDATE ordertDetails SET product_quantity = productQuantity"; 
			PreparedStatement pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();

		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
