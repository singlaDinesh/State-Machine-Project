package com.setup.statemachine.order;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.guard.Guard;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.setup.statemachine.BaseState;
import com.setup.statemachine.PaymentPendingState;
import com.setup.statemachine.SMEvents;
import com.setup.statemachine.SMResponse;
import com.setup.statemachine.StateMachineConstants;
import com.setup.statemachine.StateMachineStates;
import com.setup.statemachine.product.Product;

@Component
public class OrderPaymentPendingState extends BaseState implements PaymentPendingState {

    private final Logger logger = LoggerFactory.getLogger(OrderPaymentPendingState.class);
    private final UserOrderMap userOrderMap;
    private final OrderProductMap orderProductMap;
    private final OrderStatusMap orderStatusMap;

    public OrderPaymentPendingState(UserOrderMap userOrderMap, OrderProductMap orderProductMap,
            OrderStatusMap orderStatusMap) {
        super();
        this.userOrderMap = userOrderMap;
        this.orderProductMap = orderProductMap;
        this.orderStatusMap = orderStatusMap;
    }

    @Override
    public void setGuard() {
        guard = new Guard<StateMachineStates, SMEvents>() {
            @Override
            public boolean evaluate(StateContext<StateMachineStates, SMEvents> context) {
                Map<Object, Object> variables = context.getExtendedState().getVariables();
                SMResponse response = new SMResponse(false, false);
                variables.put(StateMachineConstants.RESPONSE, response);

                @SuppressWarnings("unchecked")
                List<Integer> productIds = (List<Integer>) variables.get(StateConstants.PRODUCTS);
                Integer orderId = (Integer) variables.get(StateConstants.ORDER_ID);
                Integer userId = (Integer) variables.get(StateConstants.USER_ID);

                if (orderId == null || userId == null || CollectionUtils.isEmpty(productIds)) {
                    String errorMssg = "Payment pending State Guard Failure: "
                            + "Order Id/User Id/Product Ids not present";

                    response = getUpdatedResponse(response, false, errorMssg, true);
                    return false;
                }

                try {
                    for (Integer i : productIds) {
                        Product p = Product.getProductFromId(i);
                    }
                } catch (Exception e) {
                    response = getUpdatedResponse(response, false, e.getMessage(), true);
                }

                response.setGuardSuccess(true);

                logger.info("Guard Success : Transitioning to payement pending State");

                return true;
            }
        };
    }

    @Override
    public void setAction() {
        action = new Action<StateMachineStates, SMEvents>() {
            @Override
            public void execute(StateContext<StateMachineStates, SMEvents> context) {
                Map<Object, Object> variables = context.getExtendedState().getVariables();
                @SuppressWarnings("unchecked")
                List<Integer> productIds = (List<Integer>) variables.get(StateConstants.PRODUCTS);
                Integer orderId = (Integer) variables.get(StateConstants.ORDER_ID);
                Integer userId = (Integer) variables.get(StateConstants.USER_ID);
                SMResponse response = new SMResponse(true, true);

                userOrderMap.addOrder(userId, orderId);

                for (Integer productId : productIds) {
                    orderProductMap.addProduct(orderId, productId);
                }

                orderStatusMap.addOrder(orderId, StateMachineStates.PENDING_PAYMENT.getStatusId());
                variables.put(StateMachineConstants.RESPONSE, response);
            }
        };
    }

}
