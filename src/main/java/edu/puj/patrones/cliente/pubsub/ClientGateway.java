package edu.puj.patrones.cliente.pubsub;

import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway(defaultRequestChannel = "pvOutputChannel")
public interface ClientGateway {
    void sendToPubsub(String text);
}
