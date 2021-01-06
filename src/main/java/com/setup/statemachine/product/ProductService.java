package com.setup.statemachine.product;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.setup.statemachine.wallet.WalletException;

@Service
public class ProductService {

    public ProductInfo getProduct(int productId) {
        Product product = Product.getProductFromId(productId);
        return new ProductInfo(product, product.getProductId(), product.getPrice());
    }

    public List<ProductInfo> getAllProducts() {
        List<ProductInfo> productInfos = new ArrayList<>();
        for (Product product : Product.values()) {
            productInfos.add(new ProductInfo(product, product.getProductId(), product.getPrice()));
        }

        return productInfos;
    }

    @ExceptionHandler(RuntimeException.class)
    public String handlerException(WalletException exception) {
        return exception.getMessage();
    }

}
