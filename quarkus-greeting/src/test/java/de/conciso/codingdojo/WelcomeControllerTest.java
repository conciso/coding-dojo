package de.conciso.codingdojo;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@QuarkusTest
public class WelcomeControllerTest {

  @Test
  public void testHelloEndpoint() {
    given()
        .when().get("/welcome")
        .then()
        .statusCode(200)
        .body(is("Hello RESTEasy"));
  }


  @ParameterizedTest
  @ValueSource(strings = {"string", "some other string"})
  public void testPostHelloEndpointShouldReturnGreeting(String name) {
    given().body(  new WelcomeRequest(name))
        .contentType(ContentType.JSON)
        .log().all()
        .when().post("/welcome")
        .then()
        .statusCode(201)
        .body(is("{\"message\":\"Welcome " + name + "\"}")).log().all();
  }



}