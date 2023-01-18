package ru.otus.spring.integration;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.spring.integration.domain.Butterfly;
import ru.otus.spring.integration.domain.Caterpillar;

import java.util.Collection;

@MessagingGateway
public interface ButterflyProcessorGateway {

    @Gateway(requestChannel = "caterpillarChannel", replyChannel = "butterflyChannel")
    Collection<Butterfly> process(Collection<Caterpillar> orderItem);
}
