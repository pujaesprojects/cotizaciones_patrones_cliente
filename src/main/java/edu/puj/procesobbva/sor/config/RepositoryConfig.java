package edu.puj.procesobbva.sor.config;

import edu.puj.procesobbva.sor.domain.Answer;
import edu.puj.procesobbva.sor.domain.Application;
import edu.puj.procesobbva.sor.domain.Card;
import edu.puj.procesobbva.sor.domain.Client;
import edu.puj.procesobbva.sor.domain.Indicator;
import edu.puj.procesobbva.sor.domain.Offer;
import edu.puj.procesobbva.sor.domain.Person;
import edu.puj.procesobbva.sor.domain.Product;
import edu.puj.procesobbva.sor.domain.Question;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

@Configuration
public class RepositoryConfig implements RepositoryRestConfigurer {
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(
            Answer.class,
            Application.class,
            Card.class,
            Client.class,
            Offer.class,
            Person.class,
            Product.class,
            Question.class,
            Indicator.class
        );
    }
}
