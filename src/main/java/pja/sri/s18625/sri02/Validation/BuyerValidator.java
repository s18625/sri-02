package pja.sri.s18625.sri02.Validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pja.sri.s18625.sri02.model.Buyer;
import pja.sri.s18625.sri02.repo.BuyerRepository;
import pja.sri.s18625.sri02.rest.error.errors.InvalidBuyerIdParameterException;

import java.security.InvalidParameterException;

@Component
@RequiredArgsConstructor
public class BuyerValidator {
    private final BuyerRepository buyerRepository;

    public Buyer findBuyerByIdOrElseThrowExc(Long buyerId) {
        return buyerRepository.findById(buyerId)
                .orElseThrow(InvalidBuyerIdParameterException::new);
    }

    public void isBuyerExist(Long buyerId) {
        if (!buyerRepository.existsById(buyerId)) throw new InvalidBuyerIdParameterException();
    }
}
