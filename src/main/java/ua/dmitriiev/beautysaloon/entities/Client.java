package ua.dmitriiev.beautysaloon.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
@Table(name = "client")

public class Client {

    @Id
    @Column(name = "client_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 3, max = 50, message = "Name should be between 3 and 50 characters")
    @Column(name = "name")
    private String clientName;

    @NotEmpty(message = "Phone number should not be empty")
    @Column(name = "phone_number", unique = true)
    @Pattern(regexp = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$",
            message = "Please enter phone number with : + (country code)")
    private String phoneNumber;

    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Please enter valid email")
    @Column(name = "email", unique = true)
    private String clientEmail;

    @OneToMany(mappedBy = "clientOwner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Order> orders = new HashSet<>();

    @Column(name = "created_date")
    @CreationTimestamp
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    @UpdateTimestamp
    private LocalDateTime updatedDate;
}
