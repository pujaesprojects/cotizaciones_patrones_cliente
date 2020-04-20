package edu.puj.procesobbva.sor.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "respuesta")
public class Answer extends AbstractEntity {
    private static final long serialVersionUID = 2957253887417284793L;

    @Size(max = 255)
    @Column(name = "valor")
    private String value;

    @Column(name = "es_correecta")
    private Boolean isCorrect = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pregunta_id")
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "persona_id")
    @JsonIgnore
    private Person person;

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", this.getId())
            .append("value", value)
            .append("isCorrect", isCorrect)
            .toString();
    }
}
