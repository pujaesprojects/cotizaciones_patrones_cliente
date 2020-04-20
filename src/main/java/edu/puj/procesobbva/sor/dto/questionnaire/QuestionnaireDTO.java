package edu.puj.procesobbva.sor.dto.questionnaire;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class QuestionnaireDTO implements Serializable {
    private static final long serialVersionUID = -808317128144322384L;

    private PersonDTO person;
    private List<QuestionDTO> questionList;
}
