package com.setup.statemachine;

import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.guard.Guard;

public interface State {
    public Guard<StateMachineStates, SMEvents> getGuard();

    public Action<StateMachineStates, SMEvents> getAction();

    public Action<StateMachineStates, SMEvents> getErrorAction();
}
