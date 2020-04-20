package edu.puj.procesobbva.sor.repository;

import edu.puj.procesobbva.sor.domain.Person;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "people", path = "people")
public interface PersonRepository extends PagingAndSortingRepository<Person, Long> {
    Person findByIdentityDocument(@Param("document") String document);

    Boolean existsByIdentityDocument(@Param("document") String document);
}
