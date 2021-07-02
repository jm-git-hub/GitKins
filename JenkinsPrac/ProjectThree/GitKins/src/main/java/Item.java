
public class Item {
    private String productName;
    private double productPrice;
    private double productOldPrice;
    private double productDiscount;

    public Item() {
    }

    public Item(String productName, double productPrice, double productOldPrice, double productDiscount) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productOldPrice = productOldPrice;
        this.productDiscount = productDiscount;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public double getProductOldPrice() {
        return productOldPrice;
    }

    public void setProductOldPrice(double productOldPrice) {
        this.productOldPrice = productOldPrice;
    }

    public double getProductDiscount() {
        return productDiscount;
    }

    public void setProductDiscount(int productDiscount) {
        this.productDiscount = productDiscount;
    }

    public Item singleItem(String productName, double productPrice, double productOldPrice, double productDiscount) {
        return new Item(productName, productPrice, productOldPrice, productDiscount);
    }
}
