package ua.dmitriiev.beautysaloon.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "service")
public class Service {


    @Id
    @Column(name = "service_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 3, max = 50, message = "Name should be between 3 and 50 characters")
    @Column(name = "name")
    private String serviceName;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "master_id", referencedColumnName = "master_id")
    private Master masterOwner;

    @Column(name = "service_rating")
    @Builder.Default
    private int serviceRating = 0;

    @NotEmpty(message = "Description should not be empty")
    @NotBlank(message = "Please enter a valid description")
    @Size(min = 3, max = 200, message = "Description should be between 3 and 300 characters")
    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "serviceOwner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Order> orders = new HashSet<>();

    @Column(name = "created_date")
    @CreationTimestamp
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    @UpdateTimestamp
    private LocalDateTime updatedDate;


}
