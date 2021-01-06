package com.setup.statemachine;

import java.util.HashMap;
import java.util.Map;

public enum StateMachineStates {
    /**
     * Just a start state for the State machine
     */
    START(1),
    /**
     * State after create event is sent
     */
    PENDING_PAYMENT(2),
    /**
     * End State if approved
     */
    COMPLETED(3);

    private int statusId;
    private static Map<Integer, StateMachineStates> map = new HashMap<>();

    static {
        for (StateMachineStates state : StateMachineStates.values()) {
            map.put(state.statusId, state);
        }
    }

    StateMachineStates(int statusId) {
        this.statusId = statusId;
    }

    public int getStatusId() {
        return statusId;
    }

    public static StateMachineStates getState(int statusId) {
        return map.get(statusId);
    }
}
