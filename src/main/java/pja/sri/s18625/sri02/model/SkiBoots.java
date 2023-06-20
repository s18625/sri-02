package pja.sri.s18625.sri02.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkiBoots {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotBlank(message = "Brand is mandatory")
    private String brand;
    @NotNull(message = "Price is mandatory")
    @Max(value = 10_000, message = "Price has to be between 0 and 10 000")
    private Double price;
    private int inclinationAngle;
    private int flex;
    @NotNull(message = "Size is mandatory")
    private Double size;
    private AdvancementLevel advancementLevel;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private Buyer buyer;


}
