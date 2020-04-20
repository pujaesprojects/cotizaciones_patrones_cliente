package edu.puj.procesobbva.sor.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.puj.procesobbva.sor.domain.enumeration.QuestionType;
import edu.puj.procesobbva.sor.domain.enumeration.Status;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "pregunta")
public class Question extends AbstractEntity {
    private static final long serialVersionUID = -6386168666332412795L;

    @NotNull
    @Size(max = 255)
    @Column(name = "enunciado", nullable = false)
    private String statement;

    @NotNull
    @Size(max = 32)
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", length = 32, nullable = false)
    private QuestionType type;

    @NotNull
    @Size(max = 255)
    @Enumerated(EnumType.STRING)
    @Column(name = "estatus", length = 32, nullable = false)
    private Status status;

    @OneToMany(
        fetch = FetchType.LAZY, mappedBy = "question",
        cascade = CascadeType.ALL, orphanRemoval = true
    )
    @JsonIgnore
    private Set<Answer> answers;

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", this.getId())
            .append("statement", statement)
            .append("type", type)
            .append("status", status)
            .toString();
    }
}
