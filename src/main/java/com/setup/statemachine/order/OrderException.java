package com.setup.statemachine.order;

public class OrderException extends RuntimeException {

    private static final long serialVersionUID = -6230074828226030500L;

    public OrderException() {
        super();
    }

    public OrderException(String message) {
        super(message);
    }

}
