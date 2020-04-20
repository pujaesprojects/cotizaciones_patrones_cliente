package edu.puj.procesobbva.sor.controller;

import com.github.javafaker.Faker;
import edu.puj.procesobbva.sor.controller.vm.OfferSaveVM;
import edu.puj.procesobbva.sor.controller.vm.SuggestCardVM;
import edu.puj.procesobbva.sor.domain.Offer;
import edu.puj.procesobbva.sor.domain.Product;
import edu.puj.procesobbva.sor.domain.enumeration.Status;
import edu.puj.procesobbva.sor.dto.suggest.SuggestDTO;
import edu.puj.procesobbva.sor.repository.OfferRepository;
import edu.puj.procesobbva.sor.repository.ProductRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class OfferController {
    private final ProductRepository productRepository;
    private final OfferRepository offerRepository;

    public OfferController(ProductRepository productRepository, OfferRepository offerRepository) {
        this.productRepository = productRepository;
        this.offerRepository = offerRepository;
    }

    @PostMapping("/api/offers/create")
    public ResponseEntity<Void> saveOffers(
        @RequestBody OfferSaveVM offers, HttpServletRequest request) throws URISyntaxException {

        List<Product> products = productRepository.findAllByBinIn(offers.getBines());

        List<Offer> offerList = new ArrayList<>();
        products.forEach(product -> {
            Offer offer = new Offer();
            offer.setProductId(product.getId());
            offer.setApplicationId(offers.getApplicationId());
            offer.setQuota(0d);
            offer.setStatus(Status.ACTIVO);

            offerList.add(offer);
        });

        this.offerRepository.saveAll(offerList);

        return ResponseEntity.created(new URI(request.getRequestURI())).build();
    }

    @PostMapping("/api/offers/suggest")
    @Transactional(readOnly = true)
    public ResponseEntity<List<SuggestDTO>> getSuggests(
        @RequestParam Long applicationId,
        @RequestBody SuggestCardVM suggestCardVM
    ) {
        Faker faker = new Faker(new Locale("es"));
        List<Offer> offerList = this.offerRepository.findAllByApplicationId(applicationId);

        List<SuggestDTO> suggestDTOList =
            offerList.stream()
            .map(offer -> {
                SuggestDTO suggestDTO = new SuggestDTO();
                Map<String, String> productType = new HashMap<>();
                productType.put("id", "CARDS");
                productType.put("name", "Cards");
                suggestDTO.setProductType(productType);

                Map<String, String> brandAssociation = new HashMap<>();
                productType.put("id", offer.getProduct().getType().toString());
                productType.put("name", StringUtils.capitalize(offer.getProduct().getType().toString().toLowerCase()));
                suggestDTO.setBrandAssociation(brandAssociation);

                Map<String, Object> grantedAmount = new HashMap<>();
                grantedAmount.put("currency", "COP");
                grantedAmount.put("value", faker.number().numberBetween(1000000L, 17000000L));
                suggestDTO.setGrantedAmount(grantedAmount);

                suggestDTO.setAdditionalInformation(offer.getProduct().getName());
                return suggestDTO;
            }).collect(Collectors.toList());


        return ResponseEntity.ok(suggestDTOList);
    }
}
