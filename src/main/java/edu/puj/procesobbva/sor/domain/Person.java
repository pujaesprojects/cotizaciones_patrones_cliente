package edu.puj.procesobbva.sor.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.puj.procesobbva.sor.domain.enumeration.DocumentType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@Table(name = "persona")
public class Person extends AbstractEntity {
    private static final long serialVersionUID = -5054319704000343222L;

    @NotNull
    @Size(min = 3, max = 255)
    @Column(name = "nombres")
    private String firstName;

    @NotNull
    @Size(min = 3, max = 255)
    @Column(name = "apellidos")
    private String lastName;

    @Size(min = 3, max = 50)
    @Column(name = "telefono", length = 50)
    private String phone;

    @Email
    @Size(min = 3, max = 128)
    @Column(name = "email", length = 128)
    private String email;

    @Past
    @Column(name = "fecha_nacimiento")
    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_documento", length = 3)
    private DocumentType identityDocumentType;

    @Size(min = 3, max = 100)
    @Column(name = "numero_documento", length = 100)
    private String identityDocument;

    @Size(max = 28)
    @Column(name = "actividad_economica", length = 28)
    private String economyActivity;

    @Column(name = "actividad_fecha")
    private LocalDate economyActivityDate;

    @Size(max = 50)
    @Column(name = "tipo_ocupacion", length = 128)
    private String occupationType;

    @PositiveOrZero
    @Column(name = "ingresos")
    private Long incomes;

    @PositiveOrZero
    @Column(name = "egresos")
    private Long expenses;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "person")
    private Client client;

    @OneToMany(
        fetch = FetchType.LAZY, mappedBy = "person",
        cascade = CascadeType.ALL, orphanRemoval = true
    )
    private Set<Answer> answers;

    @JsonIgnore
    @OneToMany(
        fetch = FetchType.LAZY, mappedBy = "person"
    )
    private Set<Application> applications;
}
