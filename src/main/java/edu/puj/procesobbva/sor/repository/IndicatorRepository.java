package edu.puj.procesobbva.sor.repository;

import edu.puj.procesobbva.sor.domain.Indicator;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface IndicatorRepository extends PagingAndSortingRepository<Indicator, Long> {
}
