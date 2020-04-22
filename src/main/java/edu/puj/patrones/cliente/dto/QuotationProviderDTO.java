package edu.puj.patrones.cliente.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class QuotationProviderDTO implements Serializable {
    private static final long serialVersionUID = -1434322320940149963L;

    private QuotationDTO quotation;
    private ProviderDTO provider;
}
