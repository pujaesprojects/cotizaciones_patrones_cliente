package edu.puj.procesobbva.sor.controller.vm;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class OfferSaveVM implements Serializable {
    private static final long serialVersionUID = -7980393175944062200L;

    private List<Integer> bines;
    private Long applicationId;
}
