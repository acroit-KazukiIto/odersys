package dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.ItemDetailsInfo;
public class ToppingListDAO {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/order_management";
    private static final String DB_USER = "order";
    private static final String DB_PASS = "1234";

    // product_details INSERT
    public boolean insertProductDetail(int productId) {
        String sql = "INSERT INTO product_details (product_id) VALUES (?)";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
                 PreparedStatement pStmt = conn.prepareStatement(sql)) {
                pStmt.setInt(1, productId);
                int rowsInserted = pStmt.executeUpdate();
                return rowsInserted > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    // 最新のorder_id取得
    public int getLastOrderId() {
        int orderId = 0;
        String sql = "SELECT MAX(order_id) AS order_id FROM product_details";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
                 PreparedStatement pStmt = conn.prepareStatement(sql);
                 ResultSet rs = pStmt.executeQuery()) {
                if (rs.next()) {
                    orderId = rs.getInt("order_id");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderId;
    }
    // order_details INSERT
    public boolean insertOrderDetail(int orderId, int productQuantity, int orderPrice,
            int sessionId, int orderTime, int orderFlag,
            int accountingFlag, int productId, int toppingId) {
        String sql = "INSERT INTO order_details (order_id,product_quantity,order_price,"
                + "session_id,order_time,order_flag,accounting_flag,"
                + "product_id,topping_id) VALUES (?,?,?,?,?,?,?,?,?)";
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
                 PreparedStatement pStmt = conn.prepareStatement(sql)) {
                pStmt.setInt(1, orderId);
                pStmt.setInt(2, 1);
                pStmt.setInt(3, orderPrice);
                pStmt.setInt(4, 1);
                pStmt.setTimestamp(5,
                        new java.sql.Timestamp(System.currentTimeMillis()));
                pStmt.setInt(6, 0);
                pStmt.setInt(7, 0);
                pStmt.setInt(8, productId);
                pStmt.setInt(9, 1);
                int rowsInserted = pStmt.executeUpdate();
                return rowsInserted > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    // multiple_toppings INSERT
    public boolean insertMutipleToppings(int toppingId,int toppingQuantity,int orderId) {
        String sql = "INSERT INTO multiple_toppings (topping_id,topping_quantity,order_id) VALUES (?,?,?)";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
                 PreparedStatement pStmt = conn.prepareStatement(sql)) {

                pStmt.setInt(1, toppingId);
                pStmt.setInt(2, toppingQuantity);
                pStmt.setInt(3, orderId);
                int rowsInserted = pStmt.executeUpdate();
                return rowsInserted > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // トッピング一
    public List<ItemDetailsInfo> findToppingList(String categoryName) {

        List<ItemDetailsInfo> toppingList = new ArrayList<>();

        String sql = "SELECT topping_id, topping_name, topping_price, topping_stock FROM topping";

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
                 PreparedStatement pStmt = conn.prepareStatement(sql);
                 ResultSet rs = pStmt.executeQuery()) {

                if ("お好み焼き".equals(categoryName)
                        || "もんじゃ焼き".equals(categoryName)
                        || "鉄板焼".equals(categoryName)
                        || "鉄板焼き".equals(categoryName)) {

                    while (rs.next()) {

                        ItemDetailsInfo topping = new ItemDetailsInfo();

                        topping.setToppingId(rs.getInt("topping_id"));
                        topping.setToppingName(rs.getString("topping_name"));
                        topping.setToppingPrice(rs.getInt("topping_price"));
                        topping.setToppingStock(rs.getInt("topping_stock"));
                        topping.setToppingQuantity(0);
                        topping.setCategory(categoryName);

                        toppingList.add(topping);
                    }
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return toppingList;
    }
}