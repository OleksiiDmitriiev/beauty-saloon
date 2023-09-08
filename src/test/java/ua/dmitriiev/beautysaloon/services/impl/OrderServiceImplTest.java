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

        Order order1 = new Order();
        order1.setId(UUID.randomUUID());
        Order order2 = new Order();
        order2.setId(UUID.randomUUID());

        List<Order> orders = new ArrayList<>();
        orders.add(order1);
        orders.add(order2);


        when(orderRepository.findAll()).thenReturn(orders);


        List<Order> returnedOrders = orderService.listOrders();


        assertEquals(orders.size(), returnedOrders.size());
        assertEquals(orders.get(0).getId(), returnedOrders.get(0).getId());
        assertEquals(orders.get(1).getId(), returnedOrders.get(1).getId());
    }


    @Test
    public void testFindOrderById() {
        UUID orderId = UUID.randomUUID();
        Order order = new Order();
        order.setId(orderId);


        when(orderRepository.findOrderById(orderId)).thenReturn(Optional.of(order));


        Order returnedOrder = orderService.findOrderById(orderId);


        assertEquals(orderId, returnedOrder.getId());
    }

    @Test
    public void testFindOrderByIdNotFound() {
        UUID orderId = UUID.randomUUID();


        when(orderRepository.findOrderById(orderId)).thenReturn(Optional.empty());


        assertThrows(NotFoundException.class, () -> orderService.findOrderById(orderId));
    }


    @Test
    public void testSaveOrder_ValidOrder_Success() {

        Client existingClient = new Client();
        existingClient.setId(UUID.randomUUID());

        Service existingService = new Service();
        existingService.setId(UUID.randomUUID());

        Order validOrder = new Order();
        validOrder.setOrderName("Test Order");
        validOrder.setClientOwner(existingClient);
        validOrder.setServiceOwner(existingService);


        orderService.saveOrder(validOrder);


        verify(orderRepository, times(1)).save(validOrder);
    }

    @Test
    public void testSaveOrder_NullOrderData_ThrowsIllegalArgumentException() {

        Order nullOrder = null;


        assertThrows(IllegalArgumentException.class, () -> orderService.saveOrder(nullOrder));


        verify(orderRepository, never()).save(nullOrder);
    }

    @Test
    public void testSaveOrder_ValidData() {
        Order validOrder = new Order();
        validOrder.setOrderName("Valid Order");


        Client clientOwner = new Client();


        Service serviceOwner = new Service();


        validOrder.setClientOwner(clientOwner);
        validOrder.setServiceOwner(serviceOwner);

        orderService.saveOrder(validOrder);


        verify(orderRepository, times(1)).save(validOrder);
    }

    @Test
    public void testSaveOrderInvalidData() {
        Order invalidOrder = new Order();


        assertThrows(IllegalArgumentException.class, () -> orderService.saveOrder(invalidOrder));


        verify(orderRepository, never()).save(invalidOrder);
    }


    @Test
    public void testUpdateOrder_ValidData() {
        UUID id = UUID.randomUUID();
        Order existingOrder = new Order();
        existingOrder.setId(id);
        existingOrder.setOrderName("Order1");


        Order updatedOrder = new Order();
        updatedOrder.setOrderName("Order2");


        when(orderRepository.findOrderById(id)).thenReturn(Optional.of(existingOrder));

        orderService.updateOrder(id, updatedOrder);


        verify(orderRepository, times(1)).save(existingOrder);
    }

    @Test
    public void testUpdateOrderNotFound() {
        UUID id = UUID.randomUUID();
        Order updatedOrder = new Order();


        when(orderRepository.findOrderById(id)).thenReturn(Optional.empty());

        try {
            orderService.updateOrder(id, updatedOrder);

            fail("Expected NoSuchElementException");
        } catch (NoSuchElementException e) {

        }


        verify(orderRepository, never()).save(any());
    }

    @Test
    public void testDeleteOrderById() {
        UUID idToDelete = UUID.randomUUID();
        Order order = new Order();
        order.setId(idToDelete);

        orderService.deleteById(idToDelete);


        verify(orderRepository, times(1)).deleteOrderById(idToDelete);
    }


    @Test
    public void testFindOrdersByName() {
        String orderName = "Example Order Name";

        List<Order> mockOrderList = new ArrayList<>();

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


        verify(orderRepository, times(1)).findOrdersByOrderNameEqualsIgnoreCase(orderName);


        assertEquals(mockOrderList.size(), result.size());
    }


    @Test
    public void testListAllOrders() {
        int pageNumber = 0;
        int pageSize = 5;

        List<Order> mockOrderList = new ArrayList<>();


        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "updatedDate"));
        Page<Order> mockPage = new PageImpl<>(mockOrderList, pageable, mockOrderList.size());

        for (int i = 1; i <= 12; i++) {
            mockOrderList.add(new Order());
        }

        when(orderRepository.findAll(pageable)).thenReturn(mockPage);

        Page<Order> result = orderService.listAllOrders(pageNumber, pageSize);


        verify(orderRepository, times(1)).findAll(pageable);


        assertEquals(mockPage.getContent(), result.getContent());
    }

}