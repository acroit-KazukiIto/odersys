package model;

import java.io.Serializable;

public class ItemDetailsInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private int toppingId;
    private String toppingName;
    private int toppingPrice;
    private int toppingStock;
    private int toppingQuantity;
    private String category;
    private int sessionId;
    

    public ItemDetailsInfo() {}

    public int getToppingId() { return toppingId; }
    public void setToppingId(int toppingId) { this.toppingId = toppingId; }

    public String getToppingName() { return toppingName; }
    public void setToppingName(String toppingName) { this.toppingName = toppingName; }

    public int getToppingPrice() { return toppingPrice; }
    public void setToppingPrice(int toppingPrice) { this.toppingPrice = toppingPrice; }

    public int getToppingStock() { return toppingStock; }
    public void setToppingStock(int toppingStock) { this.toppingStock = toppingStock; }

    public int getToppingQuantity() { return toppingQuantity; }
    public void setToppingQuantity(int toppingQuantity) { this.toppingQuantity = toppingQuantity; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}