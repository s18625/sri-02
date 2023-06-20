package pja.sri.s18625.sri02.dto.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pja.sri.s18625.sri02.dto.BuyerDetailsDto;
import pja.sri.s18625.sri02.dto.BuyerDto;
import pja.sri.s18625.sri02.dto.SkiBootsDto;
import pja.sri.s18625.sri02.model.Buyer;
import pja.sri.s18625.sri02.model.SkiBoots;

@Component
@RequiredArgsConstructor
public class BuyerDtoMapper {
    private final ModelMapper modelMapper;

    public BuyerDto convertToBuyerDto(Buyer buyer) {
        return modelMapper.map(buyer, BuyerDto.class);
    }

    public BuyerDetailsDto convertToBuyerDetailsDto(Buyer buyer) {
        return modelMapper.map(buyer, BuyerDetailsDto.class);
    }

    public Buyer convertToEntity(BuyerDto dto) {
        return modelMapper.map(dto, Buyer.class);
    }
}
