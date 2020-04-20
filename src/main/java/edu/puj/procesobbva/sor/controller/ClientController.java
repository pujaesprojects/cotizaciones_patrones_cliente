package edu.puj.procesobbva.sor.controller;

import com.github.javafaker.Faker;
import edu.puj.procesobbva.sor.domain.Client;
import edu.puj.procesobbva.sor.domain.Person;
import edu.puj.procesobbva.sor.domain.enumeration.Status;
import edu.puj.procesobbva.sor.repository.ClientRepository;
import edu.puj.procesobbva.sor.repository.PersonRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;

@RestController
public class ClientController {
    private final PersonRepository personRepository;
    private final ClientRepository clientRepository;

    public ClientController(PersonRepository personRepository, ClientRepository clientRepository) {
        this.personRepository = personRepository;
        this.clientRepository = clientRepository;
    }

    @PostMapping("/api/clients/create")
    public ResponseEntity<Void> saveClient(@RequestParam String document, HttpServletRequest request) throws URISyntaxException {
        Faker faker = new Faker(new Locale("es"));
        Person person = this.personRepository.findByIdentityDocument(document);
        Client client = clientRepository.findByPersonIdentityDocument(document);

        if(client != null) {
            return ResponseEntity.created(new URI(request.getRequestURI())).build();
        }

        client = new Client();
        client.setPerson(person);
        client.setRisk(faker.number().numberBetween(0, 100));
        client.setStatus(Status.ACTIVO);
        client.setPreApproved(faker.bool().bool());
        clientRepository.save(client);

        return ResponseEntity.created(new URI(request.getRequestURI())).build();
    }
}
