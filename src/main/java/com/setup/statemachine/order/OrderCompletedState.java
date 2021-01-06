package com.setup.statemachine.order;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.guard.Guard;
import org.springframework.stereotype.Component;

import com.setup.statemachine.BaseState;
import com.setup.statemachine.CompletedState;
import com.setup.statemachine.SMEvents;
import com.setup.statemachine.SMResponse;
import com.setup.statemachine.StateMachineConstants;
import com.setup.statemachine.StateMachineStates;
import com.setup.statemachine.product.Product;
import com.setup.statemachine.wallet.UserWalletMap;
import com.setup.statemachine.wallet.WalletException;

@Component
public class OrderCompletedState extends BaseState implements CompletedState {
    private final Logger logger = LoggerFactory.getLogger(OrderCompletedState.class);
    private final UserOrderMap userOrderMap;
    private final OrderProductMap orderProductMap;
    private final UserWalletMap userWalletMap;
    private final OrderStatusMap orderStatusMap;

    public OrderCompletedState(UserOrderMap userOrderMap, OrderProductMap orderProductMap,
            UserWalletMap userWalletMap, OrderStatusMap orderStatusMap) {
        super();
        this.userOrderMap = userOrderMap;
        this.orderProductMap = orderProductMap;
        this.userWalletMap = userWalletMap;
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

                Integer orderId = (Integer) variables.get(StateConstants.ORDER_ID);
                Integer userId = (Integer) variables.get(StateConstants.USER_ID);

                if (orderId == null || userId == null) {
                    String errorMssg = "Payment pending State Guard Failure: "
                            + "Order Id/User Id can not be null";

                    response = getUpdatedResponse(response, false, errorMssg, true);
                    return false;
                }

                if (!userOrderMap.getOrders(userId).contains(orderId)) {
                    String errorMssg = "Payment pending State Guard Failure: "
                            + "Order Id/User Id not present in order user map";

                    response = getUpdatedResponse(response, false, errorMssg, true);
                    return false;
                }

                double userWalletAmount = 0;
                try {
                    userWalletAmount = userWalletMap.getAmount(userId);
                } catch (WalletException e) {
                    response = getUpdatedResponse(response, false, e.getMessage(), true);
                    return false;
                }

                List<Integer> productIds = orderProductMap.getProducts(orderId);

                double amountToBePaid = 0;
                for (int productId : productIds) {
                    amountToBePaid += Product.getProductFromId(productId).getPrice();
                }

                if (amountToBePaid > userWalletAmount) {
                    String errorMsg = "User does not have enough amount in wallet";
                    response = getUpdatedResponse(response, false, errorMsg, true);
                    return false;
                }

                variables.put(StateConstants.AMOUNT_TO_BE_PAID, amountToBePaid);

                response.setGuardSuccess(true);

                logger.info("Guard Success : Transitioning to Completed State");

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
                Integer orderId = (Integer) variables.get(StateConstants.ORDER_ID);
                Integer userId = (Integer) variables.get(StateConstants.USER_ID);
                Double amountToBePaid = (Double) variables.get(StateConstants.AMOUNT_TO_BE_PAID);
                SMResponse response = new SMResponse(true, true);

                orderStatusMap.updateOrderStatus(orderId,
                        StateMachineStates.COMPLETED.getStatusId());
                userWalletMap.subtractMoney(userId, amountToBePaid);
                variables.put(StateMachineConstants.RESPONSE, response);
            }
        };
    }

}
