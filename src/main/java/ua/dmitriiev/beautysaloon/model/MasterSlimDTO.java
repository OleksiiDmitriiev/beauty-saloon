package ua.dmitriiev.beautysaloon.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class MasterSlimDTO {

    private UUID id;


    @NotBlank
    @NotNull
    private String masterName;


    private int masterRating;

    @NotBlank
    @NotNull
    private String phoneNumber;

    @NotNull
    @Email
    private String masterEmail;


    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

}
