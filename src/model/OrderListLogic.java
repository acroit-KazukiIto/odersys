package model;

import java.sql.SQLException;

import dao.OrderListDAO;

public class OrderListLogic {
	OrderListInfo ol = new OrderListInfo();
	OrderListDAO olDAO = new OrderListDAO();
	int toppingStock = ol.getToppingStock();
	int productStock = ol.getProductStock();
	int order = ol.getOrderQuantity();
	int toppingQuantity = ol.getToppingQuantity();
	int productPrice = ol.getProductPrice();
	int toppingPrice = ol.getToppingPrice();
	
	int allOrderPrice = ol.getAllOrderPrice();
	
	//public static void
	public void calcOrderQuantity(int n) {
		
		//オーダーの商品数の計算
		order = order + n;
		toppingQuantity = toppingQuantity * order;
		
		
		//ストック上限の処理
		if(order >= productStock) {
			order = productStock;
			ol.setOrderQuantity(order);
			
			
		}else if(toppingQuantity >= toppingStock){
			toppingQuantity = toppingStock;
			ol.setToppingQuantity(toppingQuantity);
			
		}
		
		try {
			olDAO.updateOrderDetails();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public void calcSubTotal() {
		int subTotal = ol.getSubTotal();
		subTotal = (productPrice + toppingPrice * toppingQuantity) * order;
		ol.setSubTotal(subTotal);
	}
	public void calcAllOrderPrice(int subTotal) {
		int num = ol.getAllOrderPrice();
		int aop = num + subTotal;
		ol.setAllOrderPrice(aop);
	}
	
	
}
