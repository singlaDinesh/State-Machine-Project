package com.setup.statemachine.wallet;

import org.springframework.stereotype.Service;

@Service
public class WalletService {

    private final UserWalletMap userWalletMap;

    public WalletService(UserWalletMap userWalletMap) {
        super();
        this.userWalletMap = userWalletMap;
    }

    public WalletInfo getWalletInfo(int userId) {
        WalletInfo walletInfo = new WalletInfo(userId);
        double walletAmount = userWalletMap.getAmount(userId);
        walletInfo.setAmount(walletAmount);

        return walletInfo;
    }

    public WalletInfo updateWallet(int userId, double amount) {
        userWalletMap.updateMoney(userId, amount);
        return getWalletInfo(userId);
    }

    public WalletInfo addMoney(int userId, double amount) {
        userWalletMap.addMoney(userId, amount);
        return getWalletInfo(userId);
    }

    public WalletInfo withdrawMoney(int userId, double amount) {
        userWalletMap.subtractMoney(userId, amount);
        return getWalletInfo(userId);
    }
}
