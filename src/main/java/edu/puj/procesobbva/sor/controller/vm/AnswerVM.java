package edu.puj.procesobbva.sor.controller.vm;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class AnswerVM implements Serializable {
    private static final long serialVersionUID = -5358837806356555220L;

    private Long sequenceQuestion;
    private Long sequenceAnswer;
}
