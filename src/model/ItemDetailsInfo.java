package model;

import java.io.Serializable;

public class ItemDetailsInfo implements Serializable {
    private int toppingId;      // トッピングID
    private String toppingName; // トッピング名
    private int toppingPrice;   // トッピング価格
    private int toppingStock;   // 在庫数
    private int toppingQuantity; // 選択された数量（初期値0）

    public ItemDetailsInfo() {}

    // ゲッター・セッター
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
}