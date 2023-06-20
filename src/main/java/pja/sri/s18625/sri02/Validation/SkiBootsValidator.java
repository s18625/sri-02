package pja.sri.s18625.sri02.Validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pja.sri.s18625.sri02.model.SkiBoots;
import pja.sri.s18625.sri02.repo.SkiBootsRepository;
import pja.sri.s18625.sri02.rest.error.errors.InvalidSkiBootsIdParameterException;

@Component
@RequiredArgsConstructor
public class SkiBootsValidator {
    private final SkiBootsRepository skiBootsRepository;

    public SkiBoots findSkiBootsByIdOrElseThrowExc(Long skiBootsId) {
        return skiBootsRepository.findById(skiBootsId)
                .orElseThrow(InvalidSkiBootsIdParameterException::new);
    }

    public void isSkiBootsExist(Long buyerId) {
        if (!skiBootsRepository.existsById(buyerId)) {
            throw new InvalidSkiBootsIdParameterException();
        }
    }
}
