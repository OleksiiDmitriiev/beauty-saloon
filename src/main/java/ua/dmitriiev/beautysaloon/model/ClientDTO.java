package ua.dmitriiev.beautysaloon.model;


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


    private String clientName;


    private String phoneNumber;


    private String clientEmail;

    private List<OrderSlimDTO> orders;


    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
