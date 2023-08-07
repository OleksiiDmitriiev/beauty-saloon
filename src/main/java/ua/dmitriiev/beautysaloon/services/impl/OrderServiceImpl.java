package ua.dmitriiev.beautysaloon.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dmitriiev.beautysaloon.entities.Master;
import ua.dmitriiev.beautysaloon.entities.Order;
import ua.dmitriiev.beautysaloon.lib.exceptions.NotFoundException;
import ua.dmitriiev.beautysaloon.lib.exceptions.OrderListException;
import ua.dmitriiev.beautysaloon.repositories.OrderRepository;
import ua.dmitriiev.beautysaloon.services.OrderService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {

        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> listOrders() {

        return orderRepository.findAll();
    }

    @Override
    public Order findOrderById(UUID id) {
//        try {
//            Optional<Order> foundOrder = orderRepository.findOrderById(id);
//            return foundOrder.orElseThrow(() -> new NotFoundException("Order not found"));
//        } catch (NotFoundException exception) {
//            log.error("Order not found with ID: {}", id, exception);
//            throw exception;
//        }

        Optional<Order> foundOrder = orderRepository.findOrderById(id);

        return foundOrder.orElseThrow(() -> new NotFoundException("Order not found with ID: " + id));
    }

    @Transactional
    @Override
    public void saveOrder(Order order) {

        if (order == null ||
                order.getOrderName() == null ||
                order.getServiceOwner() == null ||
                order.getClientOwner() == null) {
            log.error("Invalid order data provided: {}", order);
            throw new IllegalArgumentException("Invalid order data provided.");
        }


        orderRepository.save(order);
    }

    @Transactional
    @Override
    public void updateOrder(UUID id, Order updatedOrder) {
        Order orderToBeUpdated = orderRepository.findOrderById(id)
                .orElseThrow(() -> new NoSuchElementException("Order not found"));


        orderToBeUpdated.setOrderName(updatedOrder.getOrderName());
        orderToBeUpdated.setOrderStatus(updatedOrder.getOrderStatus());
        orderToBeUpdated.setServiceOwner(updatedOrder.getServiceOwner());
        orderToBeUpdated.setClientOwner(updatedOrder.getClientOwner());
        orderToBeUpdated.setOrderStatus(updatedOrder.getOrderStatus());


        orderRepository.save(orderToBeUpdated);

    }


    @Transactional
    @Override
    public void deleteById(UUID id) {

        orderRepository.deleteOrderById(id);
    }

    @Override
    public Order findOrderByName(String orderName) {

        Optional<Order> foundOrder = orderRepository.findByOrderNameEqualsIgnoreCase(orderName);
        return foundOrder.orElseThrow(() -> new NotFoundException("Order with name '" + orderName + "' not found."));

    }

    @Override
    public List<Order> findOrdersByName(String orderName) {

        //TODO for restAPI
//        List<Order> orders = orderRepository.findOrdersByOrderNameEqualsIgnoreCase(orderName);
//
//        if (orders.isEmpty()) {
//            log.warn("No orders found with name: {}", orderName);
//            throw new NotFoundException("No orders found with name: " + orderName);
//        }
//
//        return orders;
        return orderRepository.findOrdersByOrderNameEqualsIgnoreCase(orderName);
    }

    @Override
    public Page<Order> listAllOrders(int pageNumber, int pageSize) {
        try {
            Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "updatedDate"));
            return orderRepository.findAll(pageable);
        } catch (Exception ex) {
            log.error("An error occurred while listing all orders:", ex);
            throw new OrderListException("Error occurred while listing orders. Please try again later.");
        }
    }

}
