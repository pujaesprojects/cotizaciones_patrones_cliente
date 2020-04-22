package edu.puj.patrones.cliente.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class QuotationEmailDTO implements Serializable {
    public static final long serialVersionUID = -3374420075006355321L;

    private QuotationDTO quotation;
    private ProviderDTO provider;
    private double total;

}
