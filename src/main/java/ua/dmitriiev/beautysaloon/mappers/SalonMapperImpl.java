package ua.dmitriiev.beautysaloon.mappers;


import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;
import ua.dmitriiev.beautysaloon.entities.Client;
import ua.dmitriiev.beautysaloon.entities.Master;
import ua.dmitriiev.beautysaloon.entities.Order;
import ua.dmitriiev.beautysaloon.entities.Service;
import ua.dmitriiev.beautysaloon.model.*;
import ua.dmitriiev.beautysaloon.services.impl.ClientServiceImpl;
import ua.dmitriiev.beautysaloon.services.impl.ServiceServiceImpl;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class SalonMapperImpl implements SalonMapper {

    private final ClientServiceImpl clientService;
    private final ServiceServiceImpl serviceService;

    public SalonMapperImpl(ClientServiceImpl clientService, ServiceServiceImpl serviceService) {
        this.clientService = clientService;
        this.serviceService = serviceService;
    }

    @Override
    public Master masterDtoToMaster(MasterDTO masterDTO) {

        if (masterDTO == null) {
            return null;
        }
        Master master = new Master();

        master.setId(masterDTO.getId());
        master.setMasterName(masterDTO.getMasterName());
        master.setMasterRating(masterDTO.getMasterRating());

        master.setPhoneNumber(masterDTO.getPhoneNumber());
        master.setMasterEmail(masterDTO.getMasterEmail());
        master.setCreatedDate(masterDTO.getCreatedDate());
        master.setUpdatedDate(masterDTO.getUpdatedDate());

        Set<Service> services = new HashSet<>();
        if (masterDTO.getServices() != null) {
            for (ServiceSlimDTO serviceSlimDTO : masterDTO.getServices()) {
                services.add(serviceSlimDtoToService(serviceSlimDTO));
            }
        }
        master.setServices(services);


        return master;
    }

    @Override
    public MasterDTO masterToMasterDto(Master master) {

        if (master == null) {
            return null;
        }
        MasterDTO masterDTO = new MasterDTO();

        masterDTO.setId(master.getId());
        masterDTO.setMasterName(master.getMasterName());
        masterDTO.setMasterRating(master.getMasterRating());
        masterDTO.setPhoneNumber(master.getPhoneNumber());
        masterDTO.setMasterEmail(master.getMasterEmail());
        masterDTO.setCreatedDate(master.getCreatedDate());
        masterDTO.setUpdatedDate(master.getUpdatedDate());

        List<ServiceSlimDTO> serviceDTOs = new ArrayList<>();
        if (master.getServices() != null) {
            for (Service service : master.getServices()) {
                serviceDTOs.add(serviceToServiceSlimDto(service));
            }
        }
        masterDTO.setServices(serviceDTOs);

        return masterDTO;
    }

    @Override
    public List<MasterDTO> mastersToMastersDto(List<Master> masters) {

        if (masters == null) {
            return Collections.emptyList();
        }
        return masters.stream()
                .map(this::masterToMasterDto)
                .collect(Collectors.toList());

    }

    @Override
    public Page<MasterDTO> pageMastersToPageMastersDto(Page<Master> masters) {
        if (masters == null) {
            return null;
        }

        List<MasterDTO> masterDTOList = mastersToMastersDto(masters.getContent());

        return new PageImpl<>(masterDTOList, masters.getPageable(), masters.getTotalElements());
    }


    @Override
    public Master masterSlimDtoToMaster(MasterSlimDTO masterSlimDTO) {


        if (masterSlimDTO == null) {
            return null;
        }
        Master master = new Master();

        master.setId(masterSlimDTO.getId());
        master.setMasterName(masterSlimDTO.getMasterName());
        master.setMasterRating(masterSlimDTO.getMasterRating());
        master.setPhoneNumber(masterSlimDTO.getPhoneNumber());
        master.setMasterEmail(masterSlimDTO.getMasterEmail());
        master.setCreatedDate(masterSlimDTO.getCreatedDate());
        master.setUpdatedDate(masterSlimDTO.getUpdatedDate());


        return master;
    }

    @Override
    public MasterSlimDTO masterToMasterSlimDto(Master master) {


        if (master == null) {
            return null;
        }
        MasterSlimDTO masterSlimDTO = new MasterSlimDTO();

        masterSlimDTO.setId(master.getId());
        masterSlimDTO.setMasterName(master.getMasterName());
        masterSlimDTO.setMasterRating(master.getMasterRating());
        masterSlimDTO.setPhoneNumber(master.getPhoneNumber());
        masterSlimDTO.setMasterEmail(master.getMasterEmail());
        masterSlimDTO.setCreatedDate(master.getCreatedDate());
        masterSlimDTO.setUpdatedDate(master.getUpdatedDate());


        return masterSlimDTO;
    }

    @Override
    public List<MasterSlimDTO> mastersToMastersSlimDto(List<Master> masters) {
        if (masters == null) {
            return null;
        }

        return masters.stream()
                .map(this::masterToMasterSlimDto)
                .collect(Collectors.toList());

    }

    @Override
    public Page<MasterSlimDTO> pageMastersToPageMastersSlimDto(Page<Master> masters) {
        if (masters == null) {
            return null;
        }

        List<MasterSlimDTO> masterDTOList = mastersToMastersSlimDto(masters.getContent());

        return new PageImpl<>(masterDTOList, masters.getPageable(), masters.getTotalElements());
    }

    @Override
    public Client clientDtoToClient(ClientDTO clientDTO) {

        if (clientDTO == null) {
            return null;
        }

        Client client = new Client();
        client.setId(clientDTO.getId());
        client.setClientName(clientDTO.getClientName());
        client.setClientEmail(clientDTO.getClientEmail());
        client.setPhoneNumber(clientDTO.getPhoneNumber());

        Set<Order> orders = new HashSet<>();
        if (clientDTO.getOrders() != null) {
            for (OrderSlimDTO orderSlimDTO : clientDTO.getOrders()) {
                orders.add(orderSlimDtoToOrder(orderSlimDTO));
            }
        }
        client.setOrders(orders);

        client.setCreatedDate(clientDTO.getCreatedDate());
        client.setUpdatedDate(clientDTO.getUpdatedDate());

        return client;
    }

    @Override
    public ClientDTO clientToClientDto(Client client) {

        if (client == null) {
            return null;
        }

        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(client.getId());
        clientDTO.setClientName(client.getClientName());
        clientDTO.setClientEmail(client.getClientEmail());
        clientDTO.setPhoneNumber(client.getPhoneNumber());

        List<OrderSlimDTO> orderSlimDTOs = new ArrayList<>();
        if (client.getOrders() != null) {
            for (Order order : client.getOrders()) {
                orderSlimDTOs.add(orderToOrderSlimDto(order));
            }
        }
        clientDTO.setOrders(orderSlimDTOs);

        clientDTO.setCreatedDate(client.getCreatedDate());
        clientDTO.setUpdatedDate(client.getUpdatedDate());

        return clientDTO;
    }

    @Override
    public List<ClientDTO> clientsToClientsDto(List<Client> clients) {

        if (clients == null) {
            return null;
        }
        return clients.stream()
                .map(this::clientToClientDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ClientDTO> pageClientsToPageClientsDto(Page<Client> clientPage) {
        if (clientPage == null) {
            return null;
        }

        List<ClientDTO> clientDTOList = clientsToClientsDto(clientPage.getContent());

        return new PageImpl<>(clientDTOList, clientPage.getPageable(), clientPage.getTotalElements());
    }


    @Override
    public Client clientSlimDtoToClient(ClientSlimDTO clientSlimDTO) {

        if (clientSlimDTO == null) {
            return null;
        }

        Client client = new Client();
        client.setId(clientSlimDTO.getId());
        client.setClientName(clientSlimDTO.getClientName());
        client.setClientEmail(clientSlimDTO.getClientEmail());
        client.setPhoneNumber(clientSlimDTO.getPhoneNumber());
        client.setCreatedDate(clientSlimDTO.getCreatedDate());
        client.setUpdatedDate(clientSlimDTO.getUpdatedDate());

        return client;
    }

    @Override
    public ClientSlimDTO clientToClientSlimDto(Client client) {

        if (client == null) {
            return null;
        }

        ClientSlimDTO clientSlimDTO = new ClientSlimDTO();
        clientSlimDTO.setId(client.getId());
        clientSlimDTO.setClientName(client.getClientName());
        clientSlimDTO.setClientEmail(client.getClientEmail());
        clientSlimDTO.setPhoneNumber(client.getPhoneNumber());
        clientSlimDTO.setCreatedDate(client.getCreatedDate());
        clientSlimDTO.setUpdatedDate(client.getUpdatedDate());

        return clientSlimDTO;

    }

    @Override
    public List<ClientSlimDTO> clientsToClientsSlimDto(List<Client> clients) {

        if (clients == null) {
            return null;
        }
        return clients.stream()
                .map(this::clientToClientSlimDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ClientSlimDTO> pageClientsToPageClientsSlimDto(Page<Client> clientPage) {
        if (clientPage == null) {
            return null;
        }

        List<ClientSlimDTO> clientSlimDTOList = clientsToClientsSlimDto(clientPage.getContent());

        return new PageImpl<>(clientSlimDTOList, clientPage.getPageable(), clientPage.getTotalElements());
    }

    @Override
    public Order orderDtoToOrder(OrderDTO orderDTO) {

        Order order = new Order();

        order.setId(orderDTO.getId());
        order.setOrderName(orderDTO.getOrderName());
        order.setOrderStatus(orderDTO.getOrderStatus());

        if (orderDTO.getServiceOwner() != null) {
            order.setServiceOwner(serviceSlimDtoToService(orderDTO.getServiceOwner()));
        }

        if (orderDTO.getClientOwner() != null) {
            order.setClientOwner(clientSlimDtoToClient(orderDTO.getClientOwner()));
        }

        order.setUpdatedDate(orderDTO.getUpdatedDate());
        order.setCreatedDate(orderDTO.getCreatedDate());


        return order;
    }

    @Override
    public OrderDTO orderToOrderDto(Order order) {

        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setId(order.getId());
        orderDTO.setOrderName(order.getOrderName());
        orderDTO.setOrderStatus(order.getOrderStatus());


        if (order.getServiceOwner() != null) {
            orderDTO.setServiceOwner(serviceToServiceSlimDto(order.getServiceOwner()));
        }

        if (order.getClientOwner() != null) {
            orderDTO.setClientOwner(clientToClientSlimDto(order.getClientOwner()));
        }

        orderDTO.setUpdatedDate(order.getUpdatedDate());
        orderDTO.setCreatedDate(order.getCreatedDate());


        return orderDTO;
    }

    @Override
    public List<OrderDTO> ordersToOrdersDto(List<Order> orders) {

        if (orders == null) {
            return null;
        }
        return orders.stream()
                .map(this::orderToOrderDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<OrderDTO> pageOrdersToPageOrdersDto(Page<Order> orders) {
        if (orders == null) {
            return null;
        }

        List<OrderDTO> orderDTOList = ordersToOrdersDto(orders.getContent());

        return new PageImpl<>(orderDTOList, orders.getPageable(), orders.getTotalElements());
    }

    @Override
    public Order orderSlimDtoToOrder(OrderSlimDTO orderSlimDTO) {

        Order order = new Order();

        order.setId(orderSlimDTO.getId());
        order.setOrderName(orderSlimDTO.getOrderName());
        order.setOrderStatus(orderSlimDTO.getOrderStatus());
        order.setUpdatedDate(orderSlimDTO.getUpdatedDate());
        order.setCreatedDate(orderSlimDTO.getCreatedDate());

        return order;
    }

    @Override
    public OrderSlimDTO orderToOrderSlimDto(Order order) {

        OrderSlimDTO orderSlimDTO = new OrderSlimDTO();

        orderSlimDTO.setId(order.getId());
        orderSlimDTO.setOrderName(order.getOrderName());
        orderSlimDTO.setOrderStatus(order.getOrderStatus());
        orderSlimDTO.setUpdatedDate(order.getUpdatedDate());
        orderSlimDTO.setCreatedDate(order.getCreatedDate());
        return orderSlimDTO;
    }

    @Override
    public List<OrderSlimDTO> ordersToOrdersSlimDto(List<Order> orders) {

        if (orders == null) {
            return null;
        }
        return orders.stream()
                .map(this::orderToOrderSlimDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<OrderSlimDTO> pageOrdersToPageOrdersSlimDto(Page<Order> orders) {
        if (orders == null) {
            return null;
        }

        List<OrderSlimDTO> orderSlimDTOList = ordersToOrdersSlimDto(orders.getContent());

        return new PageImpl<>(orderSlimDTOList, orders.getPageable(), orders.getTotalElements());
    }


    @Override
    public Order orderPostDtoToOrder(OrderPostDTO orderPostDTO) {

        Order order = new Order();

        order.setId(orderPostDTO.getId());
        order.setOrderName(orderPostDTO.getOrderName());
        order.setOrderStatus(orderPostDTO.getOrderStatus());


        if (orderPostDTO.getServiceOwnerId() != null) {
            Service serviceOwner = serviceService.findServiceById(UUID.fromString(orderPostDTO.getClientOwnerId()));
            order.setServiceOwner(serviceOwner);
            log.info(serviceOwner.getServiceName());
            log.info(serviceOwner.getDescription());
            log.info(serviceOwner.getMasterOwner().getId().toString());
            log.info(serviceOwner.getCreatedDate().toString());
            log.info(serviceOwner.getCreatedDate().toString());

        }


        if (orderPostDTO.getClientOwnerId() != null) {
            Client clientOwner = clientService.findClientById(UUID.fromString(orderPostDTO.getClientOwnerId()));
            order.setClientOwner(clientOwner);
        }

        order.setUpdatedDate(orderPostDTO.getUpdatedDate());
        order.setCreatedDate(orderPostDTO.getCreatedDate());


        return order;
    }

    @Override
    public OrderPostDTO orderToOrderPostDto(Order order) {
        return null;
    }

    @Override
    public List<OrderPostDTO> ordersToOrdersPostDto(List<Order> orders) {
        return null;
    }

    @Override
    public Service servicePostDtoToService(ServicePostDTO servicePostDTO) {
        return null;
    }

    @Override
    public ServicePostDTO serviceToServicePostDto(Service service) {
        return null;
    }

    @Override
    public List<ServicePostDTO> servicesToServicesPostDto(List<Service> services) {
        return null;
    }


    @Override
    public Service serviceDtoToService(ServiceDTO serviceDTO) {

        if (serviceDTO == null) {
            return null;
        }

        Service service = new Service();
        service.setId(serviceDTO.getId());
        service.setServiceName(serviceDTO.getServiceName());
        service.setServiceRating(serviceDTO.getServiceRating());
        service.setDescription(serviceDTO.getDescription());
        if (serviceDTO.getMasterOwner() != null) {
            service.setMasterOwner(masterSlimDtoToMaster(serviceDTO.getMasterOwner()));
        }

        Set<Order> orders = new HashSet<>();
        if (serviceDTO.getOrders() != null) {
            for (OrderSlimDTO orderSlimDTO : serviceDTO.getOrders()) {
                orders.add(orderSlimDtoToOrder(orderSlimDTO));
            }
        }
        service.setOrders(orders);


        return service;
    }


    @Override
    public ServiceDTO serviceToServiceDto(Service service) {
        if (service == null) {
            return null;
        }
        ServiceDTO serviceDTO = new ServiceDTO();
        serviceDTO.setId(service.getId());
        serviceDTO.setServiceName(service.getServiceName());
        if (service.getMasterOwner() != null) {
            serviceDTO.setMasterOwner(masterToMasterSlimDto(service.getMasterOwner()));
        }

        List<OrderSlimDTO> orderSlimDTOs = new ArrayList<>();
        if (service.getOrders() != null) {
            for (Order order : service.getOrders()) {
                orderSlimDTOs.add(orderToOrderSlimDto(order));
            }
        }
        serviceDTO.setOrders(orderSlimDTOs);

        serviceDTO.setServiceRating(service.getServiceRating());
        serviceDTO.setDescription(service.getDescription());
        serviceDTO.setCreatedDate(service.getCreatedDate());
        serviceDTO.setUpdatedDate(service.getUpdatedDate());
        return serviceDTO;
    }


    @Override
    public List<ServiceDTO> servicesToServicesDto(List<Service> services) {

        if (services == null) {
            return null;
        }
        return services.stream()
                .map(this::serviceToServiceDto)
                .collect(Collectors.toList());


    }

    @Override
    public Page<ServiceDTO> pageServicesToPageServicesDto(Page<Service> services) {

        if (services == null) {
            return null;
        }

        List<ServiceDTO> serviceDTOList = servicesToServicesDto(services.getContent());

        return new PageImpl<>(serviceDTOList, services.getPageable(), services.getTotalElements());


    }

    @Override
    public Service serviceSlimDtoToService(ServiceSlimDTO serviceSlimDTO) {

        if (serviceSlimDTO == null) {
            return null;
        }

        Service service = new Service();
        service.setId(serviceSlimDTO.getId());
        service.setServiceName(serviceSlimDTO.getServiceName());
        service.setServiceRating(serviceSlimDTO.getServiceRating());
        service.setDescription(serviceSlimDTO.getDescription());
        service.setCreatedDate(serviceSlimDTO.getCreatedDate());
        service.setUpdatedDate(serviceSlimDTO.getCreatedDate());

        return service;
    }

    @Override
    public ServiceSlimDTO serviceToServiceSlimDto(Service service) {

        if (service == null) {
            return null;
        }
        ServiceSlimDTO serviceSlimDTO = new ServiceSlimDTO();
        serviceSlimDTO.setId(service.getId());
        serviceSlimDTO.setServiceName(service.getServiceName());

        serviceSlimDTO.setServiceRating(service.getServiceRating());
        serviceSlimDTO.setDescription(service.getDescription());
        serviceSlimDTO.setCreatedDate(service.getCreatedDate());
        serviceSlimDTO.setUpdatedDate(service.getUpdatedDate());
        return serviceSlimDTO;
    }

    @Override
    public List<ServiceSlimDTO> servicesToServicesSlimDto(List<Service> services) {
        if (services == null) {
            return null;
        }
        return services.stream()
                .map(this::serviceToServiceSlimDto)
                .collect(Collectors.toList());

    }


    @Override
    public Page<ServiceSlimDTO> pageServicesToPageServicesSlimDto(Page<Service> services) {

        if (services == null) {
            return null;
        }

        List<ServiceSlimDTO> serviceDTOList = servicesToServicesSlimDto(services.getContent());

        return new PageImpl<>(serviceDTOList, services.getPageable(), services.getTotalElements());
    }

}

