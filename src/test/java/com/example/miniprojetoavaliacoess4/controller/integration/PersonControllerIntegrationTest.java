package com.example.miniprojetoavaliacoess4.controller.integration;

import com.example.miniprojetoavaliacoess4.configuration.TestContainerDatabaseConfiguration;
import com.example.miniprojetoavaliacoess4.model.enums.NotificationTypeEnum;
import com.example.miniprojetoavaliacoess4.model.transport.PersonDTO;
import com.example.miniprojetoavaliacoess4.model.transport.operations.CreatePersonDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;


/*Não é uma ´prática recomendada criar testes que dependam um do outro*/
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class PersonControllerIntegrationTest extends TestContainerDatabaseConfiguration {

    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;
    private static CreatePersonDTO createPersonDTO;
    private static PersonDTO personDTO;

    @BeforeAll
    static void setup(){

        objectMapper = new ObjectMapper()
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        specification = new RequestSpecBuilder()
                .setBasePath("/person")
                .setPort(8888)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        createPersonDTO = new CreatePersonDTO("user@test.com", "User 01", "48-99999-9999", "UmaSenhaForte", NotificationTypeEnum.EMAIL);
        String notificationType = NotificationTypeEnum.EMAIL.toString();
    }

    @Test
    @Order(1)
    @DisplayName("Criar usuário com testes de integração deve retornar sucesso")
    void createUserWithIntegrationTestsShloudReturnSuccess() throws JsonProcessingException {

        String responseAsJson = RestAssured.given()
                .spec(specification)
                .contentType("application/json")
                .body(createPersonDTO)
                .when().post().then().statusCode(201).extract().body().asString();

        personDTO = objectMapper
                .readValue(responseAsJson, PersonDTO.class);


        Assertions.assertNotNull(personDTO);
        Assertions.assertNotNull(personDTO.guid());
        Assertions.assertNotNull(personDTO.name());
        Assertions.assertNotNull(personDTO.email());

        Assertions.assertEquals(createPersonDTO.name(), personDTO.name());
        Assertions.assertEquals(createPersonDTO.email(), personDTO.email());
    }

    @Test
    @Order(2)
    @DisplayName("Buscar usuario por GUID retorna com sucesso")
    void getUserByIntegrationTestShouldReturnSuccess() throws JsonProcessingException {
        String responseAsJson = RestAssured.given()
                .spec(specification)
                .pathParam("id", personDTO.guid())
                .auth().basic(createPersonDTO.email(), createPersonDTO.password())
                .when().get("{id}").then().statusCode(200).extract().body().asString();

        PersonDTO personById = objectMapper.readValue(responseAsJson, PersonDTO.class);

        Assertions.assertNotNull(personById);
        Assertions.assertNotNull(personById.guid());
        Assertions.assertNotNull(personById.name());
        Assertions.assertNotNull(personById.email());

        Assertions.assertEquals(personDTO.name(), personById.name());
        Assertions.assertEquals(personDTO.email(), personById.email());

    }
}
