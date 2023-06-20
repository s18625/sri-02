package pja.sri.s18625.sri02.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.hateoas.RepresentationModel;
import pja.sri.s18625.sri02.model.AdvancementLevel;
import pja.sri.s18625.sri02.model.Buyer;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkiBootsDetailsDto extends RepresentationModel<SkiBootsDetailsDto> {
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

    private BuyerDto buyer;

}
