package ua.dmitriiev.beautysaloon.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.dmitriiev.beautysaloon.lib.OrderUtils;

import java.time.LocalDateTime;
import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class OrderPostDTO {

    private UUID id;


    private String orderName = OrderUtils.generateRandomOrderName();

//    @NotNull
    @Builder.Default
    private OrderStatus orderStatus = OrderStatus.SCHEDULED;

    private String serviceOwnerId;

    private String clientOwnerId;


    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
