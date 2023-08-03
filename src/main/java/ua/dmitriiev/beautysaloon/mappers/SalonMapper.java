package ua.dmitriiev.beautysaloon.mappers;


import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.dmitriiev.beautysaloon.entities.Client;
import ua.dmitriiev.beautysaloon.entities.Master;
import ua.dmitriiev.beautysaloon.entities.Order;
import ua.dmitriiev.beautysaloon.entities.Service;
import ua.dmitriiev.beautysaloon.model.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SalonMapper {

    SalonMapper INSTANCE = Mappers.getMapper(SalonMapper.class);

    //Service
    Service serviceDtoToService(ServiceDTO serviceDTO);

    ServiceDTO serviceToServiceDto(Service service);

    @IterableMapping(qualifiedByName = "toServicesDto")
    List<ServiceDTO> servicesToServicesDto(List<Service> services);


    Service serviceSlimDtoToService(ServiceSlimDTO serviceSlimDTO);

    ServiceSlimDTO serviceToServiceSlimDto(Service service);

    @IterableMapping(qualifiedByName = "toServicesDto")
    List<ServiceSlimDTO> servicesToServicesSlimDto(List<Service> services);

    //Master

    Master masterDtoToMaster(MasterDTO masterDTO);

    @Mapping(target = "masterName", source = "masterName")
    MasterDTO masterToMasterDto(Master master);

    List<MasterDTO> mastersToMastersDto(List<Master> masters);

    //NEW
    Master masterSlimDtoToMaster(MasterSlimDTO masterSlimDTO);

    @Mapping(target = "masterName", source = "masterName")
    MasterSlimDTO masterToMasterSlimDto(Master master);

    List<MasterSlimDTO> mastersToMastersSlimDto(List<Master> masters);

    //Client


    Client clientDtoToClient(ClientDTO clientDTO);


    ClientDTO clientToClientDto(Client client);

    List<ClientDTO> clientsToClientsDto(List<Client> clients);



    Client clientSlimDtoToClient(ClientSlimDTO clientSlimDTO);

    ClientSlimDTO clientToClientSlimDto(Client client);

    List<ClientSlimDTO> clientsToClientsSlimDto(List<Client> clients);


    //Orders

    Order orderDtoToOrder(OrderDTO orderDTO);


    OrderDTO orderToOrderDto(Order order);

    List<OrderDTO> ordersToOrdersDto(List<Order> orders);



    Order orderSlimDtoToOrder(OrderSlimDTO orderSlimDTO);


    OrderSlimDTO orderToOrderSlimDto(Order order);

    List<OrderSlimDTO> ordersToOrdersSlimDto(List<Order> orders);

//Orders
    Order orderPostDtoToOrder(OrderPostDTO orderPostDTO);


    OrderPostDTO orderToOrderPostDto(Order order);

    List<OrderPostDTO> ordersToOrdersPostDto(List<Order> orders);

    //
    Service servicePostDtoToService(ServicePostDTO servicePostDTO);

    ServicePostDTO serviceToServicePostDto(Service service);

    @IterableMapping(qualifiedByName = "toServicesDto")
    List<ServicePostDTO> servicesToServicesPostDto(List<Service> services);

}
