package spring.boot.fainalproject.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Entity

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "offer")
// فاطمة العبدي
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "varchar(10) ")
    @Pattern(regexp = "PENDING|APPROVED|REJECTED|CANCELED", message = "Status must be either PENDING|APPROVED|REJECTED|CANCELED")
    private String status;

    @NotNull(message = " Price cannot be null" )
    @Column(columnDefinition = "int not null")
    private double price;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "facility_request_id", referencedColumnName = "id")
    private FacilityRequest facilityRequest;


    @ManyToOne
    @JsonIgnore
    private Supplier supplier;



}
