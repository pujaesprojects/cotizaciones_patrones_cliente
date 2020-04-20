package edu.puj.procesobbva.sor.repository;

import edu.puj.procesobbva.sor.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Set;

@RepositoryRestResource
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findAllByIdIn(Set<Long> ids);
}
