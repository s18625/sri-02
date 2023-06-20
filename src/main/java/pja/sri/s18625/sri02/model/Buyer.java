package pja.sri.s18625.sri02.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Set;


@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Buyer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Size(min = 2, max = 255)
    private String name;
    @NotBlank(message = "Surname is mandatory")
    private String surname;
    @Email(message = "not a valid email")
    private String email;
    @DateTimeFormat(pattern = "yyyy-MMM-dd")
    private LocalDate dateOfBirth;

    @OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<SkiBoots> skiBoots;

}
