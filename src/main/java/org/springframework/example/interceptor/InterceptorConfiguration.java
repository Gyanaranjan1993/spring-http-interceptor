package org.springframework.example.interceptor;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "http.interceptor")
public class InterceptorConfiguration {

    private String httpRequestRecorderTopic;
    private String httpResponseRecorderTopic;
    private String kafkaServer;
    private List<String> urlMatchers = new ArrayList<>();
}
