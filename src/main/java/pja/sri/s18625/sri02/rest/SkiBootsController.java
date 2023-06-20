package pja.sri.s18625.sri02.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pja.sri.s18625.sri02.Hateoas.LinksHateoas;
import pja.sri.s18625.sri02.Validation.SkiBootsValidator;
import pja.sri.s18625.sri02.dto.BuyerDto;
import pja.sri.s18625.sri02.dto.SkiBootsDetailsDto;
import pja.sri.s18625.sri02.dto.SkiBootsDto;
import pja.sri.s18625.sri02.dto.mapper.BuyerDtoMapper;
import pja.sri.s18625.sri02.dto.mapper.SkiBootsMapper;
import pja.sri.s18625.sri02.model.Buyer;
import pja.sri.s18625.sri02.model.SkiBoots;
import pja.sri.s18625.sri02.repo.BuyerRepository;
import pja.sri.s18625.sri02.repo.SkiBootsRepository;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/skiBoots", produces = {"application/hal+json"})
public class SkiBootsController extends LinksHateoas {
    private final SkiBootsRepository skiBootsRepository;
    private final SkiBootsMapper skiBootsMapper;
    private final BuyerDtoMapper buyerDtoMapper;
    private final SkiBootsValidator skiBootsValidator;

    @GetMapping
    public ResponseEntity<CollectionModel<SkiBootsDto>> getSkiBoots() {
        List<SkiBoots> allSkiBoots = skiBootsRepository.findAll();
        List<SkiBootsDto> allSkiBootsDtos = allSkiBoots.stream()
                .map(skiBootsMapper::convertToDto)
                .collect(Collectors.toList());

        allSkiBootsDtos
                .forEach(skiBootsDto -> skiBootsDto.add(createGetSkiBootsByIdSelfLink(skiBootsDto.getId())));

        CollectionModel<SkiBootsDto> result = CollectionModel
                .of(allSkiBootsDtos, linkTo(methodOn(SkiBootsController.class).getSkiBoots()).withSelfRel());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{skiBootsId}")
    public ResponseEntity<SkiBootsDto> getSkiBootsById(@PathVariable Long skiBootsId) {
        SkiBoots skiBoots = skiBootsValidator.findSkiBootsByIdOrElseThrowExc(skiBootsId);
        SkiBootsDto SkiBootsDto = skiBootsMapper.convertToDto(skiBoots)
                .add(createGetSkiBootsByIdSelfLink(skiBootsId));
        return new ResponseEntity<>(SkiBootsDto, HttpStatus.OK);

    }

    @GetMapping("/details/{skiBootsId}")
    public ResponseEntity<SkiBootsDetailsDto> getDetailedSkiBootsById(@PathVariable Long skiBootsId) {
        SkiBoots skiBoots = skiBootsValidator.findSkiBootsByIdOrElseThrowExc(skiBootsId);
        SkiBootsDetailsDto skiBootsDetailsDto = skiBootsMapper.convertToDetailedDto(skiBoots)
                .add(createGetDetailedSkiBootsByIdSelfLink(skiBootsId));
        skiBootsDetailsDto.getBuyer().add(createGetBuyerSelfLink(skiBootsDetailsDto.getBuyer().getId()));
        return new ResponseEntity<>(skiBootsDetailsDto, HttpStatus.OK);

    }

    @GetMapping("/buyer/{skiBootsId}")
    public ResponseEntity<BuyerDto> getBuyerBySkiBootsById(@PathVariable Long skiBootsId) {
        SkiBoots skiBoots = skiBootsValidator.findSkiBootsByIdOrElseThrowExc(skiBootsId);
        BuyerDto result = buyerDtoMapper.convertToBuyerDto(skiBoots.getBuyer()).add(createGetBuyerBySkiBootsIdSelfLink(skiBootsId));
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<SkiBootsDto> saveNewSkiBoots(@Valid @RequestBody SkiBootsDto skiBootsDto) {
        SkiBoots entity = skiBootsMapper.convertToEntity(skiBootsDto);
        skiBootsRepository.save(entity);
        HttpHeaders headers = new HttpHeaders();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(entity.getId())
                .toUri();
        headers.add("Location", location.toString());
        skiBootsDto.add(createSaveNewSkiBootsSelfLink(skiBootsDto), createGetSkiBootsByIdSelfLink(entity.getId()));
        return new ResponseEntity<>(skiBootsDto, headers, HttpStatus.CREATED);
    }

    @PutMapping("/{skiBootsId}")
    public ResponseEntity<SkiBootsDto> updateSkiBoots(@PathVariable Long skiBootsId, @RequestBody SkiBootsDto skiBootsDto) {
        skiBootsValidator.isSkiBootsExist(skiBootsId);
        skiBootsDto.setId(skiBootsId);
        SkiBoots entity = skiBootsMapper.convertToEntity(skiBootsDto);
        skiBootsRepository.save(entity);
        skiBootsDto.add(createUpdateSkiBootsSelfLink(skiBootsId, skiBootsDto));
        return new ResponseEntity<>(skiBootsDto, HttpStatus.OK);
    }

    @DeleteMapping("/{skiBootsId}")
    public ResponseEntity<Link> deleteSkiBoots(@PathVariable Long skiBootsId) {
        skiBootsValidator.isSkiBootsExist(skiBootsId);
        skiBootsRepository.deleteById(skiBootsId);
        return new ResponseEntity<>(createDeleteSkiBoots(skiBootsId), HttpStatus.OK);
    }

}
