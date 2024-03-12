package org.springframework.example.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Component
public class SpringHttpInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        KafkaEvent event = buildKafkaEvent(request);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    private KafkaEvent buildKafkaEvent(HttpServletRequest request) {

        KafkaEvent kafkaEvent = new KafkaEvent();
        kafkaEvent.setRequestUrl(request.getRequestURI());
        kafkaEvent.setRequestMethod(request.getMethod());

        Map<String, List<String>> headers = new HashMap<>();
        Map<String, List<String>> queryParams = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();

        while (headerNames.hasMoreElements()){
            String headerName = headerNames.nextElement();
            Enumeration<String> headerValues = request.getHeaders(headerName);
            List<String> values = new ArrayList<>();

            while(headerValues.hasMoreElements()){
                values.add(headerValues.nextElement());
            }

            headers.put(headerName, values);

        }

        kafkaEvent.setHeaders(headers);

        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()){
            String paramName = params.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            List<String> values = new ArrayList<>(Arrays.asList(paramValues));
            queryParams.put(paramName, values);
        }

        kafkaEvent.setRequestParams(queryParams);

        return kafkaEvent;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
}
