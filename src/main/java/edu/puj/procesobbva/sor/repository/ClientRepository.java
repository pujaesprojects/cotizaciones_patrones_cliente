package edu.puj.procesobbva.sor.repository;

import edu.puj.procesobbva.sor.domain.Client;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ClientRepository extends PagingAndSortingRepository<Client, Long> {
    Client findByPersonIdentityDocument(@Param("document") String personId);
}
