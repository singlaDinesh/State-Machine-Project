package com.setup.statemachine.wallet;

import java.io.Serializable;

public class WalletInfo implements Serializable {

    private static final long serialVersionUID = -1617915574311821240L;

    private int userId;

    private double amount;

    public WalletInfo() {
        super();
    }

    public WalletInfo(int userId) {
        super();
        this.userId = userId;
    }

    public WalletInfo(int userId, double amount) {
        super();
        this.userId = userId;
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}
