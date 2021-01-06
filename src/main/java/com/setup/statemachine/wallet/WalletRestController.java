package com.setup.statemachine.wallet;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "wallet", produces = MediaType.APPLICATION_JSON_VALUE)
public class WalletRestController {

    private final WalletService walletService;

    public WalletRestController(WalletService walletService) {
        super();
        this.walletService = walletService;
    }

    @GetMapping(value = "/info", produces = MediaType.APPLICATION_JSON_VALUE)
    public WalletInfo getWalletInfo(@RequestParam int userId) {
        return walletService.getWalletInfo(userId);
    }

    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public WalletInfo updateWallet(@RequestParam int userId, @RequestParam double amount) {
        return walletService.updateWallet(userId, amount);
    }

    @PutMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public WalletInfo addMoneyToWallet(@RequestParam int userId, @RequestParam double amount) {
        return walletService.addMoney(userId, amount);
    }

    @PutMapping(value = "/withdraw", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public WalletInfo withdrawMoney(@RequestParam int userId, @RequestParam double amount) {
        return walletService.withdrawMoney(userId, amount);
    }
}
