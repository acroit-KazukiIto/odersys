package model;

public class ProductInfo {

    private int productId;
    private String productName;
    private String categoryName;
    private int productPrice;
    private int productStock;
    private int productDisplayFlag;

    public ProductInfo() {}

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    public int getProductPrice() { return productPrice; }
    public void setProductPrice(int productPrice) { this.productPrice = productPrice; }

    public int getProductStock() { return productStock; }
    public void setProductStock(int productStock) { this.productStock = productStock; }

    public int getProductDisplayFlag() { return productDisplayFlag; }
    public void setProductDisplayFlag(int productDisplayFlag) { this.productDisplayFlag = productDisplayFlag; }
}