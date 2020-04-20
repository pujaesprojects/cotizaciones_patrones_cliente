package edu.puj.procesobbva.sor.controller;

import com.github.javafaker.Faker;
import edu.puj.procesobbva.sor.controller.vm.AnswerListVM;
import edu.puj.procesobbva.sor.domain.Answer;
import edu.puj.procesobbva.sor.domain.Person;
import edu.puj.procesobbva.sor.domain.Question;
import edu.puj.procesobbva.sor.dto.questionnaire.AnswerDTO;
import edu.puj.procesobbva.sor.dto.questionnaire.PersonDTO;
import edu.puj.procesobbva.sor.dto.questionnaire.QuestionDTO;
import edu.puj.procesobbva.sor.dto.questionnaire.QuestionnaireDTO;
import edu.puj.procesobbva.sor.dto.questionnaire.ResponseAnswerDTO;
import edu.puj.procesobbva.sor.dto.questionnaire.ResponseDTO;
import edu.puj.procesobbva.sor.repository.AnswerRepository;
import edu.puj.procesobbva.sor.utils.QuestionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/questions")
@Slf4j
public class QuestionsController {
    private final AnswerRepository answerRepository;

    public QuestionsController(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<Map<String, QuestionnaireDTO>> getQuestions(
        @RequestParam String personId
    ) {

        Faker faker = new Faker(new Locale("es"));
        Set<Long> questionIds = new HashSet<>();

        while (questionIds.size() < 3) {
            Long questionId = faker.number().numberBetween(1L, 12L);
            questionIds.add(questionId);
        }

        log.debug("Questions IDS: {}", questionIds);
        List<Answer> answers = this.answerRepository
            .findAllByPersonIdentityDocumentAndQuestionIdIn(personId, questionIds);
        log.debug("Answers: {}", answers);

        Map<String, QuestionnaireDTO> response = new HashMap<>();
        QuestionnaireDTO questionnaire = new QuestionnaireDTO();
        List<QuestionDTO> questionList = new ArrayList<>();

        AtomicInteger position = new AtomicInteger(1);

        answers.forEach(answer -> {
            if (questionnaire.getPerson() == null) {
                PersonDTO personDTO = new PersonDTO();
                Person person = answer.getPerson();
                personDTO.setId(person.getId());
                personDTO.setFullName(person.getFirstName() + " " + person.getLastName());
                questionnaire.setPerson(personDTO);
            }

            Question question = answer.getQuestion();
            QuestionDTO questionDTO = new QuestionDTO();
            questionDTO.setQuestionSequence(question.getId());
            questionDTO.setQuestionText(question.getStatement());
            questionDTO.setQuestionPosition(position.getAndAdd(1));

            List<AnswerDTO> answerList = new ArrayList<>();
            int answerPosition = faker.number().numberBetween(1, 4);
            for (int i = 1; i <= 4; i++) {
                AnswerDTO answerDTO = new AnswerDTO();
                answerDTO.setQuestionSequence(question.getId());
                answerDTO.setAnswerPosition(i);
                if(i == answerPosition) {
                    answerDTO.setAnswerdSequence(answer.getId());
                    answerDTO.setAnswerText(answer.getValue());
                } else {
                    answerDTO.setAnswerdSequence(faker.number().numberBetween(1000L, 5000L));
                    answerDTO.setAnswerText(QuestionUtils.createAnswer(question));
                }
                answerList.add(answerDTO);
            }
            questionDTO.setAnswerList(answerList);
            questionList.add(questionDTO);
        });
        questionnaire.setQuestionList(questionList);

        response.put("data", questionnaire);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/validate")
    @Transactional(readOnly = true)
    public ResponseEntity<Map<String, ResponseDTO>> validate(@RequestBody AnswerListVM answers) {
        Faker faker = new Faker(new Locale("es"));
        Map<String, ResponseDTO> response = new HashMap<>();
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode(answers.getCode());
        responseDTO.setSequence(answers.getSequence());

        ResponseAnswerDTO responseAnswerDTO = new ResponseAnswerDTO();
        responseAnswerDTO.setAttempts(faker.number().numberBetween(1, 4));
        responseAnswerDTO.setPeriodTimeAttempts(faker.number().numberBetween(1, 4));

        List<Boolean> exists = answers.stream()
            .map(answerVM ->
                this.answerRepository
                    .existsByIdAndQuestionId(answerVM.getSequenceAnswer(), answerVM.getSequenceQuestion())
            )
            .collect(Collectors.toList());

        if(exists.contains(Boolean.FALSE)) {
            responseAnswerDTO.setDescription("CONFRONTACION NO EXISTOSA");
            responseAnswerDTO.setCode(4095L);
        } else {
            responseAnswerDTO.setDescription("CONFRONTACION EXISTOSA");
            responseAnswerDTO.setCode(4093L);
        }

        responseDTO.setAnswer(responseAnswerDTO);

        response.put("data", responseDTO);
        return ResponseEntity.ok(response);
    }
}
