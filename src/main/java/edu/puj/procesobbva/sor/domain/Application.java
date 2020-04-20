package edu.puj.procesobbva.sor.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "solicitud")
public class Application extends AbstractEntity {
    private static final long serialVersionUID = 8734843494006935282L;

    @Column(name = "caso_id")
    private Long caseId;

    @Size(max = 100)
    @Column(name = "estado_caso", length = 100)
    private String caseState;

    @Column(name = "activo")
    private Boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "persona_id")
    private Person person;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "application")
    private Set<Offer> offers;
}
