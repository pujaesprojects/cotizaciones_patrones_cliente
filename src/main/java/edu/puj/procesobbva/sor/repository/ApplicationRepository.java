package edu.puj.procesobbva.sor.repository;

import edu.puj.procesobbva.sor.domain.Application;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface ApplicationRepository extends PagingAndSortingRepository<Application, Long> {
   Application findByPersonIdentityDocumentAndCaseId(
       @Param("document") String document,
       @Param("case") Long caseId
   );

   List<Application> findAllByPersonIdentityDocument(@Param("document") String document);

   Application findAllByCaseId(@Param("caseId") Long caseId);
}
