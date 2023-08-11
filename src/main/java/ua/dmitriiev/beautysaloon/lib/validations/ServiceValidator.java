package ua.dmitriiev.beautysaloon.lib.validations;

import lombok.extern.slf4j.Slf4j;
import ua.dmitriiev.beautysaloon.entities.Service;
import ua.dmitriiev.beautysaloon.lib.exceptions.InvalidMasterException;

@Slf4j
public class ServiceValidator {


    public static void validateService(Service service) {

        if (service == null || service.getDescription() == null || service.getServiceName() == null) {

            throw new IllegalArgumentException("Invalid service data provided.");
        }

        if (service.getMasterOwner() == null) {
            log.error("Invalid service data provided: {}", service);
            throw new InvalidMasterException("Invalid master exception");
        }

    }
}
