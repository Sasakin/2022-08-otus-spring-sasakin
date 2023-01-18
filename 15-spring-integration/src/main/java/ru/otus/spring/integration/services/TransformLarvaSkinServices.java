package ru.otus.spring.integration.services;

import org.springframework.stereotype.Service;
import ru.otus.spring.integration.domain.Butterfly;
import ru.otus.spring.integration.domain.Caterpillar;

@Service
public class TransformLarvaSkinServices {

    public Butterfly transform(Caterpillar caterpillar) throws Exception {
        System.out.println("Transform " + caterpillar.getType() + " id: " + caterpillar.getId());
        Thread.sleep(3000);
        System.out.println("Transform " + caterpillar.getType() + " id: " + caterpillar.getId() + " done");
        return new Butterfly(caterpillar.getId());
    }
}
