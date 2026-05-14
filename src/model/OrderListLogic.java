package model;

import dao.OrderListDao;

public class OrderListLogic {
	OrderList ol = new OrderList();
	OrderListDao olDAO = new OrderListDao();
	int toppingStock = ol.getToppingStock();
	int productStock = ol.getProductStock();
	int order = ol.getOrderQuantity();
	int toppingQuantity = ol.getToppingQuantity();
	public void calcOrderQuantity(int n) {
		if(order >= productStock  || toppingQuantity >= toppingStock) {
			order = order + 0;
		}else {
			order = order + n;
			ol.setOrderQuantity(order);
			
		}
		
		
		
		
		//olDAO.updateOrderDetails();
	}
	
	
}
