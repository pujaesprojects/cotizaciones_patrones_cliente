package edu.puj.procesobbva.sor.domain;

import edu.puj.procesobbva.sor.domain.enumeration.TerminationCause;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Getter
@Setter
@ToString
@Entity
@Table(name = "indicador")
public class Indicator extends AbstractEntity {
    private static final long serialVersionUID = 1466025297307020333L;

    @Column(name = "solicitud_id")
    private Long applicationId;

    @Enumerated(EnumType.STRING)
    @Column(name = "causal_terminacion", length = 50)
    private TerminationCause terminationCause;

    @Transient
    private String description;

    @Column(name = "estado", length = 32)
    private String state;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solicitud_id", updatable = false, insertable = false)
    private Application application;

    public TerminationCause getTerminationCause() {
        return terminationCause;
    }

    public void setTerminationCause(TerminationCause terminationCause) {
        this.terminationCause = terminationCause;
        if(this.terminationCause != null) {
            this.setDescription(this.terminationCause.getDescription());
        } else {
            this.setDescription(null);
        }
    }
}

