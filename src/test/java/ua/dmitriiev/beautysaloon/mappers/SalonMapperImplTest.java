//package ua.dmitriiev.beautysaloon.mappers;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import ua.dmitriiev.beautysaloon.entities.*;
//import ua.dmitriiev.beautysaloon.model.*;
//
//import java.util.*;
//
//class SalonMapperImplTest {
//
//    private final SalonMapper salonMapper = new SalonMapperImpl();
//
//    @Test
//    void testMasterDtoToMaster() {
//        // Arrange
//        MasterDTO masterDTO = new MasterDTO();
//        masterDTO.setId(UUID.randomUUID());
//        masterDTO.setMasterName("John Doe");
//        masterDTO.setMasterRating(4);
//
//        // Act
//        Master result = salonMapper.masterDtoToMaster(masterDTO);
//
//        // Assert
//        Assertions.assertNotNull(result);
//        Assertions.assertEquals(masterDTO.getId(), result.getId());
//        Assertions.assertEquals(masterDTO.getMasterName(), result.getMasterName());
//        Assertions.assertEquals(masterDTO.getMasterRating(), result.getMasterRating());
//    }
//
//    @Test
//    void testMasterToMasterDto() {
//        // Arrange
//        Master master = new Master();
//        master.setId(UUID.randomUUID());
//        master.setMasterName("John Doe");
//        master.setMasterRating(4);
//
//        // Act
//        MasterDTO result = salonMapper.masterToMasterDto(master);
//
//        // Assert
//        Assertions.assertNotNull(result);
//        Assertions.assertEquals(master.getId(), result.getId());
//        Assertions.assertEquals(master.getMasterName(), result.getMasterName());
//        Assertions.assertEquals(master.getMasterRating(), result.getMasterRating());
//    }
//
//    @Test
//    void testMastersToMastersDto() {
//        // Arrange
//        List<Master> masters = new ArrayList<>();
//        Master master1 = new Master();
//        master1.setId(UUID.randomUUID());
//        master1.setMasterName("John Doe");
//        master1.setMasterRating(4);
//        masters.add(master1);
//
//        Master master2 = new Master();
//        master2.setId(UUID.randomUUID());
//        master2.setMasterName("Jane Smith");
//        master2.setMasterRating(4);
//        masters.add(master2);
//
//        // Act
//        List<MasterDTO> result = salonMapper.mastersToMastersDto(masters);
//
//        // Assert
//        Assertions.assertNotNull(result);
//        Assertions.assertEquals(2, result.size());
//
//        MasterDTO resultMaster1 = result.get(0);
//        Assertions.assertEquals(master1.getId(), resultMaster1.getId());
//        Assertions.assertEquals(master1.getMasterName(), resultMaster1.getMasterName());
//        Assertions.assertEquals(master1.getMasterRating(), resultMaster1.getMasterRating());
//
//        MasterDTO resultMaster2 = result.get(1);
//        Assertions.assertEquals(master2.getId(), resultMaster2.getId());
//        Assertions.assertEquals(master2.getMasterName(), resultMaster2.getMasterName());
//        Assertions.assertEquals(master2.getMasterRating(), resultMaster2.getMasterRating());
//    }
//
//    @Test
//    void testMasterSlimDtoToMaster() {
//        // Arrange
//        MasterSlimDTO masterSlimDTO = new MasterSlimDTO();
//        masterSlimDTO.setId(UUID.randomUUID());
//        masterSlimDTO.setMasterName("John Doe");
//        masterSlimDTO.setMasterRating(4);
//
//        // Act
//        Master result = salonMapper.masterSlimDtoToMaster(masterSlimDTO);
//
//        // Assert
//        Assertions.assertNotNull(result);
//        Assertions.assertEquals(masterSlimDTO.getId(), result.getId());
//        Assertions.assertEquals(masterSlimDTO.getMasterName(), result.getMasterName());
//        Assertions.assertEquals(masterSlimDTO.getMasterRating(), result.getMasterRating());
//    }
//
//    @Test
//    void testMasterToMasterSlimDto() {
//        // Arrange
//        Master master = new Master();
//        master.setId(UUID.randomUUID());
//        master.setMasterName("John Doe");
//        master.setMasterRating(4);
//
//        // Act
//        MasterSlimDTO result = salonMapper.masterToMasterSlimDto(master);
//
//        // Assert
//        Assertions.assertNotNull(result);
//        Assertions.assertEquals(master.getId(), result.getId());
//        Assertions.assertEquals(master.getMasterName(), result.getMasterName());
//        Assertions.assertEquals(master.getMasterRating(), result.getMasterRating());
//    }
//
//    @Test
//    void testMastersToMastersSlimDto() {
//        // Arrange
//        List<Master> masters = new ArrayList<>();
//        Master master1 = new Master();
//        master1.setId(UUID.randomUUID());
//        master1.setMasterName("John Doe");
//        master1.setMasterRating(4);
//        masters.add(master1);
//
//        Master master2 = new Master();
//        master2.setId(UUID.randomUUID());
//        master2.setMasterName("Jane Smith");
//        master2.setMasterRating(4.8);
//        masters.add(master2);
//
//        // Act
//        List<MasterSlimDTO> result = salonMapper.mastersToMastersSlimDto(masters);
//
//        // Assert
//        Assertions.assertNotNull(result);
//        Assertions.assertEquals(2, result.size());
//
//        MasterSlimDTO resultMaster1 = result.get(0);
//        Assertions.assertEquals(master1.getId(), resultMaster1.getId());
//        Assertions.assertEquals(master1.getMasterName(), resultMaster1.getMasterName());
//        Assertions.assertEquals(master1.getMasterRating(), resultMaster1.getMasterRating());
//
//        MasterSlimDTO resultMaster2 = result.get(1);
//        Assertions.assertEquals(master2.getId(), resultMaster2.getId());
//        Assertions.assertEquals(master2.getMasterName(), resultMaster2.getMasterName());
//        Assertions.assertEquals(master2.getMasterRating(), resultMaster2.getMasterRating());
//    }
//
//    @Test
//    void testClientDtoToClient() {
//        // Arrange
//        ClientDTO clientDTO = new ClientDTO();
//        clientDTO.setId(UUID.randomUUID());
//        clientDTO.setClientName("John Doe");
//        clientDTO.setClientEmail("john.doe@example.com");
//        clientDTO.setPhoneNumber("1234567890");
//
//        // Act
//        Client result = salonMapper.clientDtoToClient(clientDTO);
//
//        // Assert
//        Assertions.assertNotNull(result);
//        Assertions.assertEquals(clientDTO.getId(), result.getId());
//        Assertions.assertEquals(clientDTO.getClientName(), result.getClientName());
//        Assertions.assertEquals(clientDTO.getClientEmail(), result.getClientEmail());
//        Assertions.assertEquals(clientDTO.getPhoneNumber(), result.getPhoneNumber());
//    }
//
//    @Test
//    void testClientToClientDto() {
//        // Arrange
//        Client client = new Client();
//        client.setId(UUID.randomUUID());
//        client.setClientName("John Doe");
//        client.setClientEmail("john.doe@example.com");
//        client.setPhoneNumber("1234567890");
//
//        // Act
//        ClientDTO result = salonMapper.clientToClientDto(client);
//
//        // Assert
//        Assertions.assertNotNull(result);
//        Assertions.assertEquals(client.getId(), result.getId());
//        Assertions.assertEquals(client.getClientName(), result.getClientName());
//        Assertions.assertEquals(client.getClientEmail(), result.getClientEmail());
//        Assertions.assertEquals(client.getPhoneNumber(), result.getPhoneNumber());
//    }
//
//    @Test
//    void testClientsToClientsDto() {
//        // Arrange
//        List<Client> clients = new ArrayList<>();
//        Client client1 = new Client();
//        client1.setId(UUID.randomUUID());
//        client1.setClientName("John Doe");
//        client1.setClientEmail("john.doe@example.com");
//        client1.setPhoneNumber("1234567890");
//        clients.add(client1);
//
//        Client client2 = new Client();
//        client2.setId(UUID.randomUUID());
//        client2.setClientName("Jane Smith");
//        client2.setClientEmail("jane.smith@example.com");
//        client2.setPhoneNumber("9876543210");
//        clients.add(client2);
//
//        // Act
//        List<ClientDTO> result = salonMapper.clientsToClientsDto(clients);
//
//        // Assert
//        Assertions.assertNotNull(result);
//        Assertions.assertEquals(2, result.size());
//
//        ClientDTO resultClient1 = result.get(0);
//        Assertions.assertEquals(client1.getId(), resultClient1.getId());
//        Assertions.assertEquals(client1.getClientName(), resultClient1.getClientName());
//        Assertions.assertEquals(client1.getClientEmail(), resultClient1.getClientEmail());
//        Assertions.assertEquals(client1.getPhoneNumber(), resultClient1.getPhoneNumber());
//
//        ClientDTO resultClient2 = result.get(1);
//        Assertions.assertEquals(client2.getId(), resultClient2.getId());
//        Assertions.assertEquals(client2.getClientName(), resultClient2.getClientName());
//        Assertions.assertEquals(client2.getClientEmail(), resultClient2.getClientEmail());
//        Assertions.assertEquals(client2.getPhoneNumber(), resultClient2.getPhoneNumber());
//    }
//
//    @Test
//    void testClientSlimDtoToClient() {
//        // Arrange
//        ClientSlimDTO clientSlimDTO = new ClientSlimDTO();
//        clientSlimDTO.setId(UUID.randomUUID());
//        clientSlimDTO.setClientName("John Doe");
//        clientSlimDTO.setClientEmail("john.doe@example.com");
//        clientSlimDTO.setPhoneNumber("1234567890");
//
//        // Act
//        Client result = salonMapper.clientSlimDtoToClient(clientSlimDTO);
//
//        // Assert
//        Assertions.assertNotNull(result);
//        Assertions.assertEquals(clientSlimDTO.getId(), result.getId());
//        Assertions.assertEquals(clientSlimDTO.getClientName(), result.getClientName());
//        Assertions.assertEquals(clientSlimDTO.getClientEmail(), result.getClientEmail());
//        Assertions.assertEquals(clientSlimDTO.getPhoneNumber(), result.getPhoneNumber());
//    }
//
//    @Test
//    void testClientToClientSlimDto() {
//        // Arrange
//        Client client = new Client();
//        client.setId(UUID.randomUUID());
//        client.setClientName("John Doe");
//        client.setClientEmail("john.doe@example.com");
//        client.setPhoneNumber("1234567890");
//
//        // Act
//        ClientSlimDTO result = salonMapper.clientToClientSlimDto(client);
//
//        // Assert
//        Assertions.assertNotNull(result);
//        Assertions.assertEquals(client.getId(), result.getId());
//        Assertions.assertEquals(client.getClientName(), result.getClientName());
//        Assertions.assertEquals(client.getClientEmail(), result.getClientEmail());
//        Assertions.assertEquals(client.getPhoneNumber(), result.getPhoneNumber());
//    }
//
//    @Test
//    void testClientsToClientsSlimDto() {
//        // Arrange
//        List<Client> clients = new ArrayList<>();
//        Client client1 = new Client();
//        client1.setId(UUID.randomUUID());
//        client1.setClientName("John Doe");
//        client1.setClientEmail("john.doe@example.com");
//        client1.setPhoneNumber("1234567890");
//        clients.add(client1);
//
//        Client client2 = new Client();
//        client2.setId(UUID.randomUUID());
//        client2.setClientName("Jane Smith");
//        client2.setClientEmail("jane.smith@example.com");
//        client2.setPhoneNumber("9876543210");
//        clients.add(client2);
//
//        // Act
//        List<ClientSlimDTO> result = salonMapper.clientsToClientsSlimDto(clients);
//
//        // Assert
//        Assertions.assertNotNull(result);
//        Assertions.assertEquals(2, result.size());
//
//        ClientSlimDTO resultClient1 = result.get(0);
//        Assertions.assertEquals(client1.getId(), resultClient1.getId());
//        Assertions.assertEquals(client1.getClientName(), resultClient1.getClientName());
//        Assertions.assertEquals(client1.getClientEmail(), resultClient1.getClientEmail());
//        Assertions.assertEquals(client1.getPhoneNumber(), resultClient1.getPhoneNumber());
//
//        ClientSlimDTO resultClient2 = result.get(1);
//        Assertions.assertEquals(client2.getId(), resultClient2.getId());
//        Assertions.assertEquals(client2.getClientName(), resultClient2.getClientName());
//        Assertions.assertEquals(client2.getClientEmail(), resultClient2.getClientEmail());
//        Assertions.assertEquals(client2.getPhoneNumber(), resultClient2.getPhoneNumber());
//    }
//
//    @Test
//    void testOrderDtoToOrder() {
//        // Arrange
//        OrderDTO orderDTO = new OrderDTO();
//        orderDTO.setId(UUID.randomUUID());
//        orderDTO.setOrderName("Test Order");
//        orderDTO.setOrderStatus("Pending");
//
//        ServiceSlimDTO serviceSlimDTO = new ServiceSlimDTO();
//        serviceSlimDTO.setId(UUID.randomUUID());
//        serviceSlimDTO.setServiceName("Test Service");
//        orderDTO.setServiceOwner(serviceSlimDTO);
//
//        ClientSlimDTO clientSlimDTO = new ClientSlimDTO();
//        clientSlimDTO.setId(UUID.randomUUID());
//        clientSlimDTO.setClientName("John Doe");
//        orderDTO.setClientOwner(clientSlimDTO);
//
//        // Act
//        Order result = salonMapper.orderDtoToOrder(orderDTO);
//
//        // Assert
//        Assertions.assertNotNull(result);
//        Assertions.assertEquals(orderDTO.getId(), result.getId());
//        Assertions.assertEquals(orderDTO.getOrderName(), result.getOrderName());
//        Assertions.assertEquals(orderDTO.getOrderStatus(), result.getOrderStatus());
//        Assertions.assertNotNull(result.getServiceOwner());
//        Assertions.assertEquals(serviceSlimDTO.getId(), result.getServiceOwner().getId());
//        Assertions.assertEquals(serviceSlimDTO.getServiceName(), result.getServiceOwner().getServiceName());
//        Assertions.assertNotNull(result.getClientOwner());
//        Assertions.assertEquals(clientSlimDTO.getId(), result.getClientOwner().getId());
//        Assertions.assertEquals(clientSlimDTO.getClientName(), result.getClientOwner().getClientName());
//    }
//
//    @Test
//    void testOrderToOrderDto() {
//        // Arrange
//        Order order = new Order();
//        order.setId(UUID.randomUUID());
//        order.setOrderName("Test Order");
//        order.setOrderStatus("Pending");
//
//        Service serviceOwner = new Service();
//        serviceOwner.setId(UUID.randomUUID());
//        serviceOwner.setServiceName("Test Service");
//        order.setServiceOwner(serviceOwner);
//
//        Client clientOwner = new Client();
//        clientOwner.setId(UUID.randomUUID());
//        clientOwner.setClientName("John Doe");
//        order.setClientOwner(clientOwner);
//
//        // Act
//        OrderDTO result = salonMapper.orderToOrderDto(order);
//
//        // Assert
//        Assertions.assertNotNull(result);
//        Assertions.assertEquals(order.getId(), result.getId());
//        Assertions.assertEquals(order.getOrderName(), result.getOrderName());
//        Assertions.assertEquals(order.getOrderStatus(), result.getOrderStatus());
//        Assertions.assertNotNull(result.getServiceOwner());
//        Assertions.assertEquals(serviceOwner.getId(), result.getServiceOwner().getId());
//        Assertions.assertEquals(serviceOwner.getServiceName(), result.getServiceOwner().getServiceName());
//        Assertions.assertNotNull(result.getClientOwner());
//        Assertions.assertEquals(clientOwner.getId(), result.getClientOwner().getId());
//        Assertions.assertEquals(clientOwner.getClientName(), result.getClientOwner().getClientName());
//    }
//
//    @Test
//    void testOrdersToOrdersDto() {
//        // Arrange
//        List<Order> orders = new ArrayList<>();
//        Order order1 = new Order();
//        order1.setId(UUID.randomUUID());
//        order1.setOrderName("Test Order 1");
//        order1.setOrderStatus("Pending");
//        orders.add(order1);
//
//        Order order2 = new Order();
//        order2.setId(UUID.randomUUID());
//        order2.setOrderName("Test Order 2");
//        order2.setOrderStatus("Completed");
//        orders.add(order2);
//
//        // Act
//        List<OrderDTO> result = salonMapper.ordersToOrdersDto(orders);
//
//        // Assert
//        Assertions.assertNotNull(result);
//        Assertions.assertEquals(2, result.size());
//
//        OrderDTO resultOrder1 = result.get(0);
//        Assertions.assertEquals(order1.getId(), resultOrder1.getId());
//        Assertions.assertEquals(order1.getOrderName(), resultOrder1.getOrderName());
//        Assertions.assertEquals(order1.getOrderStatus(), resultOrder1.getOrderStatus());
//
//        OrderDTO resultOrder2 = result.get(1);
//        Assertions.assertEquals(order2.getId(), resultOrder2.getId());
//        Assertions.assertEquals(order2.getOrderName(), resultOrder2.getOrderName());
//        Assertions.assertEquals(order2.getOrderStatus(), resultOrder2.getOrderStatus());
//    }
//
//    @Test
//    void testOrderSlimDtoToOrder() {
//        // Arrange
//        OrderSlimDTO orderSlimDTO = new OrderSlimDTO();
//        orderSlimDTO.setId(UUID.randomUUID());
//        orderSlimDTO.setOrderName("Test Order");
//        orderSlimDTO.setOrderStatus("Pending");
//
//        // Act
//        Order result = salonMapper.orderSlimDtoToOrder(orderSlimDTO);
//
//        // Assert
//        Assertions.assertNotNull(result);
//        Assertions.assertEquals(orderSlimDTO.getId(), result.getId());
//        Assertions.assertEquals(orderSlimDTO.getOrderName(), result.getOrderName());
//        Assertions.assertEquals(orderSlimDTO.getOrderStatus(), result.getOrderStatus());
//    }
//
//    @Test
//    void testOrderToOrderSlimDto() {
//        // Arrange
//        Order order = new Order();
//        order.setId(UUID.randomUUID());
//        order.setOrderName("Test Order");
//        order.setOrderStatus("Pending");
//
//        // Act
//        OrderSlimDTO result = salonMapper.orderToOrderSlimDto(order);
//
//        // Assert
//        Assertions.assertNotNull(result);
//        Assertions.assertEquals(order.getId(), result.getId());
//        Assertions.assertEquals(order.getOrderName(), result.getOrderName());
//        Assertions.assertEquals(order.getOrderStatus(), result.getOrderStatus());
//    }
//
//    @Test
//    void testOrdersToOrdersSlimDto() {
//        // Arrange
//        List<Order> orders = new ArrayList<>();
//        Order order1 = new Order();
//        order1.setId(UUID.randomUUID());
//        order1.setOrderName("Test Order 1");
//        order1.setOrderStatus("Pending");
//        orders.add(order1);
//
//        Order order2 = new Order();
//        order2.setId(UUID.randomUUID());
//        order2.setOrderName("Test Order 2");
//        order2.setOrderStatus("Completed");
//        orders.add(order2);
//
//        // Act
//        List<OrderSlimDTO> result = salonMapper.ordersToOrdersSlimDto(orders);
//
//        // Assert
//        Assertions.assertNotNull(result);
//        Assertions.assertEquals(2, result.size());
//
//        OrderSlimDTO resultOrder1 = result.get(0);
//        Assertions.assertEquals(order1.getId(), resultOrder1.getId());
//        Assertions.assertEquals(order1.getOrderName(), resultOrder1.getOrderName());
//        Assertions.assertEquals(order1.getOrderStatus(), resultOrder1.getOrderStatus());
//
//        OrderSlimDTO resultOrder2 = result.get(1);
//        Assertions.assertEquals(order2.getId(), resultOrder2.getId());
//        Assertions.assertEquals(order2.getOrderName(), resultOrder2.getOrderName());
//        Assertions.assertEquals(order2.getOrderStatus(), resultOrder2.getOrderStatus());
//    }
//
//    @Test
//    void testServiceDtoToService() {
//        // Arrange
//        ServiceDTO serviceDTO = new ServiceDTO();
//        serviceDTO.setId(UUID.randomUUID());
//        serviceDTO.setServiceName("Test Service");
//        serviceDTO.setServiceRating(4.5);
//
//        // Act
//        Service result = salonMapper.serviceDtoToService(serviceDTO);
//
//        // Assert
//        Assertions.assertNotNull(result);
//        Assertions.assertEquals(serviceDTO.getId(), result.getId());
//        Assertions.assertEquals(serviceDTO.getServiceName(), result.getServiceName());
//        Assertions.assertEquals(serviceDTO.getServiceRating(), result.getServiceRating());
//    }
//
//    @Test
//    void testServiceToServiceDto() {
//        // Arrange
//        Service service = new Service();
//        service.setId(UUID.randomUUID());
//        service.setServiceName("Test Service");
//        service.setServiceRating(4.5);
//
//        // Act
//        ServiceDTO result = salonMapper.serviceToServiceDto(service);
//
//        // Assert
//        Assertions.assertNotNull(result);
//        Assertions.assertEquals(service.getId(), result.getId());
//        Assertions.assertEquals(service.getServiceName(), result.getServiceName());
//        Assertions.assertEquals(service.getServiceRating(), result.getServiceRating());
//    }
//
//    @Test
//    void testServicesToServicesDto() {
//        // Arrange
//        List<Service> services = new ArrayList<>();
//        Service service1 = new Service();
//        service1.setId(UUID.randomUUID());
//        service1.setServiceName("Test Service 1");
//        service1.setServiceRating(4.5);
//        services.add(service1);
//
//        Service service2 = new Service();
//        service2.setId(UUID.randomUUID());
//        service2.setServiceName("Test Service 2");
//        service2.setServiceRating(4.8);
//        services.add(service2);
//
//        // Act
//        List<ServiceDTO> result = salonMapper.servicesToServicesDto(services);
//
//        // Assert
//        Assertions.assertNotNull(result);
//        Assertions.assertEquals(2, result.size());
//
//        ServiceDTO resultService1 = result.get(0);
//        Assertions.assertEquals(service1.getId(), resultService1.getId());
//        Assertions.assertEquals(service1.getServiceName(), resultService1.getServiceName());
//        Assertions.assertEquals(service1.getServiceRating(), resultService1.getServiceRating());
//
//        ServiceDTO resultService2 = result.get(1);
//        Assertions.assertEquals(service2.getId(), resultService2.getId());
//        Assertions.assertEquals(service2.getServiceName(), resultService2.getServiceName());
//        Assertions.assertEquals(service2.getServiceRating(), resultService2.getServiceRating());
//    }
//
//    @Test
//    void testServiceSlimDtoToService() {
//        // Arrange
//        ServiceSlimDTO serviceSlimDTO = new ServiceSlimDTO();
//        serviceSlimDTO.setId(UUID.randomUUID());
//        serviceSlimDTO.setServiceName("Test Service");
//        serviceSlimDTO.setServiceRating(4.5);
//
//        // Act
//        Service result = salonMapper.serviceSlimDtoToService(serviceSlimDTO);
//
//        // Assert
//        Assertions.assertNotNull(result);
//        Assertions.assertEquals(serviceSlimDTO.getId(), result.getId());
//        Assertions.assertEquals(serviceSlimDTO.getServiceName(), result.getServiceName());
//        Assertions.assertEquals(serviceSlimDTO.getServiceRating(), result.getServiceRating());
//    }
//
//    @Test
//    void testServiceToServiceSlimDto() {
//        // Arrange
//        Service service = new Service();
//        service.setId(UUID.randomUUID());
//        service.setServiceName("Test Service");
//        service.setServiceRating(4.5);
//
//        // Act
//        ServiceSlimDTO result = salonMapper.serviceToServiceSlimDto(service);
//
//        // Assert
//        Assertions.assertNotNull(result);
//        Assertions.assertEquals(service.getId(), result.getId());
//        Assertions.assertEquals(service.getServiceName(), result.getServiceName());
//        Assertions.assertEquals(service.getServiceRating(), result.getServiceRating());
//    }
//
//    @Test
//    void testServicesToServicesSlimDto() {
//        // Arrange
//        List<Service> services = new ArrayList<>();
//        Service service1 = new Service();
//        service1.setId(UUID.randomUUID());
//        service1.setServiceName("Test Service 1");
//        service1.setServiceRating(4.5);
//        services.add(service1);
//
//        Service service2 = new Service();
//        service2.setId(UUID.randomUUID());
//        service2.setServiceName("Test Service 2");
//        service2.setServiceRating(4.8);
//        services.add(service2);
//
//        // Act
//        List<ServiceSlimDTO> result = salonMapper.servicesToServicesSlimDto(services);
//
//        // Assert
//        Assertions.assertNotNull(result);
//        Assertions.assertEquals(2, result.size());
//
//        ServiceSlimDTO resultService1 = result.get(0);
//        Assertions.assertEquals(service1.getId(), resultService1.getId());
//        Assertions.assertEquals(service1.getServiceName(), resultService1.getServiceName());
//        Assertions.assertEquals(service1.getServiceRating(), resultService1.getServiceRating());
//
//        ServiceSlimDTO resultService2 = result.get(1);
//        Assertions.assertEquals(service2.getId(), resultService2.getId());
//        Assertions.assertEquals(service2.getServiceName(), resultService2.getServiceName());
//        Assertions.assertEquals(service2.getServiceRating(), resultService2.getServiceRating());
//    }
//}
