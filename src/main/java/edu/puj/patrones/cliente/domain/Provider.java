package edu.puj.patrones.cliente.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@Entity
@Table(name = "proveedor")
public class Provider extends AbstractEntity {
    private static final long serialVersionUID = 8096250272628982312L;

    @Column(name = "nombre", nullable = false)
    private String name;

    @Column(name = "direccion", nullable = false)
    private String address;

    @Column(name = "correo", nullable = false)
    private String email;

    @Column(name = "telefono")
    private String phone;
}
