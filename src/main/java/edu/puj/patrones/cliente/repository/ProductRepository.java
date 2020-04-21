package edu.puj.patrones.cliente.repository;

import edu.puj.patrones.cliente.domain.Product;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
}
