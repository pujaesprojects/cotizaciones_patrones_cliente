package edu.puj.patrones.cliente.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ProductQuotationDTO implements Serializable {
    public static final long serialVersionUID = -3650974325132329274L;

    private String productName;
    private Integer quantity;
}
