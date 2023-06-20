package pja.sri.s18625.sri02.Hateoas;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import pja.sri.s18625.sri02.dto.BuyerDto;
import pja.sri.s18625.sri02.dto.SkiBootsDto;
import pja.sri.s18625.sri02.rest.BuyerController;
import pja.sri.s18625.sri02.rest.SkiBootsController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class LinksHateoas {

    protected Link createGetBuyerDetailsSelfLink(Long buyerId) {
        return linkTo(methodOn(BuyerController.class).getBuyerDetailsById(buyerId)).withSelfRel();
    }

    protected Link createGetBuyerSelfLink(Long buyerId) {
        return linkTo(methodOn(BuyerController.class).getBuyerById(buyerId)).withSelfRel();
    }

    protected Link createSaveBuyerSkiBootsSelfLink(Long buyerId, Long skiBoots) {
        return linkTo(methodOn(BuyerController.class).saveSkiBootsToBuyer(skiBoots, buyerId)).withSelfRel();
    }

    protected Link createRemoveBuyerSkiBootsSelfLink(Long buyerId, Long skiBoots) {
        return linkTo(methodOn(BuyerController.class).removeSkiBootsFromBuyer(skiBoots, buyerId)).withSelfRel();
    }

    protected Link createsaveNewBuyerSelfLink(BuyerDto buyerDto) {
        return linkTo(methodOn(BuyerController.class).saveNewBuyer(buyerDto)).withSelfRel();
    }

    protected Link createupdateBuyerSelfLink(Long buyerId, BuyerDto buyerDto) {
        return linkTo(methodOn(BuyerController.class).updateBuyer(buyerId, buyerDto)).withSelfRel();
    }

    protected Link createdeleteBuyerSelfLink(Long buyerId) {
        return linkTo(methodOn(BuyerController.class).deleteBuyer(buyerId)).withSelfRel();
    }

    protected Link createBuyerSkiBootsLink(Long buyerId) {
        return linkTo(methodOn(BuyerController.class).getSkiBootsByBuyerId(buyerId)).withRel("skiBoots");
    }

    protected void createLinks(RepresentationModel<?> buyerResponse, Long buyerDtoId) {
        buyerResponse.add(createBuyerSkiBootsLink(buyerDtoId));
        buyerResponse.add(createGetBuyerDetailsSelfLink(buyerDtoId));
    }
    protected Link createGetSkiBootsByIdSelfLink(Long skiBootsId) {
        return linkTo(methodOn(SkiBootsController.class).getSkiBootsById(skiBootsId)).withSelfRel();
    }
    
    protected Link createGetDetailedSkiBootsByIdSelfLink(Long skiBootsId) {
        return linkTo(methodOn(SkiBootsController.class).getDetailedSkiBootsById(skiBootsId)).withSelfRel();
    }
    protected Link createGetBuyerBySkiBootsIdSelfLink(Long skiBootsId) {
        return linkTo(methodOn(SkiBootsController.class).getBuyerBySkiBootsById(skiBootsId)).withSelfRel();
    }

    protected Link createSaveNewSkiBootsSelfLink(SkiBootsDto skiBootsDto) {
        return linkTo(methodOn(SkiBootsController.class).saveNewSkiBoots(skiBootsDto)).withSelfRel();
    }

    protected Link createUpdateSkiBootsSelfLink(Long skiBootsId, SkiBootsDto skiBootsDto) {
        return linkTo(methodOn(SkiBootsController.class).updateSkiBoots(skiBootsId, skiBootsDto)).withSelfRel();
    }

    protected Link createDeleteSkiBoots(Long skiBootsId) {
        return linkTo(methodOn(SkiBootsController.class).deleteSkiBoots(skiBootsId)).withSelfRel();
    }
}
