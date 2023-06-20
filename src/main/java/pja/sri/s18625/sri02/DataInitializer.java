package pja.sri.s18625.sri02;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import pja.sri.s18625.sri02.model.AdvancementLevel;
import pja.sri.s18625.sri02.model.Buyer;
import pja.sri.s18625.sri02.model.SkiBoots;
import pja.sri.s18625.sri02.repo.BuyerRepository;
import pja.sri.s18625.sri02.repo.SkiBootsRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final SkiBootsRepository skiBootsRepository;
    private final BuyerRepository buyerRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        SkiBoots skiBoots = SkiBoots.builder()
                .name("HEAD RAPTOR")
                .brand("Head")
                .price(469.0)
                .flex(120)
                .size(43.0)
                .inclinationAngle(15)
                .advancementLevel(AdvancementLevel.INTERMEDIATE)
                .build();

        SkiBoots skiBoots2 = SkiBoots.builder()
                .name("ATOMIC HAWX PRIME XTD")
                .brand("Atomic")
                .price(1_899.0)
                .flex(140)
                .size(41.0)
                .inclinationAngle(20)
                .advancementLevel(AdvancementLevel.EXPERT)
                .build();

        SkiBoots skiBoots3 = SkiBoots.builder()
                .name("ROSSIGNOL HERO WORLD CUP")
                .brand("Rossignol")
                .price(1_799.0)
                .flex(110)
                .size(42.0)
                .inclinationAngle(10)
                .advancementLevel(AdvancementLevel.INTERMEDIATE)
                .build();

        Buyer stefan = Buyer.builder()
                .name("Stefan")
                .surname("Branicki")
                .dateOfBirth(LocalDate.of(1999, 7, 1))
                .email("s18625@pjwstk.edu.pl")
                .skiBoots(new HashSet<>(Set.of(skiBoots2,skiBoots)))
                .build();

        Buyer jan = Buyer.builder()
                .name("Jan")
                .surname("Kowalski")
                .dateOfBirth(LocalDate.of(1969, 6, 9))
                .email("janKowal@gmail.com")
                .skiBoots(new HashSet<>())
                .build();

        skiBoots.setBuyer(stefan);
        skiBoots2.setBuyer(stefan);

        buyerRepository.saveAll(List.of(stefan, jan));
        skiBootsRepository.saveAll(Arrays.asList(skiBoots2, skiBoots, skiBoots3));

    }
}
