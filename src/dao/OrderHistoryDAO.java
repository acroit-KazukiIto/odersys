package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.OrderHistoryInfo;

public class OrderHistoryDAO {
    private final String JDBC_URL = "jdbc:mysql://localhost:3306/order_management";
    private final String DB_USER = "order";
    private final String DB_PASS = "1234";

    public List<OrderHistoryInfo> findOrderDetails() throws SQLException {
    	System.out.println("DAO　◯");
        List<OrderHistoryInfo> list = new ArrayList<>();
        
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
        	throw new IllegalStateException("JDBCドライバを読み込めませんでした");
        }
        
        String sql = 
        		"SELECT od.order_id, od.product_quantity, od.order_price, od.session_id, od.order_flag, "
        		+ "p.product_name, p.product_stock, "
        		+ "t.topping_name, t.topping_stock, "
        		+ "mt.topping_quantity "
        		+ "FROM order_details AS od "
        		+ "LEFT JOIN product_details AS pd "
        		+ "ON od.order_id = pd.order_id "
        		+ "LEFT JOIN product AS p "
        		+ "ON pd.product_id = p.product_id "
        		+ "LEFT JOIN multiple_toppings AS mt "
        		+ "ON od.order_id = mt.order_id "
        		+ "LEFT JOIN topping AS t "
        		+ "ON mt.topping_id = t.topping_id "
        		+ "WHERE od.order_flag = 1 "
        		+ "ORDER BY od.order_id ASC";
        
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             PreparedStatement pStmt = conn.prepareStatement(sql)) {
        	System.out.println("tryに入りました");
        	
        	String logSql = "SELECT COUNT(*) AS total FROM order_details";
        	try (PreparedStatement pStmtCheck = conn.prepareStatement(logSql)) {
                ResultSet rsCheck = pStmtCheck.executeQuery();
                if (rsCheck.next()) {
                    int total = rsCheck.getInt("total");
                    System.out.println("【デバッグ】order_detailsテーブルの全データ件数: " + total);
                }
            }
        	
            //pStmt.setString(1, sessionId);
            ResultSet rs = pStmt.executeQuery();
            
            if (!rs.isBeforeFirst()) {
                System.out.println("【警告】ResultSetは空です（レコードが1件もありません）");
            }
            
            while (rs.next()) {
            	System.out.println("while文に入りました");
            	
            	int orderId = rs.getInt("order_id");
				int subTotal = rs.getInt("order_price");
				String productName = rs.getString("product_name");
				String toppingName = rs.getString("topping_name");
				int toppingQuantity = rs.getInt("topping_quantity");
				int orderQuantity = rs.getInt("product_quantity");
				int orderFlag = rs.getInt("order_flag");
				OrderHistoryInfo info = new OrderHistoryInfo
						(orderId, orderFlag, productName, toppingName, toppingQuantity, orderQuantity, subTotal);
                
                info.setOrderId(orderId);
				info.setSubTotal(subTotal);
				info.setProductName(productName);
				info.setToppingName(toppingName);
				info.setOrderQuantity(orderQuantity);
				info.setToppingQuantity(toppingQuantity);
				info.setOrderFlag(orderFlag);
				list.add(info);

				String pname = info.getProductName();
				System.out.println("DAOチェック" + pname);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("✕");
        }
        return list;
    }

    // 会計状態を「会計完了」に更新する
    public void updateAccountingFlag() {
    	try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
        	throw new IllegalStateException("JDBCドライバを読み込めませんでした");
        }
    	
        String sql = "UPDATE order_details SET accounting_flag = 1 WHERE session_id = ?";
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             PreparedStatement pStmt = conn.prepareStatement(sql)) {
            //pStmt.setString(1, sessionId);
            pStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}