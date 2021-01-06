package com.setup.statemachine;

public class SMResponse {

    private boolean guardSuccess;

    private boolean actionSuccess;

    private String error;

    public SMResponse(boolean guardSuccess, boolean actionSuccess) {
        super();
        this.guardSuccess = guardSuccess;
        this.actionSuccess = actionSuccess;
    }

    public boolean isGuardSuccess() {
        return guardSuccess;
    }

    public void setGuardSuccess(boolean guardSuccess) {
        this.guardSuccess = guardSuccess;
    }

    public boolean isActionSuccess() {
        return actionSuccess;
    }

    public void setActionSuccess(boolean actionSuccess) {
        this.actionSuccess = actionSuccess;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
