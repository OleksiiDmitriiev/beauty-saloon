package ua.dmitriiev.beautysaloon.model;


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


    private String masterName;

    private int masterRating;


    private String phoneNumber;


    private String masterEmail;


    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

}
