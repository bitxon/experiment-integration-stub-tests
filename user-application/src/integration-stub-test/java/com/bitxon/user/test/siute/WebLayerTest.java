package com.bitxon.user.test.siute;

import com.bitxon.user.api.User;
import com.bitxon.user.application.UserApplication;
import com.bitxon.user.test.client.UserClient;
import com.bitxon.user.test.config.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("stub-test")
@TestPropertySource(value = "classpath:test-local.yml")
@SpringBootTest(classes = UserApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = TestConfig.class, loader = SpringBootContextLoader.class)
public class WebLayerTest {

    @Autowired
    private UserClient userClient;

    @Test
    public void searchAll() {

        ResponseEntity<List<User>> userSearchResponse = userClient.getUsers();

        assertThat(userSearchResponse).as("Check response entity")
                .isNotNull()
                .hasFieldOrPropertyWithValue("statusCode", HttpStatus.OK);

        assertThat(userSearchResponse.getBody()).as("Check body")
                .hasSize(3)
                .usingElementComparatorOnFields("id", "email", "dateOfBirth")
                .containsExactlyInAnyOrder(prepareAllUsers().toArray(User[]::new));
    }

    @Test
    public void createUser() {

        final User userToSave = User.builder()
                .email("fourth@email.com")
                .dateOfBirth(LocalDate.of(1994, 4, 4))
                .build();

        ResponseEntity<User> response = userClient.postUser(userToSave);

        assertThat(response).as("Check response entity")
                .isNotNull()
                .hasFieldOrPropertyWithValue("statusCode", HttpStatus.OK);

        assertThat(response.getBody()).as("Check body")
                .isNotNull()
                .hasNoNullFieldsOrProperties();
    }

    private List<User> prepareAllUsers() {
        return List.of(
                User.builder().id(2L).email("second@mail.com").dateOfBirth(LocalDate.of(1992, 2,2)).build(),
                User.builder().id(1L).email("first@mail.com").dateOfBirth(LocalDate.of(1991, 1,1)).build(),
                User.builder().id(3L).email("third@mail.com").dateOfBirth(LocalDate.of(1993, 3,3)).build()
        );
    }
}
