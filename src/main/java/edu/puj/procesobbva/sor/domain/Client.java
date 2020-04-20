package edu.puj.procesobbva.sor.domain;

import edu.puj.procesobbva.sor.domain.enumeration.Status;
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

@Getter
@Setter
@ToString
@Entity
@Table(name = "cliente")
public class Client extends AbstractEntity {
    private static final long serialVersionUID = 5513539553264011656L;

    @Column(name = "indicador_riesgo")
    private Integer risk;

    @Enumerated(EnumType.STRING)
    @Column(name = "estatus")
    private Status status;

    @Column(name = "tiene_preaprobado")
    private Boolean preApproved;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "persona_id")
    private Person person;

}
