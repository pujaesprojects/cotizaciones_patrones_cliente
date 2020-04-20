package edu.puj.procesobbva.sor.repository;

import edu.puj.procesobbva.sor.domain.Card;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CardRepository extends PagingAndSortingRepository<Card, Long> {
}
