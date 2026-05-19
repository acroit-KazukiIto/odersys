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

    /**
     * product_details テーブルに新しい注文を1行追加
     * (order_id は AUTO_INCREMENT なので、product_id のみを指定してインサートします)
     */
    public boolean insertProductDetail(int productId) { 
        String sql = "INSERT INTO product_details (product_id) "
        		+ "VALUES ('2')";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (
                Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
                PreparedStatement pStmt = conn.prepareStatement(sql)
            ) {
                // 「?」に商品IDをセット
                pStmt.setInt(1, productId);

                int rowsInserted = pStmt.executeUpdate();

                return rowsInserted > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * トッピング一覧の取得
     */
    public List<ItemDetailsInfo> findToppingList(String categoryName) {
        List<ItemDetailsInfo> toppingList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pStmt = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);

            if (categoryName.equals("お好み焼き")
                    || categoryName.equals("もんじゃ焼き")
                    || categoryName.equals("鉄板焼")
                    || categoryName.equals("鉄板焼き")) {

                String sql = "SELECT topping_id, topping_name, topping_price, topping_stock FROM topping";
                pStmt = conn.prepareStatement(sql);
                rs = pStmt.executeQuery();

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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pStmt != null) pStmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return toppingList;
    }
}