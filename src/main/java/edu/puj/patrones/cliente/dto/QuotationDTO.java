package edu.puj.patrones.cliente.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class QuotationDTO implements Serializable {
    public static final long serialVersionUID = 6112734441348170696L;

    private String customerName;
    private String email;
    private List<ProductQuotationDTO> products;
}
