package ua.dmitriiev.beautysaloon.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ServiceDTO {

    private UUID id;


//    @NotEmpty(message = "Name should not be empty")
//    @Size(min = 3, max = 50, message = "Name should be between 3 and 50 characters")
    private String serviceName;

    private MasterSlimDTO masterOwner;


    @Builder.Default
    private int serviceRating = 0;


//    @NotEmpty(message = "Description should not be empty")
//    @NotBlank(message = "Please enter a valid description")
//    @Size(min = 3, max = 200, message = "Description should be between 3 and 300 characters")
    private String description;

    private List<OrderSlimDTO> orders;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;


}
