package edu.puj.patrones.cliente.controller.vm;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class QuotationVM implements Serializable {
    private static final long serialVersionUID = 3992352647119098392L;

    private Long id;
    private String customerName;
    private String email;
    private List<ProductVM> products;

    @Getter
    @Setter
    public static class ProductVM implements Serializable {
        private static final long serialVersionUID = -3866446151063025295L;

        private String productName;
        private Integer quantity;
    }
}
