package edu.puj.procesobbva.sor.dto.questionnaire;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ResponseAnswerDTO implements Serializable {
    private static final long serialVersionUID = 2637688036904682547L;

    private Long code;
    private String description;
    private Integer attempts;
    private Integer periodTimeAttempts;
}
