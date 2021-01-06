package com.setup.statemachine;

import java.util.EnumSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.config.StateMachineBuilder.Builder;
import org.springframework.stereotype.Component;

@Component
public class StateMachineFactory {
    private final Logger logger = LoggerFactory.getLogger(StateMachineFactory.class);
    private final PaymentPendingState paymentPendingState;
    private final CompletedState completedState;

    public StateMachineFactory(PaymentPendingState paymentPendingState,
            CompletedState completedState) {
        super();
        this.paymentPendingState = paymentPendingState;
        this.completedState = completedState;
    }

    public StateMachine<StateMachineStates, SMEvents> buildMachine() throws Exception {
        Builder<StateMachineStates, SMEvents> builder = StateMachineBuilder.builder();
        builder.configureStates()
        .withStates()
            .initial(StateMachineStates.START)
            .end(StateMachineStates.COMPLETED)
            .states(EnumSet.allOf(StateMachineStates.class));

        configureTransitions(builder);

        builder.configureConfiguration()
        .withConfiguration()
            .taskScheduler(new DefaultTaskScheduler());

        return builder.build();
    }

    private Builder<StateMachineStates, SMEvents> configureTransitions(
            Builder<StateMachineStates, SMEvents> builder)
            throws Exception {
        builder.configureTransitions()
        .withExternal()
            .source(StateMachineStates.START).target(StateMachineStates.PENDING_PAYMENT)
            .event(SMEvents.CREATE)
            .guard(paymentPendingState.getGuard())
            .action(paymentPendingState.getAction(), paymentPendingState.getErrorAction())
            .and()
        .withExternal()
            .source(StateMachineStates.PENDING_PAYMENT).target(StateMachineStates.COMPLETED)
            .event(SMEvents.PAYMENT)
            .guard(completedState.getGuard())
            .action(completedState.getAction(), completedState.getErrorAction());

        return builder;
    }
}
