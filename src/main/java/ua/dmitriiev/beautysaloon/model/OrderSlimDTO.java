package ua.dmitriiev.beautysaloon.model;


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
public class OrderSlimDTO {

    private UUID id;


    private String orderName = OrderUtils.generateRandomOrderName();


    @Builder.Default
    private OrderStatus orderStatus = OrderStatus.SCHEDULED;


    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
