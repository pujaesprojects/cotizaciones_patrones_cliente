package edu.puj.procesobbva.sor.dto.questionnaire;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class QuestionDTO implements Serializable {
    private static final long serialVersionUID = -1946946372216946853L;

    private Long questionSequence;
    private Integer questionPosition;
    private String questionText;

    private List<AnswerDTO> answerList;
}
