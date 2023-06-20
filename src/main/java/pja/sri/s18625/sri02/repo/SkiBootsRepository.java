package pja.sri.s18625.sri02.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.PathVariable;
import pja.sri.s18625.sri02.model.SkiBoots;

import java.util.List;

public interface SkiBootsRepository extends CrudRepository<SkiBoots, Long> {
    List<SkiBoots> findAll();

    @Query("select b.skiBoots from Buyer as b where b.id = :buyerId")
    List<SkiBoots> findSkiBootsByBuyerId(@PathVariable Long buyerId);
}
