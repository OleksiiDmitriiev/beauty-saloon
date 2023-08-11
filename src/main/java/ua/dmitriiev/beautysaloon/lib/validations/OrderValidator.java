package ua.dmitriiev.beautysaloon.lib.validations;

import lombok.extern.slf4j.Slf4j;
import ua.dmitriiev.beautysaloon.entities.Order;
import ua.dmitriiev.beautysaloon.lib.exceptions.InvalidClientException;
import ua.dmitriiev.beautysaloon.lib.exceptions.InvalidServiceException;

@Slf4j
public class OrderValidator {

    public static void validateOrder(Order order) {

        if (order == null || order.getOrderName() == null) {

            throw new IllegalArgumentException("Invalid order data provided.");
        }

        if (order.getServiceOwner() == null) {
            log.error("Invalid order data provided: {}", order);
            throw new InvalidServiceException("Invalid service exception");
        }

        if (order.getClientOwner() == null) {
            log.error("Invalid order data provided: {}", order);
            throw new InvalidClientException("Invalid client provided.");
        }
    }
}
