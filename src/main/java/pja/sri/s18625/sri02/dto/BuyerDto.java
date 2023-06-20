package pja.sri.s18625.sri02.dto;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuyerDto extends RepresentationModel<BuyerDto> {
    private Long Id;
    @NotBlank(message = "Name is mandatory")
    @Size(min = 2, max = 255)
    private String name;
    @NotBlank(message = "Surname is mandatory")
    private String surname;
    @Email(message = "not a valid email")
    private String email;
    @DateTimeFormat(pattern="yyyy-MMM-dd")
    private LocalDate dateOfBirth;

}
