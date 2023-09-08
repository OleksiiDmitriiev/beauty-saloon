package ua.dmitriiev.beautysaloon.model;


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

    private String clientName;


    private String phoneNumber;


    private String clientEmail;


    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
