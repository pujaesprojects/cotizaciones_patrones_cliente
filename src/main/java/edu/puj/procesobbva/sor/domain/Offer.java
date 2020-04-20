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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@Entity
@Table(name = "oferta")
public class Offer extends AbstractEntity {
    private static final long serialVersionUID = -6630406251638679845L;

    @Enumerated(EnumType.STRING)
    @Column(name = "estatus", length = 32)
    private Status status;

    @Column(name = "cupo")
    private Double quota;

    @Column(name = "solicitud_id")
    private Long applicationId;

    @Column(name = "producto_id")
    private Long productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solicitud_id", insertable = false, updatable = false)
    private Application application;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", insertable = false, updatable = false)
    private Product product;
}
