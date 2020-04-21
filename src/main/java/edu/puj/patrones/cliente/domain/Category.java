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
@Table(name = "categoria")
public class Category extends AbstractEntity {
    private static final long serialVersionUID = -5689783944967768306L;

    @Column(name = "nombre", nullable = false)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "producto_por_categoria",
            joinColumns = @JoinColumn(name = "idCategoria"),
            inverseJoinColumns = @JoinColumn(name = "idProducto")
    )
    private Set<Product> products;
}
