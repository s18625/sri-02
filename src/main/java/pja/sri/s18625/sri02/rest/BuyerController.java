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
import pja.sri.s18625.sri02.Validation.BuyerValidator;
import pja.sri.s18625.sri02.Validation.SkiBootsValidator;
import pja.sri.s18625.sri02.dto.BuyerDetailsDto;
import pja.sri.s18625.sri02.dto.BuyerDto;
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
@RequestMapping(value = "/api/buyer", produces = {"application/hal+json"})
public class BuyerController extends LinksHateoas {
    private final BuyerRepository buyerRepository;
    private final SkiBootsRepository skiBootsRepository;
    private final BuyerDtoMapper buyerDtoMapper;
    private final SkiBootsMapper skiBootsMapper;
    private final SkiBootsValidator skiBootsValidator;
    private final BuyerValidator buyerValidator;


    @GetMapping
    public ResponseEntity<CollectionModel<BuyerDto>> getBuyers() {
        List<Buyer> allBuyers = buyerRepository.findAll();
        List<BuyerDto> buyerDtoList = allBuyers.stream()
                .map(buyerDtoMapper::convertToBuyerDto)
                .collect(Collectors.toList());

        buyerDtoList.forEach(buyerDto -> createLinks(buyerDto, buyerDto.getId()));

        CollectionModel<BuyerDto> result = CollectionModel
                .of(buyerDtoList, linkTo(methodOn(BuyerController.class).getBuyers()).withSelfRel());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("details/{buyerId}")
    public ResponseEntity<BuyerDetailsDto> getBuyerDetailsById(@PathVariable Long buyerId) {
        Buyer buyer = buyerValidator.findBuyerByIdOrElseThrowExc(buyerId);
        BuyerDetailsDto buyerDetailsDto = buyerDtoMapper.convertToBuyerDetailsDto(buyer);
        buyerDetailsDto.getSkiBoots().forEach(skiBootsDto -> skiBootsDto.add(createGetSkiBootsByIdSelfLink(skiBootsDto.getId())));
        buyerDetailsDto.add(createGetBuyerDetailsSelfLink(buyerId));
        return new ResponseEntity<>(buyerDetailsDto, HttpStatus.OK);

    }

    @GetMapping("/{buyerId}")
    public ResponseEntity<BuyerDto> getBuyerById(@PathVariable Long buyerId) {
        Buyer buyer = buyerValidator.findBuyerByIdOrElseThrowExc(buyerId);
        BuyerDto buyerDto = buyerDtoMapper.convertToBuyerDto(buyer);
        buyerDto.add(createGetBuyerSelfLink(buyerId));
        return new ResponseEntity<>(buyerDto, HttpStatus.OK);
    }

    @GetMapping("/{buyerId}/skiBoots")
    public ResponseEntity<CollectionModel<SkiBootsDto>> getSkiBootsByBuyerId(@PathVariable Long buyerId) {
        buyerValidator.isBuyerExist(buyerId);
        List<SkiBoots> skiBootsByBuyerId = skiBootsRepository.findSkiBootsByBuyerId(buyerId);
        List<SkiBootsDto> skiBootsDtoList = skiBootsByBuyerId.stream()
                .map(skiBootsMapper::convertToDto)
                .collect(Collectors.toList());

        skiBootsDtoList.forEach(skiBootsDto -> skiBootsDto.add(createGetSkiBootsByIdSelfLink(skiBootsDto.getId())));

        CollectionModel<SkiBootsDto> result = CollectionModel
                .of(skiBootsDtoList, linkTo(methodOn(BuyerController.class).getSkiBootsByBuyerId(buyerId)).withSelfRel());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/{buyerId}/skiBoots/{skiBootsId}")
    public ResponseEntity<BuyerDetailsDto> saveSkiBootsToBuyer(@PathVariable Long skiBootsId, @PathVariable Long buyerId) {
        Buyer buyer = buyerValidator.findBuyerByIdOrElseThrowExc(buyerId);
        SkiBoots skiBoots = skiBootsValidator.findSkiBootsByIdOrElseThrowExc(skiBootsId);
        buyer.getSkiBoots().add(skiBoots);
        skiBoots.setBuyer(buyer);
        buyerRepository.save(buyer);
        skiBootsRepository.save(skiBoots);
        BuyerDetailsDto result = buyerDtoMapper.convertToBuyerDetailsDto(buyer).add(createSaveBuyerSkiBootsSelfLink(buyerId, skiBootsId));
        return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{buyerId}/skiBoots/{skiBootsId}")
    public ResponseEntity<BuyerDetailsDto> removeSkiBootsFromBuyer(@PathVariable Long skiBootsId, @PathVariable Long buyerId) {
        Buyer buyer = buyerValidator.findBuyerByIdOrElseThrowExc(buyerId);
        SkiBoots skiBoots = skiBootsValidator.findSkiBootsByIdOrElseThrowExc(skiBootsId);
        buyer.getSkiBoots().remove(skiBoots);
        skiBoots.setBuyer(null);
        buyerRepository.save(buyer);
        skiBootsRepository.save(skiBoots);
        BuyerDetailsDto result = buyerDtoMapper.convertToBuyerDetailsDto(buyer).add(createRemoveBuyerSkiBootsSelfLink(buyerId, skiBootsId));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BuyerDto> saveNewBuyer(@Valid @RequestBody BuyerDto buyerDto) {
        Buyer entity = buyerDtoMapper.convertToEntity(buyerDto);
        buyerRepository.save(entity);
        HttpHeaders headers = new HttpHeaders();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(entity.getId())
                .toUri();
        headers.add("Location", location.toString());
        buyerDto.add(createsaveNewBuyerSelfLink(buyerDto));
        return new ResponseEntity<>(buyerDto, headers, HttpStatus.CREATED);
    }

    @PutMapping("/{buyerId}")
    public ResponseEntity<BuyerDto> updateBuyer(@PathVariable Long buyerId, @Valid @RequestBody BuyerDto buyerDto) {
        buyerValidator.isBuyerExist(buyerId);
        buyerDto.setId(buyerId);
        Buyer buyer = buyerDtoMapper.convertToEntity(buyerDto);
        buyerRepository.save(buyer);
        buyerDto.add(createupdateBuyerSelfLink(buyerId, buyerDto));
        return new ResponseEntity<>(buyerDto, HttpStatus.OK);
    }

    @DeleteMapping("/{buyerId}")
    public ResponseEntity<Link> deleteBuyer(@PathVariable Long buyerId) {
        buyerValidator.isBuyerExist(buyerId);
        buyerRepository.deleteById(buyerId);
        return new ResponseEntity<>(createdeleteBuyerSelfLink(buyerId), HttpStatus.OK);
    }

}
