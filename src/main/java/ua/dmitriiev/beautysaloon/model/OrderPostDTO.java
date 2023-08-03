package ua.dmitriiev.beautysaloon.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class OrderPostDTO {

    private UUID id;

    @NotBlank
    @NotNull
    private String orderName;

    @NotNull
    @Builder.Default
    private OrderStatus orderStatus = OrderStatus.SCHEDULED;

    private UUID serviceOwner;

    private UUID clientOwner;


    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
