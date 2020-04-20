package edu.puj.procesobbva.sor.controller;

import com.github.javafaker.Faker;
import edu.puj.procesobbva.sor.dto.questionnaire.ResponseAnswerDTO;
import edu.puj.procesobbva.sor.dto.questionnaire.ResponseDTO;
import edu.puj.procesobbva.sor.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/risk")
@Slf4j
public class RiskController {
    private final PersonRepository personRepository;

    public RiskController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping("/black-listed-entities")
    @Transactional(readOnly = true)
    public ResponseEntity<Map<String, ResponseDTO>> validate(
        @RequestParam String personId
    ) {
        log.debug("Validando lista negra para: {}", personId);

        Faker faker = new Faker(new Locale("es"));
        Map<String, ResponseDTO> response = new HashMap<>();
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode(faker.number().numberBetween(10000L, 99999L));
        responseDTO.setSequence(faker.number().numberBetween(10000L, 99999L));

        ResponseAnswerDTO responseAnswerDTO = new ResponseAnswerDTO();
        responseAnswerDTO.setAttempts(faker.number().numberBetween(1, 4));
        responseAnswerDTO.setPeriodTimeAttempts(faker.number().numberBetween(1, 4));

        if(personRepository.existsByIdentityDocument(personId)) {
            responseAnswerDTO.setDescription("CONFRONTACION EXISTOSA");
            responseAnswerDTO.setCode(4093L);
        } else {
            responseAnswerDTO.setDescription("CONFRONTACION NO EXISTOSA");
            responseAnswerDTO.setCode(4095L);
        }

        responseDTO.setAnswer(responseAnswerDTO);

        response.put("data", responseDTO);
        return ResponseEntity.ok(response);
    }
}
