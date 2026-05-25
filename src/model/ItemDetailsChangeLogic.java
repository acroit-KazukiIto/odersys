package model;

import java.sql.SQLException;

import dao.OrderListDAO;

public class ItemDetailsChangeLogic {
	OrderListInfo ol = new OrderListInfo();
	OrderListDAO olDAO = new OrderListDAO();
	int toppingStock = ol.getToppingStock();
	int productStock = ol.getProductStock();
	int order = ol.getOrderQuantity();
	int toppingQuantity = ol.getToppingQuantity();
	int orderPrice = ol.getOrderPrice();
	int productPrice = ol.getProductPrice();
	int toppingPrice = ol.getToppingPrice();
	int subTotal = ol.getSubTotal();
	int allOrderPrice = ol.getAllOrderPrice();
	public void calcOrderQuantity(int n, int oid, int tquan) throws SQLException {
		System.out.println("ロジック呼び出されました。ItemDetailsChangeLogic");
		
			//オーダーの商品数の計算
			tquan = tquan + n;
			//subTotal = productPrice + toppingPrice * tquan;
			
			ol.setSubTotal(subTotal);
			ol.setToppingQuantity(tquan);
			ol.setToppingQuantity(toppingQuantity);
			


	}

}
