package com.setup.statemachine.order;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/order", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderRestController {

    private final OrderService orderService;

    public OrderRestController(OrderService orderService) {
        super();
        this.orderService = orderService;
    }

    @PutMapping(value = "/book", produces = MediaType.APPLICATION_JSON_VALUE, consumes =
            MediaType.APPLICATION_JSON_VALUE)
    public OrderInfo bookOrder(@RequestParam int userId, @RequestBody List<Integer> productIds)
            throws Exception {
        return orderService.bookOrder(userId, productIds);
    }

    @PutMapping(value = "/payment", produces = MediaType.APPLICATION_JSON_VALUE, consumes =
            MediaType.APPLICATION_JSON_VALUE)
    public OrderInfo doPayment(@RequestParam int userId, @RequestParam int orderId)
            throws Exception {
        return orderService.doPayment(orderId, userId);
    }

}
