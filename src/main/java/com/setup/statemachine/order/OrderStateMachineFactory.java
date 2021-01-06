package com.setup.statemachine.order;

import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Component;

import com.setup.statemachine.SMEvents;
import com.setup.statemachine.StateMachineFactory;
import com.setup.statemachine.StateMachineStates;

@Component
public class OrderStateMachineFactory {

    private final StateMachineFactory factory;
    private final OrderStatusMap orderStatusMap;

    public OrderStateMachineFactory(StateMachineFactory factory, OrderStatusMap orderStatusMap) {
        super();
        this.factory = factory;
        this.orderStatusMap = orderStatusMap;
    }

    public StateMachine<StateMachineStates, SMEvents> getStateMachine(int orderId)
            throws Exception {

        StateMachineStates state = StateMachineStates
                .getState(orderStatusMap.getOrderStatus(orderId));
        StateMachine<StateMachineStates, SMEvents> stateMachine = factory.buildMachine();
        stateMachine.start();
        stateMachine.getStateMachineAccessor().doWithAllRegions(access -> access
                .resetStateMachine(new DefaultStateMachineContext<>(state, null, null, null)));
        return stateMachine;
    }

    public StateMachine<StateMachineStates, SMEvents> getStateMachine()
            throws Exception {
        StateMachine<StateMachineStates, SMEvents> stateMachine = factory.buildMachine();
        stateMachine.start();
        return stateMachine;
    }
}
