package ua.dmitriiev.beautysaloon.controllers.api;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.dmitriiev.beautysaloon.entities.Order;
import ua.dmitriiev.beautysaloon.mappers.SalonMapperImpl;
import ua.dmitriiev.beautysaloon.model.OrderDTO;
import ua.dmitriiev.beautysaloon.services.OrderService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderRestController {

    private final OrderService orderService;
    private final SalonMapperImpl salonMapper;

    @Autowired
    public OrderRestController(OrderService orderService, SalonMapperImpl salonMapper) {
        this.orderService = orderService;
        this.salonMapper = salonMapper;
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDTO getOrderById(@PathVariable("id") UUID id) {
        Order order = orderService.findOrderById(id);
        return salonMapper.orderToOrderDto(order);
    }


    //TODO
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public OrderDTO getOrderByName(@RequestParam(required = false) String orderName) {
        Order order = orderService.findOrderByName(orderName);
        return salonMapper.orderToOrderDto(order);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDTO> listAllOrders() {
        List<Order> orders = orderService.listOrders();
        return salonMapper.ordersToOrdersDto(orders);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void saveOrder(@RequestBody @Valid OrderDTO orderDTO) {
        Order order = salonMapper.orderDtoToOrder(orderDTO);
        orderService.saveOrder(order);
    }

    @DeleteMapping({"/{id}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrderById(@PathVariable("id") UUID id) {

        orderService.deleteById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateOrderById(@PathVariable("id") UUID id, @Valid @RequestBody OrderDTO orderDTO) {
        Order order = salonMapper.orderDtoToOrder(orderDTO);
        orderService.updateOrder(id, order);
    }


}
