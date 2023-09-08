package ua.dmitriiev.beautysaloon.services;

import org.springframework.data.domain.Page;

import ua.dmitriiev.beautysaloon.entities.Order;

import java.util.List;
import java.util.UUID;

public interface OrderService {


    List<Order> listOrders();

    Order findOrderById(UUID id);


    void saveOrder(Order order);


    void updateOrder(UUID id, Order updatedOrder);

    void deleteById(UUID id);

    Order findOrderByName(String orderName);

    List<Order> findOrdersByName(String orderName);

    Page<Order> listAllOrders(int pageNumber, int pageSize);
}
