package com.setup.statemachine.order;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.setup.statemachine.SMEvents;
import com.setup.statemachine.SMResponse;
import com.setup.statemachine.StateMachineConstants;
import com.setup.statemachine.StateMachineStates;
import com.setup.statemachine.product.Product;

@Service
public class OrderService {
    private static final AtomicInteger ORDER_ID = new AtomicInteger(0);

    private final OrderStateMachineFactory stateMachineFactory;

    private final OrderProductMap orderProductMap;

    public OrderService(OrderStateMachineFactory stateMachineFactory, OrderProductMap orderProductMap) {
        super();
        this.stateMachineFactory = stateMachineFactory;
        this.orderProductMap = orderProductMap;
    }

    public OrderInfo bookOrder(int userId, List<Integer> productIds) throws Exception {
        if (CollectionUtils.isEmpty(productIds)) {
            throw new OrderException("Products list can not be empty");
        }

        int orderId = ORDER_ID.incrementAndGet();

        StateMachine<StateMachineStates, SMEvents> stateMachine = stateMachineFactory.getStateMachine();
        Map<Object, Object> variableMap = stateMachine.getExtendedState().getVariables();
        variableMap.clear();

        variableMap.put(StateConstants.ORDER_ID, orderId);
        variableMap.put(StateConstants.USER_ID, userId);
        variableMap.put(StateConstants.PRODUCTS, productIds);

        boolean success = stateMachine.sendEvent(SMEvents.CREATE);

        if (!success) {
            throw new OrderException("Create event not accepted by state machine");
        }

        SMResponse response = (SMResponse) variableMap.get(StateMachineConstants.RESPONSE);
        if (response != null && (!response.isGuardSuccess() || !response.isActionSuccess())) {
            throw new OrderException(response.getError());
        }

        return getOrderInfo(userId, orderId);
    }

    public OrderInfo doPayment(int orderId, int userId) throws Exception {
        StateMachine<StateMachineStates, SMEvents> stateMachine = stateMachineFactory.getStateMachine(orderId);
        Map<Object, Object> variableMap = stateMachine.getExtendedState().getVariables();
        variableMap.clear();

        variableMap.put(StateConstants.ORDER_ID, orderId);
        variableMap.put(StateConstants.USER_ID, userId);
        boolean success = stateMachine.sendEvent(SMEvents.PAYMENT);

        if (!success) {
            throw new OrderException("Create event not accepted by state machine");
        }

        SMResponse response = (SMResponse) variableMap.get(StateMachineConstants.RESPONSE);
        if (response != null && (!response.isGuardSuccess() || !response.isActionSuccess())) {
            throw new OrderException(response.getError());
        }

        return getOrderInfo(userId, orderId);
    }

    private OrderInfo getOrderInfo(int userId, int orderId) {
        OrderInfo info = new OrderInfo(orderId, userId);
        List<Integer> productIds = orderProductMap.getProducts(orderId);
        double amountToBePaid = 0;
        for (int productId : productIds) {
            amountToBePaid += Product.getProductFromId(productId).getPrice();
        }

        info.setProducts(productIds);
        info.setAmount(amountToBePaid);
        return info;
    }
}
