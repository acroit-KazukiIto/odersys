package model;

import java.io.Serializable;

public class OrderList implements Serializable{
	private int[] productToppingList;
	private String productName;
	private String toppingName;
	private int subTotal;
	private int productPrice;
	private int toppingPrice;
	private int toppingQuantity;
	
	
	public int[] getProductToppingList() {
		return productToppingList;
	}
	public void setProductToppingList(int[] productToppingList) {
		this.productToppingList = productToppingList;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getToppingName() {
		return toppingName;
	}
	public void setToppingName(String toppingName) {
		this.toppingName = toppingName;
	}
	public int getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(int subTotal) {
		this.subTotal = subTotal;
	}
	public int getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(int productPrice) {
		this.productPrice = productPrice;
	}
	public int getToppingPrice() {
		return toppingPrice;
	}
	public void setToppingPrice(int toppingPrice) {
		this.toppingPrice = toppingPrice;
	}
	public int getToppingQuantity() {
		return toppingQuantity;
	}
	public void setToppingQuantity(int toppingQuantity) {
		this.toppingQuantity = toppingQuantity;
	}
}
