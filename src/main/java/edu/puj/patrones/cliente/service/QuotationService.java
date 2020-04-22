package edu.puj.patrones.cliente.service;

import com.github.javafaker.Faker;
import edu.puj.patrones.cliente.domain.Provider;
import edu.puj.patrones.cliente.dto.QuotationDTO;
import edu.puj.patrones.cliente.dto.QuotationEmailDTO;
import edu.puj.patrones.cliente.mapper.ProviderMapper;
import edu.puj.patrones.cliente.repository.ProductRepository;
import edu.puj.patrones.cliente.repository.ProviderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class QuotationService {

    private final ProductRepository productRepository;
    private final ProviderRepository providerRepository;
    private final EmailService emailService;
    private final ProviderMapper providerMapper;

    public QuotationService(ProductRepository productRepository, ProviderRepository providerRepository,
                            EmailService emailService, ProviderMapper providerMapper) {
        this.productRepository = productRepository;
        this.providerRepository = providerRepository;
        this.emailService = emailService;
        this.providerMapper = providerMapper;
    }

    public void sendQuotation(QuotationDTO quotation) {
        Faker faker = new Faker(new Locale("es"));

        List<Provider> providersEntity = providerRepository.findAll();

        QuotationEmailDTO quotationEmail = new QuotationEmailDTO();
        quotationEmail.setQuotation(quotation);

        providersEntity.stream().forEach(provider -> {
            quotationEmail.setProvider(providerMapper.toDto(provider));
            quotationEmail.setTotal(faker.number().randomDouble(2, 1000000, 3000000));
            emailService.sendEmail(quotationEmail);
        });
    }
}
