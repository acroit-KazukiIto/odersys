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
		System.out.println("ダオにきたお");

		List<OrderListInfo> olList = new ArrayList<>();
		//JDBCドライバを読み込む
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e){
			throw new IllegalStateException("JDBCドライバを読み込めませんでしたあ");
		}

		try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){
			String sql = "SELECT od.order_id, mt.topping_id, od.product_quantity, od.order_price, od.session_id, od.order_flag, p.product_name, p.product_price, p.category_name, p.product_stock, t.topping_name, t.topping_price, t.topping_stock, mt.topping_quantity, (od.product_quantity * od.order_price) AS sub_total  FROM order_details AS od LEFT JOIN product_details AS pd ON od.order_id = pd.order_id LEFT JOIN product AS p ON pd.product_id = p.product_id LEFT JOIN multiple_toppings AS mt ON od.order_id = mt.order_id LEFT JOIN topping AS t ON mt.topping_id = t.topping_id WHERE order_flag = 0";
			
			PreparedStatement pStmt = conn.prepareStatement(sql);
			
			ResultSet rs = pStmt.executeQuery();
		
			while(rs.next()) {
				int orderId = rs.getInt("order_id");
				int toppingId = rs.getInt("topping_id");
				int orderPrice = rs.getInt("order_price");
				String productName = rs.getString("product_name");
				String toppingName = rs.getString("topping_name");
				String categoryName = rs.getString("category_name");
				int productPrice = rs.getInt("product_price");
				int toppingPrice = rs.getInt("topping_price");
				int toppingQuantity = rs.getInt("topping_quantity");
				int productQuantity = rs.getInt("product_quantity");
				int sessionId = rs.getInt("session_id");
				int subTotal = rs.getInt("sub_total");
				
				
				OrderListInfo ol = new OrderListInfo(orderId, toppingId, toppingName, categoryName, productName, orderPrice, productPrice, toppingPrice,
						toppingQuantity, productQuantity, sessionId, subTotal);
				ol.setOrderId(orderId);
				ol.setToppingName(toppingName);
				ol.setProductName(productName);
				ol.setCategoryName(categoryName);
				ol.setOrderPrice(orderPrice);
				ol.setProductPrice(productPrice);
				ol.setToppingPrice(toppingPrice);
				ol.setProductQuantity(productQuantity);
				ol.setToppingQuantity(toppingQuantity);
				ol.setSubTotal(subTotal);
				ol.setToppingId(toppingId);
				
				olList.add(ol);
				

			}
			

		}catch(SQLException e){
			System.out.println("失敗");
			e.printStackTrace();
		}
		return olList;
	}


	public void updateOrderDetails(int n, int oid) throws SQLException {
		//JDBCドライバを読み込む
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e){
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		//DB接続
		try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){

			//order_details更新のsql
			if(n > 0) {
				String sql = "UPDATE order_details SET product_quantity = product_quantity + 1 WHERE order_id = ?"; 
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setInt(1, oid);
				int rs = ps.executeUpdate();
				System.out.println("オーダー増加dao");				
			}else {
				String sql = "UPDATE order_details SET product_quantity = product_quantity - 1 WHERE order_id = ?"; 
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setInt(1, oid);
				int rs = ps.executeUpdate();
				System.out.println("オーダー減少dao");
			}


		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

	public OrderListInfo findAllOrderPrice()throws SQLException{
		OrderListInfo ol2 = null;
		//JDBCドライバを読み込む
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e){
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}
		//DB接続
		try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){
			String sql = "SELECT SUM(od.product_quantity * od.order_price) AS all_order_price FROM order_details AS od LEFT JOIN product_details AS pd ON od.order_id = pd.order_id LEFT JOIN product AS p ON pd.product_id = p.product_id LEFT JOIN multiple_toppings AS mt ON od.order_id = mt.order_id LEFT JOIN topping AS t ON mt.topping_id = t.topping_id WHERE order_flag = 0";
			
			PreparedStatement pStmt = conn.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();
			if(rs.next()) {
				int allOrderPrice = rs.getInt("all_order_price");
				ol2 = new OrderListInfo(allOrderPrice);
				ol2.setAllOrderPrice(allOrderPrice);
			}
			
			



		}catch(SQLException e) {
			e.printStackTrace();
			System.out.println("合計DAO失敗");
		}
		return ol2;
	}


	public OrderListInfo findorderDetailsByorderFlag2(int oId) {
		System.out.println("DAOチェック２");

		OrderListInfo ol = null;
		//JDBCドライバを読み込む
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e){
			throw new IllegalStateException("JDBCドライバを読み込めませんでしたあ");
		}
		try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){
			String sql = "SELECT od.order_id, od.product_quantity, od.order_price, od.session_id, od.order_flag, p.product_name, p.product_price, p.category_name, p.product_stock, t.topping_name, t.topping_price, t.topping_stock, mt.topping_quantity, (od.product_quantity * od.order_price) AS sub_total  FROM order_details AS od LEFT JOIN product_details AS pd ON od.order_id = pd.order_id LEFT JOIN product AS p ON pd.product_id = p.product_id LEFT JOIN multiple_toppings AS mt ON od.order_id = mt.order_id LEFT JOIN topping AS t ON mt.topping_id = t.topping_id WHERE order_flag = 0 AND od.order_id = ?";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, oId);
			ResultSet rs = ps.executeQuery();
		
			if(rs.next()) {
				int orderId = rs.getInt("order_id");
				int toppinId = rs.getInt("topping_id");
				int orderPrice = rs.getInt("order_price");
				String productName = rs.getString("product_name");
				String toppingName = rs.getString("topping_name");
				String categoryName = rs.getString("category_name");
				int productPrice = rs.getInt("product_price");
				int toppingPrice = rs.getInt("topping_price");
				int toppingQuantity = rs.getInt("topping_quantity");
				int productQuantity = rs.getInt("product_quantity");
				int sessionId = rs.getInt("session_id");
				int subTotal = rs.getInt("sub_total");
				
				
				
				ol = new OrderListInfo(orderId,toppinId, toppingName,categoryName, productName, orderPrice, productPrice, toppingPrice,
						toppingQuantity, productQuantity, sessionId, subTotal);
				ol.setOrderId(orderId);
				ol.setToppingName(toppingName);
				ol.setProductName(productName);
				ol.setCategoryName(categoryName);
				ol.setOrderPrice(orderPrice);
				ol.setProductPrice(productPrice);
				ol.setToppingPrice(toppingPrice);
				ol.setProductQuantity(productQuantity);
				ol.setToppingQuantity(toppingQuantity);
				ol.setSubTotal(subTotal);
				int oid = ol.getOrderId();
				System.out.println("合計取得 おーだーID：" + oid);
				

			}
			

		}catch(SQLException e){
			System.out.println("失敗");
			e.printStackTrace();
		}
		return ol;
	}


	public void deteleTopping(int oid) {
		//JDBCドライバを読み込む
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e){
			throw new IllegalStateException("JDBCドライバを読み込めませんでした");
		}

		//DB接続
		try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){
			
	     String sql =
	                "DELETE FROM multiple_toppings " +
	                "WHERE order_id = ?";
	     PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, oid);
			int rs = ps.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void insertTopping(int toppingId,int orderId) {
		String sql =  "INSERT INTO multiple_toppings (" +
                "topping_id, " +
                "topping_quantity, " +
                "order_id" +
                ") VALUES (?, ?, ?)";
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            try (
                Connection conn =
                        DriverManager.getConnection(
                                JDBC_URL,
                                DB_USER,
                                DB_PASS
                        );

                PreparedStatement ps =
                        conn.prepareStatement(sql)
            ) {

                ps.setInt(1, toppingId);

                ps.setInt(2, 1);

                ps.setInt(3, orderId);
                
                int rs = ps.executeUpdate();

            }

        } catch (Exception e) {

            e.printStackTrace();
        }
	}
	public void updateTopping(int n, int oid, int tid) {
		//JDBCドライバを読み込む
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
				}catch(ClassNotFoundException e){
					throw new IllegalStateException("JDBCドライバを読み込めませんでした");
				}

				//DB接続
				try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){
					
					//order_details更新のsql
					if(n > 0) {
						String sql = "UPDATE order_details SET product_quantity = product_quantity + 1 WHERE order_id = ? AND product_id = ?"; 
						PreparedStatement ps = conn.prepareStatement(sql);
						ps.setInt(1, oid);
						ps.setInt(2, tid);
						int rs = ps.executeUpdate();
						System.out.println("オーダー増加dao");				
					}else {
						String sql = "UPDATE order_details SET product_quantity = product_quantity - 1 WHERE order_id = ? AND product_id = ?"; 
						PreparedStatement ps = conn.prepareStatement(sql);
						ps.setInt(1, oid);
						ps.setInt(2, tid);
						int rs = ps.executeUpdate();
						System.out.println("オーダー減少dao");
					}


				}catch(SQLException e) {
					e.printStackTrace();
				}
		
	}

}
