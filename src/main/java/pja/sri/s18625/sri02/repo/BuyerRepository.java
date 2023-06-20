package pja.sri.s18625.sri02.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pja.sri.s18625.sri02.model.Buyer;

import java.util.List;
import java.util.Optional;

public interface BuyerRepository extends CrudRepository<Buyer, Long> {
    List<Buyer> findAll();

    @Query("from Buyer as b left join fetch b.skiBoots where b.id = :buyerId")
    Optional<Buyer> getBuyerDetailsById(@Param("buyerId") Long buyerId);
}
