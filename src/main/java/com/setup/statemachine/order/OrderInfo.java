package com.setup.statemachine.order;

import java.io.Serializable;
import java.util.List;

import com.setup.statemachine.product.Product;

public class OrderInfo implements Serializable {

    private static final long serialVersionUID = -4745342089334378247L;

    private int orderId;

    private List<Integer> products;

    private int userId;

    private double amount;

    public OrderInfo() {
        super();
    }

    public OrderInfo(int orderId, int userId) {
        super();
        this.orderId = orderId;
        this.userId = userId;
    }

    public OrderInfo(int userId, int orderId, List<Integer> products) {
        super();
        this.orderId = orderId;
        this.products = products;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public List<Integer> getProducts() {
        return products;
    }

    public void setProducts(List<Integer> products) {
        this.products = products;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

}
