package ua.dmitriiev.beautysaloon.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class MasterDTO {

    private UUID id;


//    @NotEmpty(message = "Name should not be empty")
//    @NotBlank(message = "Please enter a valid name")
//    @Size(min = 3, max = 50, message = "Name should be between 3 and 50 characters")
    private String masterName;

    private List<ServiceSlimDTO> services;

    private int masterRating;

//    @NotEmpty(message = "Phone number should not be empty")
//    @Pattern(regexp = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$",
//            message = "Please enter phone number with : + (country code)")
    private String phoneNumber;

//    @NotEmpty(message = "Email should not be empty")
//    @Email(message = "Please enter valid email")
    private String masterEmail;


    private LocalDateTime createdDate ;


    private LocalDateTime updatedDate;

}
