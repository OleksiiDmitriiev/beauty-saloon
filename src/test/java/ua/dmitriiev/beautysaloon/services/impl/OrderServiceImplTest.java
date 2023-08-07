package ua.dmitriiev.beautysaloon.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
import ua.dmitriiev.beautysaloon.entities.Client;
import ua.dmitriiev.beautysaloon.entities.Order;
import ua.dmitriiev.beautysaloon.entities.Service;
import ua.dmitriiev.beautysaloon.lib.exceptions.NotFoundException;

import ua.dmitriiev.beautysaloon.repositories.OrderRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

    }


    @Test
    public void testListOrders() {
        // Create sample orders
        Order order1 = new Order();
        order1.setId(UUID.randomUUID());
        Order order2 = new Order();
        order2.setId(UUID.randomUUID());

        List<Order> orders = new ArrayList<>();
        orders.add(order1);
        orders.add(order2);

        // Mock the behavior of the orderRepository
        when(orderRepository.findAll()).thenReturn(orders);

        // Call the listOrders method
        List<Order> returnedOrders = orderService.listOrders();

        // Assert that the returned list of orders matches the expected list
        assertEquals(orders.size(), returnedOrders.size());
        assertEquals(orders.get(0).getId(), returnedOrders.get(0).getId());
        assertEquals(orders.get(1).getId(), returnedOrders.get(1).getId());
    }


    @Test
    public void testFindOrderById() {
        UUID orderId = UUID.randomUUID();
        Order order = new Order();
        order.setId(orderId);

        // Mock the behavior of the orderRepository
        when(orderRepository.findOrderById(orderId)).thenReturn(Optional.of(order));

        // Call the findOrderById method
        Order returnedOrder = orderService.findOrderById(orderId);

        // Assert that the returned order matches the expected order
        assertEquals(orderId, returnedOrder.getId());
    }

    @Test
    public void testFindOrderByIdNotFound() {
        UUID orderId = UUID.randomUUID();

        // Mock the behavior of the orderRepository to return an empty optional
        when(orderRepository.findOrderById(orderId)).thenReturn(Optional.empty());

        // Assert that a NotFoundException is thrown when calling findOrderById
        assertThrows(NotFoundException.class, () -> orderService.findOrderById(orderId));
    }


    @Test
    public void testSaveOrder_ValidOrder_Success() {
        // Create a valid order
        Client existingClient = new Client();
        existingClient.setId(UUID.randomUUID());

        Service existingService = new Service();
        existingService.setId(UUID.randomUUID());

        Order validOrder = new Order();
        validOrder.setOrderName("Test Order");
        validOrder.setClientOwner(existingClient);
        validOrder.setServiceOwner(existingService);

        // Call saveOrder method
        orderService.saveOrder(validOrder);

        // Verify that the orderRepository method was called
        verify(orderRepository, times(1)).save(validOrder);
    }

    @Test
    public void testSaveOrder_NullOrderData_ThrowsIllegalArgumentException() {
        // Create a null order
        Order nullOrder = null;

        // Call saveOrder method and assert exception
        assertThrows(IllegalArgumentException.class, () -> orderService.saveOrder(nullOrder));

        // Verify that the orderRepository method was not called
        verify(orderRepository, never()).save(nullOrder);
    }

    @Test
    public void testSaveOrder_ValidData() {
        Order validOrder = new Order();
        validOrder.setOrderName("Valid Order");

        // Set other required properties for the valid order
        Client clientOwner = new Client();
        // Set properties for the clientOwner

        Service serviceOwner = new Service();
        // Set properties for the serviceOwner

        validOrder.setClientOwner(clientOwner);
        validOrder.setServiceOwner(serviceOwner);

        orderService.saveOrder(validOrder);

        // Verify that save method of orderRepository was called with validOrder
        verify(orderRepository, times(1)).save(validOrder);
    }

    @Test
    public void testSaveOrderInvalidData() {
        Order invalidOrder = new Order();
        // Do not set required properties

        assertThrows(IllegalArgumentException.class, () -> orderService.saveOrder(invalidOrder));

        // Verify that save method of orderRepository was not called
        verify(orderRepository, never()).save(invalidOrder);
    }


    @Test
    public void testUpdateOrder_ValidData() {
        UUID id = UUID.randomUUID();
        Order existingOrder = new Order();
        existingOrder.setId(id);
        existingOrder.setOrderName("Order1");
        // Set properties for the existingOrder

        Order updatedOrder = new Order();
        updatedOrder.setOrderName("Order2");
        // Set properties for the updatedOrder

        when(orderRepository.findOrderById(id)).thenReturn(Optional.of(existingOrder));

        orderService.updateOrder(id, updatedOrder);

        // Verify that save method of orderRepository was called with the updated existingOrder
        verify(orderRepository, times(1)).save(existingOrder);
    }

    @Test
    public void testUpdateOrderNotFound() {
        UUID id = UUID.randomUUID();
        Order updatedOrder = new Order();
        // Set properties for the updatedOrder

        when(orderRepository.findOrderById(id)).thenReturn(Optional.empty());

        try {
            orderService.updateOrder(id, updatedOrder);
            // Expect an exception to be thrown
            fail("Expected NoSuchElementException");
        } catch (NoSuchElementException e) {
            // Verify that the exception was thrown
            // (You can add more specific checks for the exception message if needed)
        }


        // Verify that save method of orderRepository was not called
        verify(orderRepository, never()).save(any());
    }

    @Test
    public void testDeleteOrderById() {
        UUID idToDelete = UUID.randomUUID(); // Example ID
        Order order = new Order();
        order.setId(idToDelete);

        orderService.deleteById(idToDelete);

        // Verify that deleteServiceById method of serviceRepository was called with the provided ID
        verify(orderRepository, times(1)).deleteOrderById(idToDelete);
    }


    @Test
    public void testFindOrdersByName() {
        String orderName = "Example Order Name";

        List<Order> mockOrderList = new ArrayList<>();
        // Create and add mock Service objects to mockServiceList
        Order order1 = new Order();
        order1.setOrderName(orderName);
        mockOrderList.add(order1);

        Order order2 = new Order();
        order2.setOrderName(orderName);
        mockOrderList.add(order2);

        Order order3 = new Order();
       order3.setOrderName(orderName);
        mockOrderList.add(order3);

        when(orderRepository.findOrdersByOrderNameEqualsIgnoreCase(orderName)).thenReturn(mockOrderList);

        List<Order> result = orderService.findOrdersByName(orderName);

        // Verify that findServicesByServiceNameEqualsIgnoreCase method of serviceRepository was called with serviceName
        verify(orderRepository, times(1)).findOrdersByOrderNameEqualsIgnoreCase(orderName);

        // Compare the expected list size with the actual list size
        assertEquals(mockOrderList.size(), result.size());
    }


    @Test
    public void testListAllOrders() {
        int pageNumber = 0;
        int pageSize = 5;

        List<Order> mockOrderList = new ArrayList<>();
        // Create and add mock Service objects to mockServiceList

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "updatedDate"));
        Page<Order> mockPage = new PageImpl<>(mockOrderList, pageable, mockOrderList.size());

        for (int i = 1; i <= 12; i++) {
            mockOrderList.add(new Order());
        }

        when(orderRepository.findAll(pageable)).thenReturn(mockPage);

        Page<Order> result = orderService.listAllOrders(pageNumber, pageSize);

        // Verify that findAll method of serviceRepository was called with the specified pageable
        verify(orderRepository, times(1)).findAll(pageable);

        // Compare the expected page content with the actual page content
        assertEquals(mockPage.getContent(), result.getContent());
    }

}