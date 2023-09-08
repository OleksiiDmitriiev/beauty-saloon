package ua.dmitriiev.beautysaloon.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ua.dmitriiev.beautysaloon.lib.OrderUtils;
import ua.dmitriiev.beautysaloon.model.OrderStatus;


import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_table")
public class Order {


    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", referencedColumnName = "client_id")
    private Client clientOwner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", referencedColumnName = "service_id")
    private Service serviceOwner;


    @NotEmpty(message = "Name should not be empty")
    @Size(min = 3, max = 50, message = "Name should be between 3 and 50 characters")
    @Column(name = "name")
    private String orderName = OrderUtils.generateRandomOrderName();


    @Column(name = "order_status")
    @Enumerated(value = EnumType.STRING)
    @Builder.Default
    @NotNull(message = "OrderStatus should not be null")
    private OrderStatus orderStatus = OrderStatus.SCHEDULED;

    @Column(name = "created_date")
    @CreationTimestamp
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    @UpdateTimestamp
    private LocalDateTime updatedDate;


}
