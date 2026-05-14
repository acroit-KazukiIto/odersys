package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.OrderList;

public class OrderListDao {
	//DB接続情報
	private final String JDBC_URL = "jdbc:mysql://localhost:3306/order_management";
	private final String DB_USER = "order";
	private final String DB_PASS = "1234";
	
	public List<OrderList> findAllProductTopping() throws SQLException {
		List<OrderList> orderList = new ArrayList<>();
		
		//JDBCドライバを読み込む
		try {
			Class.forName("mysql-connector-j-9.3.0");
		}catch(ClassNotFoundException e){
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		
		//DB接続
		try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){
			
			//product_toppingの全件取得のsql
			String sql = "SELECT * FROM product_topping";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			
			//sqlの実行と結果の取得
			ResultSet rs = pStmt.executeQuery();
			
			//レコードの取得情報をorderインスタンスに追加しorderListに格納
			while(rs.next()) {
				OrderList order = new OrderList();
				order.setProductToppingId("product_topping_id");
				order.setProductId("product_id");
				order.setToppingId("topping_id");
				orderList.add(order);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		
		
		
		
		return orderList;
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
