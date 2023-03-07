package com.aptdev.circuitbreaker;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, properties = {"server.port=0"})
@ExtendWith(SpringExtension.class)
@DirtiesContext
public class ActorServiceTest {

    @MockBean
    private PrimaryRepository primaryRepository;
    @MockBean
    private SecondaryRepository secondaryRepository;

    @Autowired
    private ActorService actorService;

    @Test
    public void testExecuteService_WithPrimarySuccess() {
        when(primaryRepository.doSomething()).thenReturn("Success");

        actorService.execute();

        verify(primaryRepository, times(1)).doSomething();
        verify(secondaryRepository, never()).doSomething();
    }

    @Test
    public void testExecuteService_WithPrimaryFailure() {
        when(primaryRepository.doSomething()).thenThrow(new RuntimeException("Primary repository failed"));
        when(secondaryRepository.doSomething()).thenReturn("Fallback");

        actorService.execute();

        verify(primaryRepository, times(1)).doSomething();
        verify(secondaryRepository, times(1)).doSomething();
    }
}

