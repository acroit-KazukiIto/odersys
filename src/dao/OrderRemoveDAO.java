package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderRemoveDAO {
	//DB接続情報
		private final String JDBC_URL = "jdbc:mysql://localhost:3306/order_management";
		private final String DB_USER = "order";
		private final String DB_PASS = "1234";
		
		public void deleteOrderDetails(int num) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			}catch(ClassNotFoundException e){
				throw new IllegalStateException("JDBCドライバを読み込めませんでしたあ");
			}
			
			try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){
				System.out.println("だおまできたお");
				String sql = "DELETE FROM order_details WHERE order_id = ?"
						+ "UNION"
						+ "DELETE FROM product_details WHERE order_id = ?"
						+ "UNION"
						+ "DELETE FROM multiple_toppings WHERE order_id = ?";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setInt(1, num);
				ps.setInt(2, num);
				ps.setInt(3, num);
				ps.executeUpdate();
			}catch(SQLException e){
				e.printStackTrace();
				System.out.println("失敗");
			}
		}
}
