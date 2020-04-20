package edu.puj.procesobbva.sor.domain;

import edu.puj.procesobbva.sor.domain.enumeration.CardBrand;
import edu.puj.procesobbva.sor.domain.enumeration.Status;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@Entity
@Table(name = "tarjeta")
public class Card extends AbstractEntity {
    private static final long serialVersionUID = -6958411431094917015L;

    @Size(min = 16, max = 16)
    @Column(name = "numero", length = 16)
    private String number;

    @Size(max = 32)
    @Column(name = "tipo", length = 32)
    private String type;

    @Column(name = "fecha_expiracion")
    private LocalDate expirationDate;

    @Column(name = "fecha_expedicion")
    private LocalDate expeditionDate;

    @Column(name = "cupo_disponible")
    private Double availableBalance;

    @Enumerated(EnumType.STRING)
    @Column(name = "estatus", length = 32)
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "marca", length = 32)
    private CardBrand brand;

    @Column(name = "tasa")
    private Double rate;
}
