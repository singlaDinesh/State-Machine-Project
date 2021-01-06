package com.setup.statemachine.wallet;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component
public final class UserWalletMap {

    private final Map<Integer, Double> map;

    public UserWalletMap() {
        super();
        this.map = new ConcurrentHashMap<>();
    }

    public double getAmount(Integer userId) {
        if (userId == null) {
            throw new WalletException("UserId cannot be null");
        }
        if (!map.containsKey(userId) || map.get(userId) == null) {
            throw new WalletException("User has not registred his wallet");
        }

        return map.get(userId);
    }

    public void addMoney(Integer userId, Double amountToAdd) {
        if (userId == null || amountToAdd == null) {
            throw new WalletException("UserId or amount to add cannot be null while adding money to"
                    + " wallet");
        }

        if (!map.containsKey(userId)) {
            map.put(userId, 0D);
        }

        map.put(userId, map.get(userId) + amountToAdd);
    }

    public void updateMoney(Integer userId, Double amount) {
        if (userId == null || amount == null) {
            throw new WalletException("UserId or amount to add cannot be null while adding money to"
                    + " wallet");
        }

        map.put(userId, amount);
    }

    public void subtractMoney(Integer userId, Double amount) {
        if (userId == null || amount == null) {
            throw new WalletException("UserId or amount to subtract cannot be null while"
                    + " subtracting money to wallet");
        }

        if (!map.containsKey(userId)) {
            throw new WalletException("User has not registred his wallet");
        }

        double currentAmount = map.get(userId);
        currentAmount -= amount;
        if (currentAmount < 0) {
            throw new WalletException("Not enough amount in the wallet");
        }

        map.put(userId, currentAmount);
    }

}
