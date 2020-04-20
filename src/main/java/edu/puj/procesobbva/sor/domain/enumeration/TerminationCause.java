package edu.puj.procesobbva.sor.domain.enumeration;

public enum TerminationCause {
    TARJETA_OK("Tarjeta contratada"),
    SIN_PREGUNTAS("No tiene vida creditícia"),
    SARLAFT("Resultado en bases de datos de riesgo negativo"),
    PREAPROBADO("Cuenta con un cupo preaprobado"),
    CONFRONTA("Error al contestar preguntas de seguridad"),
    TRANSUNION("Resultado transunion negativo"),
    SCORING("Scoring de crédito negativo");

    private String description;

    TerminationCause(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
