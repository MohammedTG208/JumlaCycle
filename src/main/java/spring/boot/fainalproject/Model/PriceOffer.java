package spring.boot.fainalproject.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PriceOffer")
// فهد المسلم
public class PriceOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = " Price cannot be null" )
    @Column(columnDefinition = "int not null")
    private double price;

    @NotBlank(message = "status cannot be null")
    @Column(columnDefinition = "varchar(10) not null")
    @Pattern(regexp = "PENDING|APPROVED|REJECTED|CANCELED", message = "Status must be either PENDING|APPROVED|REJECTED|CANCELED")
    private String status;


    @ManyToOne
    @JoinColumn(name = "recycling_request_id")
    @JsonIgnore
    private RecyclingRequest recyclingRequest;

    @ManyToOne
    @JoinColumn(name = "supplier_user_id")
    @JsonIgnore
    private Supplier supplier;




}