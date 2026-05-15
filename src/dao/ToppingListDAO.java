package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.ItemDetailsInfo;

public class ToppingListDAO {
    // DB接続情報
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/order_management";
    private static final String DB_USER = "order";
    private static final String DB_PASS = "1234";

    /**
     * トッピングテーブル(topping)から全件取得する
     */
    public List<ItemDetailsInfo> findToppingTable() {
        List<ItemDetailsInfo> toppingList = new ArrayList<>();
        
        // JDBCドライバの読み込み
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return toppingList;
        }

        // DB接続とSQL実行
        String sql = "SELECT topping_id, topping_name, topping_price, topping_stock FROM topping";
        
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             PreparedStatement pStmt = conn.prepareStatement(sql)) {

            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                ItemDetailsInfo topping = new ItemDetailsInfo();
                topping.setToppingId(rs.getInt("topping_id"));
                topping.setToppingName(rs.getString("topping_name"));
                topping.setToppingPrice(rs.getInt("topping_price"));
                topping.setToppingStock(rs.getInt("topping_stock"));
                // 初期数量は0に設定
                topping.setToppingQuantity(0);
                
                toppingList.add(topping);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toppingList;
    }
}