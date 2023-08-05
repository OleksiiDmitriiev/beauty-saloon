package ua.dmitriiev.beautysaloon.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.dmitriiev.beautysaloon.entities.Order;
import ua.dmitriiev.beautysaloon.entities.Service;
import ua.dmitriiev.beautysaloon.services.OrderServiceImpl;
import ua.dmitriiev.beautysaloon.services.ServiceService;


import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderServiceImpl orderService;

    private final ServiceService serviceService;


    @Autowired
    public OrderController(OrderServiceImpl orderService, ServiceService serviceService) {
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
    public String show(@PathVariable("id") UUID id, Model model) {
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
    public String create(@ModelAttribute("order") @Valid @RequestBody Order order, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "orders/new";


        if (order.getServiceOwner() == null) {

            return "redirect:/orders/luna";
        }
        orderService.saveOrder(order);
        return "redirect:/orders";

    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") UUID id) {
        model.addAttribute("order", orderService.findOrderById(id));
        return "orders/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("order") @Valid Order order, BindingResult bindingResult,
                         @PathVariable("id") UUID id) {

        if (bindingResult.hasErrors())
            return "orders/edit";

        orderService.updateOrder(id, order);
        return "redirect:/orders";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") UUID id) {
        orderService.deleteById(id);
        return "redirect:/orders";
    }


    @GetMapping("/search")
    public String getOrdersByName(@RequestParam(required = false) String orderName, Model model) {

        model.addAttribute("orders", orderService.findOrdersByName(orderName));

        return "orders/search";
    }
}
