package com.bitxon.user.test.client;

import com.bitxon.user.api.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Lazy // Lazy initialization required due to ${local.server.port} value injection process
@TestComponent
public class UserClient {

    private static final String USERS_RESOURCE = "/users";

    @Value("${user-service.url}")
    private String userServiceUrl;

    @Autowired
    private TestRestTemplate restTemplate;

    public ResponseEntity<List<User>> getUsers() {
        return restTemplate.exchange(
                buildPath(USERS_RESOURCE),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<User>>() {
                });
    }


    public ResponseEntity<User> postUser(User userToPost) {
        return restTemplate.postForEntity(
                buildPath(USERS_RESOURCE),
                userToPost,
                User.class);
    }

    private String buildPath(String resource) {
        return userServiceUrl + resource;
    }
}
