package com.setup.statemachine.order;

import java.util.Set;

import org.springframework.stereotype.Component;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

@Component
public class UserOrderMap {

    private final Multimap<Integer, Integer> map;

    public UserOrderMap() {
        super();
        this.map = Multimaps.synchronizedSetMultimap(HashMultimap.create());
    }

    public void addOrder(Integer userId, Integer orderId) {
        if (userId == null || orderId == null) {
            throw new OrderException("User Id and order id can not be null");
        }

        map.put(userId, orderId);
    }

    public Set<Integer> getOrders(int userId) {
        if (!map.containsKey(userId)) {
            throw new OrderException("User does not have any order associated");
        }

        return (Set<Integer>) map.get(userId);
    }
}
