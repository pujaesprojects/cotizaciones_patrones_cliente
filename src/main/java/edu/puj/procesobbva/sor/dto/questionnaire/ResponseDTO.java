package edu.puj.procesobbva.sor.dto.questionnaire;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ResponseDTO implements Serializable {
    private static final long serialVersionUID = -2509214220127651531L;

    private Long code;
    private Long sequence;
    private ResponseAnswerDTO answer;
}
