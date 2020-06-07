package com.bitxon.user.test.siute;

import com.bitxon.user.api.User;
import com.bitxon.user.application.UserApplication;
import com.bitxon.user.test.client.UserClient;
import com.bitxon.user.test.config.TestConfig;
import com.bitxon.user.test.model.UserDbObject;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("stub-test")
@TestPropertySource(value = "classpath:test-local.yml")
@SpringBootTest(classes = UserApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = TestConfig.class, loader = SpringBootContextLoader.class)
@AutoConfigureWireMock(port = 0)
public class WebLayerTest {

    @Autowired
    private UserClient userClient;
    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    public void setUp() {
        List<UserDbObject> testUsers = List.of(
                UserDbObject.builder().id(2L).email("second@mail.com").dateOfBirth(LocalDate.of(1992, 2, 2)).build(),
                UserDbObject.builder().id(1L).email("first@mail.com").dateOfBirth(LocalDate.of(1991, 1, 1)).build(),
                UserDbObject.builder().id(3L).email("third@mail.com").dateOfBirth(LocalDate.of(1993, 3, 3)).build()
        );
        testUsers.stream()
                .forEach(mongoTemplate::save);
    }

    @AfterEach
    public void tearDown() {
        mongoTemplate.dropCollection(UserDbObject.class);
    }

    @Test
    public void createUser() {

        WireMock.stubFor(get(urlEqualTo("/tag"))
                .willReturn(aResponse().withBody("#z-rex-0893")));

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
                .hasNoNullFieldsOrProperties()
                .hasFieldOrPropertyWithValue("tag", "#z-rex-0893");
    }

    @Test
    public void searchAll() {

        List<DBObject> dbObjects = mongoTemplate.findAll(DBObject.class, "users");

        ResponseEntity<List<User>> userSearchResponse = userClient.getUsers();

        assertThat(userSearchResponse).as("Check response entity")
                .isNotNull()
                .hasFieldOrPropertyWithValue("statusCode", HttpStatus.OK);

        assertThat(userSearchResponse.getBody()).as("Check body")
                .hasSize(dbObjects.size())
                .usingElementComparatorOnFields("id", "email", "dateOfBirth")
                .containsExactlyInAnyOrder(prepareListOfExpectedUsers(dbObjects).toArray(User[]::new));
    }

    private List<User> prepareListOfExpectedUsers(List<DBObject> dbObjects) {
        return dbObjects.stream().map(this::map).collect(Collectors.toList());
    }

    private User map(DBObject dbObject) {
        BasicDBObject basicDBObject = (BasicDBObject) dbObject;
        return User.builder()
                .id(basicDBObject.getLong("_id"))
                .tag(basicDBObject.getString("tag"))
                .email(basicDBObject.getString("email"))
                .dateOfBirth(basicDBObject.getDate("dateOfBirth")
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate())
                .build();
    }
}
