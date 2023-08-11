package ua.dmitriiev.beautysaloon.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.dmitriiev.beautysaloon.entities.Order;
import ua.dmitriiev.beautysaloon.entities.Service;
import ua.dmitriiev.beautysaloon.lib.exceptions.InvalidClientException;
import ua.dmitriiev.beautysaloon.lib.exceptions.InvalidServiceException;
import ua.dmitriiev.beautysaloon.services.impl.OrderServiceImpl;
import ua.dmitriiev.beautysaloon.services.impl.ServiceServiceImpl;


import java.util.List;
import java.util.UUID;


@Slf4j
@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderServiceImpl orderService;

    private final ServiceServiceImpl serviceService;


    @Autowired
    public OrderController(OrderServiceImpl orderService, ServiceServiceImpl serviceService) {
        this.orderService = orderService;
        this.serviceService = serviceService;
    }


    @GetMapping()
    public String listAllOrders(@RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
                                @RequestParam(value = "pageSize", required = false, defaultValue = "5") Integer pageSize, Model model) {

        if (pageNumber < 0) pageNumber = 0;


        Page<Order> orderPage = orderService.listAllOrders(pageNumber, pageSize);
        List<Order> orders = orderPage.getContent();


        model.addAttribute("orders", orders);
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", orderPage.getTotalPages());
        model.addAttribute("nextPage", pageNumber + 1);

        model.addAttribute("pageSize", pageSize);

        return "orders/index";
    }


    @GetMapping("/{id}")
    public String getOrderById(@PathVariable("id") UUID id, Model model) {
        model.addAttribute("order", orderService.findOrderById(id));


        return "orders/show";
    }

    @GetMapping("/new")
    public String newOrder(@ModelAttribute("order") Order order, @RequestParam(value = "serviceId", required = false) String serviceId) {
        if (serviceId != null) {

            Service serviceOwner = serviceService.findServiceById(UUID.fromString(serviceId));
            order.setServiceOwner(serviceOwner);
            return "orders/new";
        }

        return "orders/new-manual";
    }

    @PostMapping()
    public String createNewOrder(@ModelAttribute("order") @Valid @RequestBody Order order, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "orders/new";


        try {
            orderService.saveOrder(order);
        } catch (IllegalArgumentException ex) {
            log.error("IllegalArgumentException occurred:", ex);
            return "orders/new";
        } catch (InvalidServiceException ex) {
            log.error("InvalidServiceException occurred:", ex);
            bindingResult.rejectValue("serviceOwner", "error.order", ex.getMessage());
            return "orders/new-manual";
        } catch (InvalidClientException ex) {
            log.error("InvalidClientException occurred:", ex);
            bindingResult.rejectValue("clientOwner", "error.order", ex.getMessage());
            return "orders/new";
        }
        return "redirect:/orders";
    }

    @GetMapping("/{id}/edit")
    public String editOrder(Model model, @PathVariable("id") UUID id) {
        model.addAttribute("order", orderService.findOrderById(id));
        return "orders/edit";
    }

    @PatchMapping("/{id}")
    public String updateOrderById(@ModelAttribute("order") @Valid Order order, BindingResult bindingResult,
                                  @PathVariable("id") UUID id) {

        if (bindingResult.hasErrors())
            return "orders/edit";


        try {
            orderService.updateOrder(id, order);
        } catch (IllegalArgumentException ex) {
            log.error("IllegalArgumentException occurred:", ex);

            return "orders/edit";
        } catch (InvalidServiceException ex) {
            log.error("InvalidServiceException occurred:", ex);
            bindingResult.rejectValue("serviceOwner", "error.order", ex.getMessage());
            return "orders/edit";
        } catch (InvalidClientException ex) {
            log.error("InvalidClientException occurred:", ex);
            bindingResult.rejectValue("clientOwner", "error.order", ex.getMessage());
            return "orders/edit";
        }
        return "redirect:/orders";
    }

    @DeleteMapping("/{id}")
    public String deleteOrderById(@PathVariable("id") UUID id) {
        orderService.deleteById(id);
        return "redirect:/orders";
    }


    @GetMapping("/search")
    public String getOrdersByName(@RequestParam(required = false) String orderName, Model model) {

        model.addAttribute("orders", orderService.findOrdersByName(orderName));

        return "orders/search";
    }
}
