package edu.puj.procesobbva.sor.controller.vm;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class SuggestCardVM implements Serializable {
    private static final long serialVersionUID = 354602703917724123L;

    @Getter
    @Setter
    public class Income implements Serializable {
        private static final long serialVersionUID = 296702002218053494L;

        private String incomeType;
        private List<Map<String, Object>> incomeValues;
    }

    @Getter
    @Setter
    public class Expense implements Serializable {
        private static final long serialVersionUID = 296702002218053494L;

        private String expenseType;
        private List<Map<String, Object>> expenseValues;
    }

    private Income income;
    private Expense expense;
}
