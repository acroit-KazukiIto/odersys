package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import model.ItemDetailsInfo;

public class ToppingListDAO {

    private static final String JDBC_URL =
            "jdbc:mysql://localhost:3306/order_management";
    private static final String DB_USER =
            "order";
    private static final String DB_PASS =
            "1234";
    
	public List<ItemDetailsInfo> findorderDetails() throws SQLException {
		System.out.println("ダオにきたお");

		List<ItemDetailsInfo> idList = new ArrayList<>();
		//JDBCドライバを読み込む
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e){
			throw new IllegalStateException("JDBCドライバを読み込めませんでしたあ");
		}

		try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){
			String sql = "SELECT od.order_id, od.product_quantity, od.order_price, od.session_id, od.order_flag, p.product_name, p.product_price, p.product_stock, t.topping_name, t.topping_price, t.topping_stock, mt.topping_quantity, (od.product_quantity * od.order_price) AS sub_total  FROM order_details AS od LEFT JOIN product_details AS pd ON od.order_id = pd.order_id LEFT JOIN product AS p ON pd.product_id = p.product_id LEFT JOIN multiple_toppings AS mt ON od.order_id = mt.order_id LEFT JOIN topping AS t ON mt.topping_id = t.topping_id WHERE order_flag = 0";
			
			PreparedStatement pStmt = conn.prepareStatement(sql);
			
			ResultSet rs = pStmt.executeQuery();
		
			while(rs.next()) {
				int orderId = rs.getInt("order_id");
				int orderPrice = rs.getInt("order_price");
				String productName = rs.getString("product_name");
				String toppingName = rs.getString("topping_name");
				int productPrice = rs.getInt("product_price");
				int toppingPrice = rs.getInt("topping_price");
				int toppingQuantity = rs.getInt("topping_quantity");
				int productQuantity = rs.getInt("product_quantity");
				int sessionId = rs.getInt("session_id");
				int subTotal = rs.getInt("sub_total");
				
				ItemDetailsInfo id = new ItemDetailsInfo(orderId, toppingName, productName, orderPrice, productPrice, toppingPrice,
						toppingQuantity, productQuantity, sessionId, subTotal);
				id.setOrderId(orderId);
				id.setToppingName(toppingName);
				id.setProductName(productName);
				id.setOrderPrice(orderPrice);
				id.setProductPrice(productPrice);
				id.setToppingPrice(toppingPrice);
				id.setToppingQuantity(toppingQuantity);
				
				idList.add(id);
				

			}
			

		}catch(SQLException e){
			System.out.println("失敗");
			e.printStackTrace();
		}
		return idList;
	}
	
    // product_details insert
    public boolean insertProductDetail(int productId) {

        String sql =
                "INSERT INTO product_details (product_id) VALUES (?)";
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

                ps.setInt(1, productId);

                return ps.executeUpdate() > 0;
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    // 最新 order_id
    public int getLastOrderId() {

        int orderId = 0;

        String sql =
                "SELECT MAX(order_id) AS order_id " +
                "FROM product_details";

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
                        conn.prepareStatement(sql);

                ResultSet rs =
                        ps.executeQuery()
            ) {

                if (rs.next()) {

                    orderId =
                            rs.getInt("order_id");
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return orderId;
    }

    // order_details insert
    public boolean insertOrderDetail(
            int orderId,
            int productQuantity,
            int orderPrice,
            int sessionId,
            int orderTime,
            int orderFlag,
            int accountingFlag,
            int productId,
            int toppingId) {

        String sql =
                "INSERT INTO order_details (" +
                "order_id, " +
                "product_quantity, " +
                "order_price, " +
                "session_id, " +
                "order_time, " +
                "order_flag, " +
                "accounting_flag, " +
                "product_id, " +
                "topping_id" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

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

                ps.setInt(1, orderId);

                ps.setInt(2, productQuantity);

                ps.setInt(3, orderPrice);

                ps.setInt(4, sessionId);

                ps.setTimestamp(
                        5,
                        new Timestamp(
                                System.currentTimeMillis()
                        )
                );

                ps.setInt(6, orderFlag);

                ps.setInt(7, accountingFlag);

                ps.setInt(8, productId);

                ps.setInt(9, toppingId);

                return ps.executeUpdate() > 0;
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    // order_details update
    public boolean updateOrderDetail(
            int orderId,
            int productId,
            int productQuantity,
            int orderPrice,
            int sessionId) {

        String sql =
                "UPDATE order_details " +
                "SET product_quantity = ?, " +
                "order_price = ?, " +
                "session_id = ? " +
                "WHERE order_id = ? " +
                "AND product_id = ?";

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

                ps.setInt(1, productQuantity);

                ps.setInt(2, orderPrice);

                ps.setInt(3, sessionId);

                ps.setInt(4, orderId);

                ps.setInt(5, productId);

                return ps.executeUpdate() > 0;
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    // multiple_toppings delete
    public boolean deleteMultipleToppings(
            int orderId) {

        String sql =
                "DELETE FROM multiple_toppings " +
                "WHERE order_id = ?";

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

                ps.setInt(1, orderId);

                return ps.executeUpdate() > 0;
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    // multiple_toppings insert
    public boolean insertMutipleToppings(
            int toppingId,
            int toppingQuantity,
            int orderId) {

        String sql =
                "INSERT INTO multiple_toppings (" +
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

                ps.setInt(2, toppingQuantity);

                ps.setInt(3, orderId);

                return ps.executeUpdate() > 0;
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    // topping list
    public List<ItemDetailsInfo> findToppingList(
            String categoryName) {

        List<ItemDetailsInfo> list =
                new ArrayList<>();

        String sql =
                "SELECT topping_id, " +
                "topping_name, " +
                "topping_price, " +
                "topping_stock " +
                "FROM topping";

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
                        conn.prepareStatement(sql);

                ResultSet rs =
                        ps.executeQuery()
            ) {

                while (rs.next()) {

                    ItemDetailsInfo t =
                            new ItemDetailsInfo();

                    t.setToppingId(
                            rs.getInt("topping_id")
                    );

                    t.setToppingName(
                            rs.getString("topping_name")
                    );

                    t.setToppingPrice(
                            rs.getInt("topping_price")
                    );

                    t.setToppingStock(
                            rs.getInt("topping_stock")
                    );

                    t.setToppingQuantity(0);

                    list.add(t);
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return list;
    }
}