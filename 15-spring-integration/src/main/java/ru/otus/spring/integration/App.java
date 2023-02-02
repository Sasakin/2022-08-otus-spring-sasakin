package ru.otus.spring.integration;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.integration.annotation.IntegrationComponentScan;
import ru.otus.spring.integration.domain.Butterfly;
import ru.otus.spring.integration.domain.Caterpillar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

@SpringBootApplication
@IntegrationComponentScan
public class App {

    public static void main(String[] args) throws Exception {
        AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(App.class);

        ButterflyProcessorGateway butterflyProcessorGateway = ctx.getBean(ButterflyProcessorGateway.class);

        ForkJoinPool pool = ForkJoinPool.commonPool();

        while (true) {
            Thread.sleep(7000);

            pool.execute(() -> {
                Collection<Caterpillar> items = generateCaterpillarItems();
                System.out.println("New caterpillar: " +
                        items.stream()
                                .map(c -> c.getType()+ " id:" + c.getId())
                                .collect(Collectors.joining(",")));
                Collection<Butterfly> butterflies = butterflyProcessorGateway.process(items);
                System.out.println("Ready butterfly: " + butterflies.stream()
                        .map(b -> b.getType() + " id:" + b.getId())
                        .collect(Collectors.joining(",")));
            });
        }
    }


    private static Collection<Caterpillar> generateCaterpillarItems() {
        List<Caterpillar> items = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < random.nextInt(5) + 1; ++ i ) {
            items.add( new Caterpillar(Long.valueOf(i)) );
        }
        return items;
    }
}
