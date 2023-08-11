package ua.dmitriiev.beautysaloon.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ClientSlimDTO {


    private UUID id;


//    @NotEmpty(message = "Name should not be empty")
//    @Size(min = 3, max = 50, message = "Name should be between 3 and 50 characters")
    private String clientName;


//    @NotEmpty(message = "Phone number should not be empty")
//    @Pattern(regexp = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$",
//            message = "Please enter phone number with : + (country code)")
    private String phoneNumber;

//    @NotEmpty(message = "Email should not be empty")
//    @Email(message = "Please enter valid email")
    private String clientEmail;



    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
