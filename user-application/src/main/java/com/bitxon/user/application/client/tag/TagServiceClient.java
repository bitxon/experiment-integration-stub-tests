package com.bitxon.user.application.client.tag;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TagServiceClient {

    @Value("${tag-service-client.url}")
    private String url;

    private RestTemplate restTemplate = new RestTemplate();

    public String getTag() {
        return restTemplate.getForObject(url + "/tag", String.class);
    }
}
