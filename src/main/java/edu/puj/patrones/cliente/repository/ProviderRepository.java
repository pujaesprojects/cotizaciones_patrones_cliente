package edu.puj.patrones.cliente.repository;

import edu.puj.patrones.cliente.domain.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface ProviderRepository extends JpaRepository<Provider, Long> {
    Optional<Provider> findByTopic(String topic);
}