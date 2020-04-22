package edu.puj.patrones.cliente.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ProviderDTO implements Serializable {
    private static final long serialVersionUID = -5399589809785593104L;
    private String name;
    private String address;
    private String email;
    private String phone;
}
