package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.OrderListInfo;

public class OrderListDAO {
	//DB接続情報
	private final String JDBC_URL = "jdbc:mysql://localhost:3306/order_management";
	private final String DB_USER = "order";
	private final String DB_PASS = "1234";


	public void findorderDetailsByorderFlag() throws SQLException{
		//JDBCドライバを読み込む
		try {
			Class.forName("mysql-connector-j-9.3.0");
		}catch(ClassNotFoundException e){
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){
			String sql = "SELECT order_details.product_quantity, product_details.product_id"
					+ "FROM order_details WHERE order_flag = 0"
					+ "INNER JOIN product_details"
					+ "ON order_details.order_id = product_details.order_id"
					+ "UNION"
					+ "SELECT product_details.order_id, product.product_name, product.product_price"
					+ "FROM product_details WHERE order_id"
					+ "INNER JOIN product"
					+ "ON product_details.product_id = product.product_id"
					+ "UNION"
					+ "SELECT order_details.order_price, multiple_toppings.topping_id, multiple_toppings.topping_quantity"
					+ "FROM order_details "
					+ "ON order_details.order_id = multiple_toppings.order_id"
					+ "UNION"
					+ "SELECT multiple_toppings.topping_id, topping.topping_name, topping.topping_price"
					+ "FROM multiple_toppings"
					+ "INNER JOIN topping"
					+ "ON multiple_topping.topping_id = topping.topping_name";
					
			PreparedStatement pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();
			while(rs.next()) {
				OrderListInfo order = new OrderListInfo();
				order.setSubTotal(Integer.parseInt("order_price"));
				order.setProductName("product_name");
				order.setToppingName("topping_name");
				order.setProductPrice(Integer.parseInt("product_price"));
				order.setToppingPrice(Integer.parseInt("topping_price"));
				order.setToppingQuantity(Integer.parseInt("topping_quantity"));
				order.setOrderQuantity(Integer.parseInt("product_quantity"));
				
			}

		}catch(SQLException e){
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

	public void insertOrderDetails() throws SQLException{
		//JDBCドライバを読み込む
		try {
			Class.forName("mysql-connector-j-9.3.0");
		}catch(ClassNotFoundException e){
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		//DB接続
		try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){

			//order_details行挿入のsql
			String sql = ""; 
			PreparedStatement pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();

		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertProductDetails() throws SQLException{
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

	public void insertMultipleOrderDetails() throws SQLException{
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
	
	public void findAllOrderPrice() throws SQLException{
		
		OrderListInfo order = new OrderListInfo();
		//JDBCドライバを読み込む
		try {
			Class.forName("mysql-connector-j-9.3.0");
		}catch(ClassNotFoundException e){
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}


		try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){
			String sql = "SELECT SUM(order_price) AS aop FROM order_details WHERE order_flag = 0"; 
			PreparedStatement pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();
			
		    int aop = rs.getInt("aop");
		    order.setAllOrderPrice(aop);

		}catch(SQLException e) {
			e.printStackTrace();
		}
	}


}
