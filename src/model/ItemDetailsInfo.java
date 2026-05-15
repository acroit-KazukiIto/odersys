package model;

import java.io.Serializable;

public class ItemDetailsInfo implements Serializable {
    private int toppingId;
    private String toppingName;
    private int toppingPrice;
    private int toppingStock;
    private int toppingDisplayFlag;
    private int toppingQuantity; // 画面表示用aa

    public ItemDetailsInfo() {}

    // Getter, Setter
    public int getToppingId() { return toppingId; }
    public void setToppingId(int toppingId) { this.toppingId = toppingId; }
    public String getToppingName() { return toppingName; }
    public void setToppingName(String toppingName) { this.toppingName = toppingName; }
    public int getToppingPrice() { return toppingPrice; }
    public void setToppingPrice(int toppingPrice) { this.toppingPrice = toppingPrice; }
    public int getToppingStock() { return toppingStock; }
    public void setToppingStock(int toppingStock) { this.toppingStock = toppingStock; }
    public int getToppingDisplayFlag() { return toppingDisplayFlag; }
    public void setToppingDisplayFlag(int toppingDisplayFlag) { this.toppingDisplayFlag = toppingDisplayFlag; }
    public int getToppingQuantity() { return toppingQuantity; }
    public void setToppingQuantity(int toppingQuantity) { this.toppingQuantity = toppingQuantity; }
}