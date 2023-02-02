package ru.otus.spring.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;

@IntegrationComponentScan
@SuppressWarnings({ "resource", "Duplicates", "InfiniteLoopStatement" })
@ComponentScan
@Configuration
@EnableIntegration
public class AppConfiguration {

    @Bean
    public QueueChannel caterpillarChannel() {
        return MessageChannels.queue( 10 ).get();
    }

    @Bean
    public PublishSubscribeChannel butterflyChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers.fixedRate( 100 ).maxMessagesPerPoll( 2 ).get();
    }

    @Bean
    public IntegrationFlow transformLarvaSkinFlow() {
        return IntegrationFlows.from( "caterpillarChannel" )
                .split()
                .handle( "transformLarvaSkinServices", "transform" )
                .aggregate()
                .channel( "butterflyChannel" )
                .get();
    }

}
