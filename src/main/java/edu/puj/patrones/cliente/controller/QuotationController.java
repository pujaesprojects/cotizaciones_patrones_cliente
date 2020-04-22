package edu.puj.patrones.cliente.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.puj.patrones.cliente.controller.vm.QuotationVM;
import edu.puj.patrones.cliente.domain.Product;
import edu.puj.patrones.cliente.domain.ProductQuotation;
import edu.puj.patrones.cliente.domain.Quotation;
import edu.puj.patrones.cliente.mapper.ProviderMapper;
import edu.puj.patrones.cliente.repository.ProductRepository;
import edu.puj.patrones.cliente.repository.ProviderRepository;
import edu.puj.patrones.cliente.repository.QuotationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/api/quotation")
public class QuotationController {
    @Value("${provider.topic}")
    private String providerTopic;

    @Value("${provider.subscription}")
    private String providerSuscription;

    private final QuotationRepository quotationRepository;
    private final ProductRepository productRepository;
    private final ProviderRepository providerRepository;
    private final PubSubTemplate pubsubTemplate;
    private final ObjectMapper objectMapper;
    private final ProviderMapper providerMapper;

    public QuotationController(QuotationRepository quotationRepository,
                               ProductRepository productRepository,
                               ProviderRepository providerRepository,
                               PubSubTemplate pubsubTemplate,
                               ObjectMapper objectMapper,
                               ProviderMapper providerMapper) {
        this.quotationRepository = quotationRepository;
        this.productRepository = productRepository;
        this.providerRepository = providerRepository;
        this.pubsubTemplate = pubsubTemplate;
        this.objectMapper = objectMapper;
        this.providerMapper = providerMapper;
    }

    @Transactional
    @PostMapping("/create-quotation")
    public ResponseEntity<QuotationVM> createQuotation(@RequestBody QuotationVM quotationVM) {
        final Quotation quotation = new Quotation();
        quotation.setDate(LocalDateTime.now());
        quotation.setCustomerName(quotationVM.getCustomerName());
        quotation.setEmail(quotationVM.getEmail());

        Set<ProductQuotation> productQuotations = new HashSet<>();
        quotationVM.getProducts().forEach(productVM -> {
            Optional<Product> product = this.productRepository.findByName(productVM.getProductName());
            product.ifPresent(p -> {
                ProductQuotation pq = new ProductQuotation();
                pq.setProduct(p);
                pq.setQuotation(quotation);
                pq.setQuantity(productVM.getQuantity());
                productQuotations.add(pq);
            });
        });
        quotation.setProductQuotations(productQuotations);

        log.debug("Sending info to provider topic {}", providerTopic);

        Quotation savedQuotation = this.quotationRepository.save(quotation);
        quotationVM.setId(savedQuotation.getId());
        try {
            String stringMessage = this.objectMapper.writeValueAsString(quotationVM);
            log.debug("Sending message: {}", stringMessage);
            pubsubTemplate.publish(providerTopic, stringMessage);
        } catch (JsonProcessingException e) {
            log.error("Error serializing message", e);
        }
        return ResponseEntity.ok(quotationVM);
    }
}
