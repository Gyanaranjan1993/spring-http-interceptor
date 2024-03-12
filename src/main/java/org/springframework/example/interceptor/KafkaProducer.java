package org.springframework.example.interceptor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaProducer {
    private final InterceptorConfiguration configuration;


    public void sendHttpEventToKafka()
}
