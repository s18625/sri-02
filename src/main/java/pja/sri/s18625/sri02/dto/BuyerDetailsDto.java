package pja.sri.s18625.sri02.dto;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import pja.sri.s18625.sri02.model.SkiBoots;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuyerDetailsDto extends RepresentationModel<BuyerDetailsDto> {
    private Long Id;
    private String name;
    private String surname;
    private String email;
    private LocalDate dateOfBirth;
    private Set<SkiBootsDto> skiBoots;
}
