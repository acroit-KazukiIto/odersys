package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.OrderListInfo;

public class OrderListDAO {
	//DB接続情報
	private final String JDBC_URL = "jdbc:mysql://localhost:3306/order_management";
	private final String DB_USER = "order";
	private final String DB_PASS = "1234";


	public List<OrderListInfo> findorderDetailsByorderFlag() throws SQLException {
		List<OrderListInfo> olList = new ArrayList<>();
		System.out.println("ダオにきたお");
		//JDBCドライバを読み込む
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e){
			throw new IllegalStateException("JDBCドライバを読み込めませんでしたあ");
		}

		try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){
			String sql = "SELECT od.order_id, od.product_quantity, od.order_price, od.session_id, od.order_flag, p.product_name, p.product_price, p.product_stock, t.topping_name, t.topping_price, t.topping_stock, mt.topping_quantity FROM order_details AS od LEFT JOIN product_details AS pd ON od.order_id = pd.order_id LEFT JOIN product AS p ON pd.product_id = p.product_id LEFT JOIN multiple_toppings AS mt ON od.order_id = mt.order_id LEFT JOIN topping AS t ON mt.topping_id = t.topping_id";
					
			PreparedStatement pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();
			while(rs.next()) {
				int subTotal = rs.getInt("order_price");
				String productName = rs.getString("product_name");
				String toppingName = rs.getString("topping_name");
				int productPrice = rs.getInt("product_price");
				int toppingPrice = rs.getInt("topping_price");
				int toppingQuantity = rs.getInt("topping_quantity");
				int productQuantity = rs.getInt("product_quantity");
				OrderListInfo olInfo = new OrderListInfo(toppingName, productName, subTotal, productPrice, toppingPrice, toppingQuantity, productQuantity);
				olList.add(olInfo);
				System.out.println("リスト表示" + olList);
				
			}

		}catch(SQLException e){
			e.printStackTrace();
			System.out.println("失敗");
		}
		return olList;
	}

	
	public void updateOrderDetails() throws SQLException {
		//JDBCドライバを読み込む
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
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

	public void insertOrderDetails() throws SQLException {
		//JDBCドライバを読み込む
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
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
			Class.forName("com.mysql.cj.jdbc.Driver");
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
			Class.forName("com.mysql.cj.jdbc.Driver");
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
			Class.forName("com.mysql.cj.jdbc.Driver");
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
