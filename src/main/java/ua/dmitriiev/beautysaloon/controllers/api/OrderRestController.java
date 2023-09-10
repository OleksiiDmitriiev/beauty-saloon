package ua.dmitriiev.beautysaloon.controllers.api;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ua.dmitriiev.beautysaloon.entities.Order;
import ua.dmitriiev.beautysaloon.mappers.SalonMapperImpl;
import ua.dmitriiev.beautysaloon.model.OrderDTO;
import ua.dmitriiev.beautysaloon.services.OrderService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
@Tag(name = "Orders", description = "API endpoints for managing saloon orders")
public class OrderRestController {

    private final OrderService orderService;
    private final SalonMapperImpl salonMapper;

    @Autowired
    public OrderRestController(OrderService orderService, SalonMapperImpl salonMapper) {
        this.orderService = orderService;
        this.salonMapper = salonMapper;
    }


    @GetMapping("/{id}")
    @Operation(summary = "Get order by ID",
            description = "Retrieve an order by unique ID.")
    @ResponseStatus(HttpStatus.OK)
    public OrderDTO getOrderById(@PathVariable("id") UUID id) {
        Order order = orderService.findOrderById(id);
        return salonMapper.orderToOrderDto(order);
    }


    @GetMapping("/search")
    @Operation(summary = "Get order by name",
            description = "Retrieve an order by name.")
    @ResponseStatus(HttpStatus.OK)
    public OrderDTO getOrderByName(@RequestParam(required = false) String orderName) {
        Order order = orderService.findOrderByName(orderName);
        return salonMapper.orderToOrderDto(order);
    }


    @GetMapping()
    @Operation(summary = "Get a list of all orders",
            description = "Retrieve a paginated list of saloon orders.")
    @ResponseStatus(HttpStatus.OK)
    public Page<OrderDTO> listAllOrders(@RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
                                        @RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer pageSize) {
        Page<Order> orders = orderService.listAllOrders(pageNumber, pageSize);
        return salonMapper.pageOrdersToPageOrdersDto(orders);
    }

    @PostMapping()
    @Operation(summary = "Create a new order",
            description = "Create a new order.")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveOrder(@RequestBody @Valid OrderDTO orderDTO) {
        Order order = salonMapper.orderDtoToOrder(orderDTO);
        orderService.saveOrder(order);
    }

    @DeleteMapping({"/{id}"})
    @Operation(summary = "Delete order by ID",
            description = "Delete order by ID")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrderById(@PathVariable("id") UUID id) {

        orderService.deleteById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update order by ID",
            description = "Update order by ID")
    @ResponseStatus(HttpStatus.OK)
    public void updateOrderById(@PathVariable("id") UUID id, @Valid @RequestBody OrderDTO orderDTO) {
        Order order = salonMapper.orderDtoToOrder(orderDTO);
        orderService.updateOrder(id, order);
    }


}
