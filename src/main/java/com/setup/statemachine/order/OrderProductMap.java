package com.setup.statemachine.order;

import java.util.List;

import org.springframework.stereotype.Component;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

@Component
public class OrderProductMap {

    private final Multimap<Integer, Integer> map;

    public OrderProductMap() {
        super();
        this.map = Multimaps.synchronizedListMultimap(ArrayListMultimap.create());
    }

    public void addProduct(Integer orderId, Integer productId) {
        if (productId == null || orderId == null) {
            throw new OrderException("product Id and order id can not be null");
        }

        map.put(orderId, productId);
    }

    public List<Integer> getProducts(int orderId) {
        if (!map.containsKey(orderId)) {
            throw new OrderException("User does not have any order associated");
        }

        return (List<Integer>) map.get(orderId);
    }
}
