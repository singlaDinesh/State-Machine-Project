package com.setup.statemachine.order;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class OrderStatusMap {

    private final Map<Integer, Integer> map;

    public OrderStatusMap() {
        super();
        this.map = new HashMap<>();
    }

    public void addOrder(Integer orderId, Integer statusId) {
        if (statusId == null || orderId == null) {
            throw new OrderException("Status Id and order id can not be null");
        }

        map.put(orderId, statusId);
    }

    public void updateOrderStatus(Integer orderId, Integer statusId) {
        if (statusId == null || orderId == null) {
            throw new OrderException("Status Id and order id can not be null");
        }

        if (!map.containsKey(orderId)) {
            throw new OrderException("Order is not present in order status map");
        }

        map.put(orderId, statusId);
    }

    public Integer getOrderStatus(Integer orderId) {
        if (orderId == null) {
            throw new OrderException("Order id can not be null");
        }
        if (!map.containsKey(orderId)) {
            throw new OrderException("Order is not present in order status map");
        }

        return map.get(orderId);
    }

}
