package ua.dmitriiev.beautysaloon.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ClientDTO {

    private UUID id;


    @NotBlank
    @NotNull
    private String clientName;


    @NotBlank
    @NotNull
    private String phoneNumber;

    @NotNull
    @Email
    private String clientEmail;

    private List<OrderSlimDTO> orders;


    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
