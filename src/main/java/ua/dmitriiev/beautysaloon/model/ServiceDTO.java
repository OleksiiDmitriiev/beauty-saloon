package ua.dmitriiev.beautysaloon.model;

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


    private String serviceName;

    private MasterSlimDTO masterOwner;


    @Builder.Default
    private int serviceRating = 0;


    private String description;

    private List<OrderSlimDTO> orders;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;


}
