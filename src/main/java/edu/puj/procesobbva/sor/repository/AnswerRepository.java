package edu.puj.procesobbva.sor.repository;

import edu.puj.procesobbva.sor.domain.Answer;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Set;

@RepositoryRestResource
public interface AnswerRepository extends PagingAndSortingRepository<Answer, Long> {
    List<Answer> findAllByPersonIdentityDocumentAndQuestionIdIn(
        String identityDocument, Set<Long> questionId
    );

    Boolean existsByIdAndQuestionId(Long id, Long questionId);
}
