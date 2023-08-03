package ua.dmitriiev.beautysaloon.lib;

import lombok.Data;
import ua.dmitriiev.beautysaloon.entities.Master;
import ua.dmitriiev.beautysaloon.entities.Service;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Data
public class RatingCalculation {

    private Master master;

    private Service service;

    public int calculateMasterRating(Master master) {

        Set<Service> services = master.getServices();
//        List<Service> services = master.getServices();

        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < services.size(); i++) {
            list.add(service.getServiceRating());
        }

        double result = list.stream().mapToInt(Integer::intValue).average().getAsDouble();

        return (int) Math.round(result);

    }

    public int calculateServiceRating(Service service) {

        //TODO
        return 0;
    }

    public static void main(String[] args) {
//        List<Service> services = new ArrayList<>(Collections.emptyList());
//
//        Master master1 = Master.builder()
//                .id(UUID.randomUUID())
//                .masterName("Anton Master2023")
//                .services(services)
//                .masterRating(0)
//                .phoneNumber("+38056655")
//                .masterEmail("anton@gmail.com")
//                .createdDate(LocalDateTime.now())
//                .updatedDate(LocalDateTime.now())
//                .build();
//
//        Service service1 = Service.builder()
//                .id(UUID.randomUUID())
//                .serviceName("Autoservice")
//                .masterOwner(master1)
//                .serviceRating(5)
//                .description("HOSTERL LSQLDL")
//                .createdDate(LocalDateTime.now())
//                .updatedDate(LocalDateTime.now())
//                .build();
//
//        Service service2 = Service.builder()
//                .id(UUID.randomUUID())
//                .serviceName("Autoservice")
//                .masterOwner(master1)
//                .serviceRating(4)
//                .description("HOSTERL LSQLDL")
//                .createdDate(LocalDateTime.now())
//                .updatedDate(LocalDateTime.now())
//                .build();
//
//        Service service3 = Service.builder()
//                .id(UUID.randomUUID())
//                .serviceName("Autoservice")
//                .masterOwner(master1)
//                .serviceRating(1)
//                .description("HOSTERL LSQLDL")
//                .createdDate(LocalDateTime.now())
//                .updatedDate(LocalDateTime.now())
//                .build();
//
//        Service service4 = Service.builder()
//                .id(UUID.randomUUID())
//                .serviceName("Autoservice")
//                .masterOwner(master1)
//                .serviceRating(1)
//                .description("HOSTERL LSQLDL")
//                .createdDate(LocalDateTime.now())
//                .updatedDate(LocalDateTime.now())
//                .build();
//
//        services.add(service1);
//        services.add(service2);
//        services.add(service3);
//        services.add(service4);
//
//        RatingCalculation ratingCalculation = new RatingCalculation();
//
//        System.out.println(ratingCalculation.calculateMasterRating(master1));


    }
}
