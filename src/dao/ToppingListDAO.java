package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.OrderListInfo;

public class ToppingListDAO {

	//DB接続情報
	private final String JDBC_URL = "jdbc:mysql://localhost:3306/order_management";
	private final String DB_USER = "order";
	private final String DB_PASS = "1234";
	
	
	public  List<OrderListInfo> findAllProductTopping() throws SQLException {
		List<OrderListInfo> orderList = new ArrayList<>();

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
				OrderListInfo order = new OrderListInfo();
				order.setProductToppingId("product_topping_id");
				order.setProductId("product_id");
				order.setToppingId("topping_id");
				orderList.add(order);
				System.out.println(order);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return orderList;
	}
}
