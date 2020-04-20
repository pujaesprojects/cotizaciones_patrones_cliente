package edu.puj.patrones.cliente.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@Entity
@Table(name = "cliente")
public class Client extends AbstractEntity {
    private static final long serialVersionUID = 5513539553264011656L;
}
