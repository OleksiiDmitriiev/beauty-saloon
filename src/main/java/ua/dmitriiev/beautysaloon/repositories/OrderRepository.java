package ua.dmitriiev.beautysaloon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.dmitriiev.beautysaloon.entities.Order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    Optional<Order> findOrderById(UUID id);

    void deleteOrderById(UUID id);

    Optional<Order> findByOrderNameEqualsIgnoreCase(String orderName);

    List<Order> findOrdersByOrderNameEqualsIgnoreCase(String orderName);
}
