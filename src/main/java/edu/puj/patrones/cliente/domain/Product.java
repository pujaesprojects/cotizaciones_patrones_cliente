package edu.puj.patrones.cliente.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@Table(name = "producto")
public class Product extends AbstractEntity {

    @Column(name = "nombre", nullable = false)
    private String name;

    @Column(name = "precio", nullable = false)
    private Double price;

    @Column(name = "descripcion")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proveedor", nullable = false)
    private Provider provider;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "products")
    private Set<Category> categories;
}
