package com.example.miniprojetoavaliacoess4.controller.integration;

import com.example.miniprojetoavaliacoess4.configuration.TestContainerDatabaseConfigurationWithScript;
import com.example.miniprojetoavaliacoess4.model.transport.BookDTO;
import com.example.miniprojetoavaliacoess4.model.transport.operations.CreateBookDTO;
import com.example.miniprojetoavaliacoess4.model.transport.operations.CreateReviewDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class BookControllerIntegrationTest extends TestContainerDatabaseConfigurationWithScript {

    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;

    @BeforeAll
    static void setup(){

        objectMapper = new ObjectMapper()
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .registerModule(new JavaTimeModule());;


        specification = new RequestSpecBuilder()
                .setBasePath("/book")
                .setPort(8888)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();
    }

    @Test
    void createBookWithIntegrationTestsShloudReturnSuccess() throws JsonProcessingException {
        String email = "user@test.com";
        String password = "UmaSenhaForte";

        LocalDateTime data = LocalDateTime.now();
        CreateBookDTO createBookDTO = new CreateBookDTO("Titulo 01", data);

        String responseAsJson = RestAssured.given()
                .spec(specification)
                .auth().basic(email, password)
                .contentType("application/json")
                .body(createBookDTO)
                .when().post().then().statusCode(201).extract().body().asString();

        BookDTO bookDTO = objectMapper
                .readValue(responseAsJson, BookDTO.class);

        Assertions.assertNotNull(bookDTO);
        Assertions.assertNotNull(bookDTO.guid());
        Assertions.assertNotNull(bookDTO.title());
        Assertions.assertNotNull(bookDTO.yearPublication());

        Assertions.assertEquals(createBookDTO.title(), bookDTO.title());

    }
    @Test
    void getBookByIntegrationTestShouldReturnSuccess() throws JsonProcessingException {
        String email = "user@test.com";
        String password = "UmaSenhaForte";

        String guid = "cc760628-b5b7-4a9b-85f1-3652c1302e1d";

        String responseAsString = RestAssured.given()
                .spec(specification)
                .pathParam("id", guid)
                .auth().basic(email, password)
                .when().get("{id}")
                .then().statusCode(200).extract().body().asString();

        BookDTO bookDTO = objectMapper.
                readValue(responseAsString, BookDTO.class);

        Assertions.assertNotNull(bookDTO);
        Assertions.assertNotNull(bookDTO.guid());
    }

    @Test
    void listBooksByIntegrationTestShouldReturnSuccess() throws JsonProcessingException {
        String email = "user@test.com";
        String password = "UmaSenhaForte";

        String responseAsString = RestAssured.given()
                .spec(specification)
                .auth().basic(email, password)
                .when().get()
                .then().statusCode(200).extract().body().asString();
        List<BookDTO> listaLivros = objectMapper
                .readValue(responseAsString, new TypeReference<List<BookDTO>>() {});

        Assertions.assertNotNull(listaLivros);
    }
    @Test
    void createReviewWithIntegrationTestsShloudReturnSuccess() throws JsonProcessingException {
        String email = "user@test.com";
        String password = "UmaSenhaForte";

        String guid = "cc760628-b5b7-4a9b-85f1-3652c1302e1d";

        CreateReviewDTO createReviewDTO = new CreateReviewDTO(3);

        String responseAsJson = RestAssured.given()
                .spec(specification)
                .pathParam("id", guid)
                .auth().basic(email, password)
                .contentType("application/json")
                .body(createReviewDTO)
                .when().post("/{id}/assessment").then().statusCode(201).extract().body().asString();

        BookDTO bookDTO = objectMapper
                .readValue(responseAsJson, BookDTO.class);

        Assertions.assertNotNull(bookDTO);
    }
}
