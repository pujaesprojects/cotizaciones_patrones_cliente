package edu.puj.patrones.cliente.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gcp.pubsub.core.PubSubTemplate;
import org.springframework.cloud.gcp.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import org.springframework.cloud.gcp.pubsub.integration.outbound.PubSubMessageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Slf4j
@Configuration
public class PubSubConfig {
    @Value("${client.topic}")
    private String clientTopic;

    @Value("${provider.topic}")
    private String providerTopic;

    @Value("${provider.subscription}")
    private String providerSubscription;


    @Bean
    public PubSubInboundChannelAdapter messageChannelAdapter(
        @Qualifier("pvInputChannel") MessageChannel inputChannel,
        PubSubTemplate pubSubTemplate
    ) {
        PubSubInboundChannelAdapter adapter =
            new PubSubInboundChannelAdapter(pubSubTemplate, providerSubscription);
        adapter.setOutputChannel(inputChannel);

        return adapter;
    }

    @Bean
    public PubSubInboundChannelAdapter clientMessageChannelAdapter(
            @Qualifier("clInputChannel") MessageChannel inputChannel,
            PubSubTemplate pubSubTemplate
    ) {
        PubSubInboundChannelAdapter adapter =
                new PubSubInboundChannelAdapter(pubSubTemplate, clientTopic + "_sub");
        adapter.setOutputChannel(inputChannel);

        return adapter;
    }

    @Bean
    @Qualifier("pvInputChannel")
    public MessageChannel pvInputChannel() {
        return new DirectChannel();
    }

    @Bean
    @Qualifier("clInputChannel")
    public MessageChannel clInputChannel() {
        return new DirectChannel();
    }

    @ServiceActivator(inputChannel = "pvInputChannel")
    public void messageReceiver(String payload) {
        log.info("Message arrived! {}: {}", providerSubscription,  payload);
    }

    @Bean
    @ServiceActivator(inputChannel = "pvOutputChannel")
    public MessageHandler messageSender(PubSubTemplate pubsubTemplate) {
        pubsubTemplate.publish(providerTopic, "Hiiii");
        return new PubSubMessageHandler(pubsubTemplate, providerTopic);
    }
}
