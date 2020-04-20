package edu.puj.procesobbva.sor.controller;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import edu.puj.procesobbva.sor.domain.Answer;
import edu.puj.procesobbva.sor.domain.Person;
import edu.puj.procesobbva.sor.domain.Question;
import edu.puj.procesobbva.sor.domain.enumeration.DocumentType;
import edu.puj.procesobbva.sor.repository.PersonRepository;
import edu.puj.procesobbva.sor.repository.QuestionRepository;
import edu.puj.procesobbva.sor.utils.QuestionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/data")
public class DataController {
    private final PersonRepository personRepository;
    private final QuestionRepository questionRepository;

    public DataController(PersonRepository personRepository, QuestionRepository questionRepository) {
        this.personRepository = personRepository;
        this.questionRepository = questionRepository;
    }

    @PostMapping("/create-people")
    public ResponseEntity<List<Person>> createPeople(
        @RequestParam Long size, @RequestParam(required = false, defaultValue = "false") Boolean save
    ) {
        List<Person> people = new ArrayList<>();
        size = Math.abs(size);
        Faker faker = new Faker(new Locale("es"));

        Date tmpDate;
        for(int i = 0; i < size; i++) {
            Person person = new Person();
            tmpDate = faker.date().birthday(18, 80);
            person.setBirthday(LocalDate.ofInstant(tmpDate.toInstant(), ZoneId.systemDefault()));

            Name name = faker.name();
            person.setEmail(faker.internet().emailAddress(name.username()));
            person.setFirstName(name.firstName());
            person.setLastName(name.lastName());
            person.setIncomes(faker.number().numberBetween(0, 50000000L));
            person.setExpenses(faker.number().numberBetween(0, person.getIncomes()));
            person.setIdentityDocument(
                String.valueOf(
                    faker.number().numberBetween(50000, 1300000000L)
                )
            );
            person.setIdentityDocumentType(DocumentType.CC);
            person.setOccupationType(faker.job().position());
            person.setPhone(faker.phoneNumber().phoneNumber());

            people.add(person);

            List<Question> questions = questionRepository.findAll();

            Set<Answer> answers = questions
                .stream()
                .map(question -> {
                    Answer answer = new Answer();
                    answer.setIsCorrect(true);
                    answer.setQuestion(question);
                    answer.setPerson(person);
                    answer.setValue(QuestionUtils.createAnswer(question));
                    return answer;
                })
                .collect(Collectors.toSet());
            person.setAnswers(answers);
        }

        if(save) {
            this.personRepository.saveAll(people);
        }

        return ResponseEntity.ok(people);
    }
}
