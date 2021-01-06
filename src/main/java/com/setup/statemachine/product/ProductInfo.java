package com.setup.statemachine.product;

import java.io.Serializable;

public class ProductInfo implements Serializable {

    private static final long serialVersionUID = -6104823998883288672L;

    private Product product;

    private int productId;

    private double price;

    public ProductInfo() {
        super();
    }

    public ProductInfo(Product product, int productId, double price) {
        super();
        this.product = product;
        this.productId = productId;
        this.price = price;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
