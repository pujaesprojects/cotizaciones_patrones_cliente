package edu.puj.procesobbva.sor.repository;

import edu.puj.procesobbva.sor.domain.Offer;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface OfferRepository extends PagingAndSortingRepository<Offer, Long> {
    List<Offer> findAllByApplicationId(@Param("applicationId") Long id);

    List<Offer> findAllByApplicationPersonIdentityDocument(String personId);
}
