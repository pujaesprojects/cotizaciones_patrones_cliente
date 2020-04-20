package edu.puj.procesobbva.sor.dto.questionnaire;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class PersonDTO implements Serializable {
    private static final long serialVersionUID = 7742175703193776007L;

    private Long id;
    private String fullName;
}
