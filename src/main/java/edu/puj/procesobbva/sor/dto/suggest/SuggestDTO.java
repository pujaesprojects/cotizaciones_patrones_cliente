package edu.puj.procesobbva.sor.dto.suggest;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
public class SuggestDTO implements Serializable {
    private static final long serialVersionUID = 2210801835910480701L;

    private Map<String, String> productType;
    private Map<String, String> brandAssociation;
    private Map<String, Object> grantedAmount;
    private String additionalInformation;
}
