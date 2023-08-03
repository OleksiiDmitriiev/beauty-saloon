package ua.dmitriiev.beautysaloon.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;


import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
//@Table(name = "master")
@Table(name = "master")
//        uniqueConstraints = {
//                @UniqueConstraint(columnNames = {"email"}, name = "uk_master_email"),
//                @UniqueConstraint(columnNames = {"phone_number"}, name = "uk_master_phone_number")

//        }
//)
public class Master {

    @Id
    @Column(name = "master_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


    @NotEmpty(message = "Name should not be empty")
    @NotBlank(message = "Please enter a valid name")
    @Size(min = 3, max = 50, message = "Name should be between 3 and 50 characters")
    @Column(name = "name")
    private String masterName;

    @JsonIgnore
    @OneToMany(mappedBy = "masterOwner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Service> services = new HashSet<>();
//    private List<Service> services = List.of();


    @Column(name = "master_rating")
    private int masterRating;

    @NotEmpty(message = "Phone number should not be empty")
    @Column(name = "phone_number", unique = true)
    @Pattern(regexp = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$",
            message = "Please enter phone number with : + (country code)")
    private String phoneNumber;

    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Please enter valid email")
    @Column(name = "email", unique = true)

    private String masterEmail;

    @Column(name = "created_date")
    @CreationTimestamp
//    @CreatedDate
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    @UpdateTimestamp
//    @LastModifiedDate
    private LocalDateTime updatedDate;


}

