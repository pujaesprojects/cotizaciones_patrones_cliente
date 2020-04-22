package edu.puj.patrones.cliente.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import edu.puj.patrones.cliente.dto.QuotationDTO;
import edu.puj.patrones.cliente.dto.QuotationEmailDTO;
import edu.puj.patrones.cliente.mapper.ProviderMapper;
import edu.puj.patrones.cliente.repository.ProductRepository;
import edu.puj.patrones.cliente.repository.ProviderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Slf4j
@Service
public class QuotationService {
    @Value("${provider.subscription}")
    private String providerSubscription;

    private final ProductRepository productRepository;
    private final ProviderRepository providerRepository;
    private final EmailService emailService;
    private final ProviderMapper providerMapper;
    private final ObjectMapper objectMapper;

    public QuotationService(ProductRepository productRepository, ProviderRepository providerRepository,
                            EmailService emailService, ProviderMapper providerMapper, ObjectMapper objectMapper) {
        this.productRepository = productRepository;
        this.providerRepository = providerRepository;
        this.emailService = emailService;
        this.providerMapper = providerMapper;
        this.objectMapper = objectMapper;
    }

    public void sendQuotation(QuotationDTO quotationDTO) {
        Faker faker = new Faker(new Locale("es"));

        QuotationEmailDTO quotationEmail = new QuotationEmailDTO();
        quotationEmail.setQuotation(quotationDTO);
        this.providerRepository.findByTopic(providerSubscription.replace("_sub", ""))
            .ifPresent(provider -> {
                quotationEmail.setProvider(this.providerMapper.toDto(provider));
            });
        quotationEmail.setTotal(faker.number().randomDouble(2, 1000000, 3000000));
        emailService.sendEmail(quotationEmail);
    }

    @ServiceActivator(inputChannel = "pvInputChannel")
    public void messageReceiver(String payload) {
        log.info("Message arrived!: {}",  payload);

        try {
            QuotationDTO quotationProvider  = objectMapper.readValue(payload, QuotationDTO.class);
            this.sendQuotation(quotationProvider);
        } catch (JsonProcessingException e) {
            log.error("Error parseando el mensaje", e);
        }
    }
}
