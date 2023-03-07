package com.aptdev.circuitbreaker;

import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;

@Service
public class ActorService {

    private final CircuitBreakerFactory circuitBreakerFactory;
    private final PrimaryRepository primaryRepository;
    private final SecondaryRepository secondaryRepository;

    public ActorService(CircuitBreakerFactory circuitBreakerFactory,
                        PrimaryRepository primaryRepository,
                        SecondaryRepository secondaryRepository) {
        this.circuitBreakerFactory = circuitBreakerFactory;
        this.primaryRepository = primaryRepository;
        this.secondaryRepository = secondaryRepository;
    }

    public void execute() {
        circuitBreakerFactory.create("primary")
                .run(primaryRepository::doSomething,
                        throwable -> secondaryRepository.doSomething());
    }

}