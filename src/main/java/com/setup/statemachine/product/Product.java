package com.setup.statemachine.product;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Product {
    ALEXA(1, 10000d),
    EAR_BUDS(2, 5000d),
    HEADPHONES(3, 15000d);

    private final int productId;
    private final double price;
    private static final Map<Integer, Product> map;

    private Product(int productId, double price) {
        this.productId = productId;
        this.price = price;
    }

    public int getProductId() {
        return productId;
    }

    public double getPrice() {
        return price;
    }

    static {
        map = Stream.of(Product.values())
                .collect(Collectors.toMap(Product::getProductId, Function.identity()));
    }

    public static Product getProductFromId(int id) {
        if (!map.containsKey(id)) {
            throw new RuntimeException("Product does not exist");
        }

        return map.get(id);
    }

}
