package edu.puj.procesobbva.sor.dto.questionnaire;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class AnswerDTO implements Serializable {
    private static final long serialVersionUID = 519136444289794516L;

    private Long answerdSequence;
    private String answerText;
    private Integer answerPosition;
    private Long questionSequence;
}
