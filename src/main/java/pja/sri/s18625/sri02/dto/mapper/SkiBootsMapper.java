package pja.sri.s18625.sri02.dto.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pja.sri.s18625.sri02.dto.SkiBootsDetailsDto;
import pja.sri.s18625.sri02.dto.SkiBootsDto;
import pja.sri.s18625.sri02.model.SkiBoots;

@Component
@RequiredArgsConstructor
public class SkiBootsMapper {
    private final ModelMapper modelMapper;
    public SkiBootsDto convertToDto(SkiBoots skiBoots) {
        return modelMapper.map(skiBoots, SkiBootsDto.class);
    }

    public SkiBoots convertToEntity(SkiBootsDto dto) {
        return modelMapper.map(dto, SkiBoots.class);
    }
    public SkiBootsDetailsDto convertToDetailedDto(SkiBoots skiBoots) {
        return modelMapper.map(skiBoots, SkiBootsDetailsDto.class);
    }
}
