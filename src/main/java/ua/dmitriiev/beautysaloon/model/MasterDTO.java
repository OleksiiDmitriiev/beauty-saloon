package ua.dmitriiev.beautysaloon.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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


    @NotBlank
    @NotNull
    private String masterName;

    private List<ServiceSlimDTO> services;

    private int masterRating;

    @NotBlank
    @NotNull
    private String phoneNumber;

    @NotNull
    @Email
    private String masterEmail;

@NotNull
    private LocalDateTime createdDate ;

@NotNull
    private LocalDateTime updatedDate;

}
