package edu.puj.patrones.cliente.controller;

import edu.puj.patrones.cliente.pubsub.ClientGateway;
import edu.puj.patrones.cliente.repository.ClientRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {
    private final ClientRepository clientRepository;
    private final ClientGateway clientGateway;

    public ClientController(ClientRepository clientRepository, ClientGateway clientGateway) {
        this.clientRepository = clientRepository;
        this.clientGateway = clientGateway;
    }

    @PostMapping("/postMessage")
    public ResponseEntity<Void> publishMessage(@RequestParam("message") String message) {
        clientGateway.sendToPubsub(message);
        return ResponseEntity.ok().build();
    }
}
