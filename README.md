# A State Machine Project

A simple state machine for Spring Boot projects

After importing into an IDE like EClipse can be run as Spring Boot application.

This project contains an example on how to use spring state machine in restful web services.

## Things to learn

1. Setting up the Spring state machine
2. State machine runs guard and action of action in different threads than main threads
3. To run the guard and action in main thread, add the implementation of TaskScheduler and each function of tashSceduler should return null
4. Global exception handler advice to return the proper message thrown inside exception

## Swagger

Swagger is majorly used for API documentation. Following are the API Controllers used in the application:
<kbd>![Swagger](https://github.com/singlaDinesh/State-machine-project/blob/master/images/swagger.png)

## Usage

|Initial State |Pre-event |   Processor    |        Post-event  |  Final State  |
| --- | --- | --- | --- | --- |  
|Start    ->|  CREATE ->| orderProcessor() ->| orderCreated   -> |PENDING_PAYMENT |
|PMTPENDING -> | PAYMENT    ->| paymentProcessor() ->| paymentError   -> |PENDING_PAYMENT |
|PMTPENDING ->|  PAYMENT    ->| paymentProcessor() ->| paymentSuccess ->| COMPLETED |

1. Register you user in wallet with amount that you want using api /wallet/update
   User wallet amount is maintained using HashMap.

2. You can add(api: /wallet/add), withdraw(api: /wallet/withdraw) from your wallet. Also, api "/wallet/info" can be used to get the current amount in your wallet.

3. You can get the info of products using api: "/product/get/all". This api will give you all the products in system.
   you can also get product info by their ids using api: "/product/get"
   Products are maintained in an ENUM.

4. Book orders using api: "/order/book". An order can be booked even if user does not have enough money in wallet as payment is not done yet.
   Order can have multiple products associated with it and same product multiple times. So to maintain this, SynchronizedListMultimap is used.
   A user can have multiple orders associated. To maintain this, SynchronizedSetMultimap is used.
   Order has status associated with it. To maintain this, HashMap is used.

5. To complete payment, use api "/order/payment".
   If the user has enough in wallet, then the order will move to complete status. Otherwise, Order will remain in PENDING_PAYMENT status.

### References
1. https://docs.spring.io/spring-statemachine/docs/current/reference/
2. https://github.com/spring-tips/statemachine/issues/1
3. https://www.baeldung.com/exception-handling-for-rest-with-spring
