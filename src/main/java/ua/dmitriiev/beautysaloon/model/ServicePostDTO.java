package ua.dmitriiev.beautysaloon.model;


import lombok.*;

import java.time.LocalDateTime;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ServicePostDTO {


    private UUID id;


    private String serviceName;

    private UUID masterOwner;


    @Builder.Default
    private int serviceRating = 0;


    private String description;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

}
