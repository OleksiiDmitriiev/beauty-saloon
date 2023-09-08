package ua.dmitriiev.beautysaloon.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.dmitriiev.beautysaloon.lib.exceptions.*;


@Slf4j
@ControllerAdvice
public class CustomErrorController {

    @ExceptionHandler(NotFoundException.class)
    public String handleNotFoundException(NotFoundException ex, Model model) {
        log.error("Not found exception occurred:", ex);
        model.addAttribute("errorMessage", "Not found. Please check ID and try again.");
        return "exceptions/error-page";
    }

    @ExceptionHandler(ClientListException.class)
    public String handleClientListException(ClientListException ex, Model model) {
        log.error("Handle Client list exception occurred:", ex);
        model.addAttribute("errorMessage", "Unable to display list of clients. Please try again later.");
        return "exceptions/error-page";
    }

    @ExceptionHandler(MasterListException.class)
    public String handleMasterListException(MasterListException ex, Model model) {
        log.error("Handle Master list exception occurred:", ex);
        model.addAttribute("errorMessage", "Unable to display list of masters. Please try again later.");
        return "exceptions/error-page";
    }

    @ExceptionHandler(OrderListException.class)
    public String handleOrderListException(OrderListException ex, Model model) {
        log.error("Handle Order list exception occurred:", ex);
        model.addAttribute("errorMessage", "Unable to display list of orders. Please try again later.");
        return "exceptions/error-page";
    }

    @ExceptionHandler(ServiceListException.class)
    public String handleServiceListException(ServiceListException ex, Model model) {
        log.error("Handle Service list exception occurred:", ex);
        model.addAttribute("errorMessage", "Unable to display list of services. Please try again later.");
        return "exceptions/error-page";
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException ex, Model model) {
        log.error("IllegalArgumentException  occurred:", ex);
        model.addAttribute("errorMessage", ex.getMessage());
        return "exceptions/error-page";
    }

    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(RuntimeException ex, Model model) {
        log.error("Runtime exception occurred:", ex);
        model.addAttribute("errorMessage", "An unexpected error occurred. Please try again later.");
        return "exceptions/error-page";
    }
}
