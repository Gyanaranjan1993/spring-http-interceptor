package org.springframework.example.interceptor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class KafkaEvent {

    private String requestUrl;
    private String requestMethod;
    private Map<String, List<String>> headers;
    private String body;
    private Map<String, List<String>> requestParams;
}
