package edu.puj.patrones.cliente.controller;

import edu.puj.patrones.cliente.repository.ClientRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {
    private final ClientRepository clientRepository;

    public ClientController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
}
