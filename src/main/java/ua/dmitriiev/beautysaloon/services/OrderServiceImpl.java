package ua.dmitriiev.beautysaloon.services;

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
import ua.dmitriiev.beautysaloon.repositories.OrderRepository;

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

        Optional<Order> foundOrder = orderRepository.findOrderById(id);
        return foundOrder.orElse(null);
    }

    @Transactional
    @Override
    public void saveOrder(Order order) {

        orderRepository.save(order);
    }

    @Transactional
    @Override
    public void updateOrder(UUID id, Order updatedOrder) {
        Order orderToBeUpdated = orderRepository.findOrderById(id)
                .orElseThrow(() -> new NoSuchElementException("Order not found"));

        orderToBeUpdated.setId(updatedOrder.getId());
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
        return foundOrder.orElse(null);

    }

    @Override
    public List<Order> findOrdersByName(String orderName) {
        return orderRepository.findOrdersByOrderNameEqualsIgnoreCase(orderName);
    }

    @Override
    public Page<Order> listAllOrders(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "updatedDate"));
        return orderRepository.findAll(pageable);
    }

}
