package model;

import java.io.Serializable;

public class OrderHistoryInfo implements Serializable {
	private int orderId;
	private int orderFlag; // 0:未提供, 1:提供済
	private String productName;
	private String toppingName;
	private int toppingQuantity;
	private int orderQuantity;
	private int subTotal;
	
	public OrderHistoryInfo() {}

	public int getOrderId() { return orderId; }
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getOrderFlag() { return orderFlag; }
	public void setOrderFlag(int orderFlag) {
		this.orderFlag = orderFlag;
	}

	public String getProductName() { return productName; }
	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getToppingName() { return toppingName; }
	public void setToppingName(String toppingName) {
		this.toppingName = toppingName;
	}

	public int getToppingQuantity() { return toppingQuantity; }
	public void setToppingQuantity(int toppingQuantity) {
		this.toppingQuantity = toppingQuantity;
	}

	public int getOrderQuantity() { return orderQuantity; }
	public void setOrderQuantity(int orderQuantity) {
		this.orderQuantity = orderQuantity;
	}

	public int getSubTotal() { return subTotal; }
	public void setSubTotal(int subTotal) {
		this.subTotal = subTotal;
	}
}
