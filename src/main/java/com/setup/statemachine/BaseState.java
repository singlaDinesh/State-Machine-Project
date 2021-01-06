package com.setup.statemachine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.guard.Guard;

public abstract class BaseState implements State {

    protected Guard<StateMachineStates, SMEvents> guard;
    protected Action<StateMachineStates, SMEvents> action;
    protected Action<StateMachineStates, SMEvents> errorAction;

    private final Logger logger = LoggerFactory.getLogger(BaseState.class);

    public BaseState() {
        setGuard();
        setAction();
    }

    public abstract void setGuard();

    public abstract void setAction();

    public void setErrorAction() {
        errorAction = new Action<StateMachineStates, SMEvents>() {
            @Override
            public void execute(StateContext<StateMachineStates, SMEvents> context) {
                String errorMssg = "Action Failure : Error while processing state actions";

                SMResponse sMResponse = new SMResponse(true, false);
                sMResponse = getUpdatedResponse(sMResponse, false, errorMssg, false);
                context.getExtendedState().getVariables().put(StateMachineConstants.RESPONSE,
                        sMResponse);
            }
        };
    }

    @Override
    public Guard<StateMachineStates, SMEvents> getGuard() {
        return guard;
    }

    @Override
    public Action<StateMachineStates, SMEvents> getAction() {
        return action;
    }

    @Override
    public Action<StateMachineStates, SMEvents> getErrorAction() {
        return errorAction;
    }


    protected SMResponse getUpdatedResponse(SMResponse response, boolean validity,
            String mssg, boolean isGuard) {

        if (validity) {
            logger.info(mssg);
        } else {
            logger.error(mssg);
            response.setError(mssg);
        }

        if (isGuard) {
             response.setGuardSuccess(validity);
        } else {
            response.setActionSuccess(validity);
        }
        return response;
    }

}
