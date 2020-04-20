package edu.puj.procesobbva.sor.repository;

import edu.puj.procesobbva.sor.domain.Product;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
    List<Product> findAllByOffersApplicationId(@Param("applicationId") Long id);

    List<Product> findAllByBinIn(@Param("bines") List<Integer> bines);
}
