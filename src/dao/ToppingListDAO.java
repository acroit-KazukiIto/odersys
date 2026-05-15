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
    
    public List<ItemDetailsInfo> findToppingTable() {
        List<ItemDetailsInfo> toppingList = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
                String sql = "SELECT topping_id, topping_name, topping_price, topping_stock FROM topping";
                PreparedStatement pStmt = conn.prepareStatement(sql);
                ResultSet rs = pStmt.executeQuery();
                while (rs.next()) {
                    ItemDetailsInfo topping = new ItemDetailsInfo();
                    topping.setToppingId(rs.getInt("topping_id"));
                    topping.setToppingName(rs.getString("topping_name"));
                    topping.setToppingPrice(rs.getInt("topping_price"));
                    topping.setToppingStock(rs.getInt("topping_stock"));
                    topping.setToppingQuantity(0);
                    toppingList.add(topping);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return toppingList;
    }
}