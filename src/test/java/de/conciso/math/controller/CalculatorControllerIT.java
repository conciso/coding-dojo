package de.conciso.math.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import io.quarkus.test.junit.QuarkusTest;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class CalculatorControllerIT {

  @Test
  public void shouldCallRestServiceAddition() {
    given()
        .when()
        .get("/addition/2/40")
        .then()
        .statusCode(HttpStatus.SC_OK)
        .body(is("42"));
  }

  @Test
  public void shouldCallRestServiceAdditionWithSlashAtTheEnd() {
    given()
        .when()
        .get("/addition/2/40/")
        .then()
        .statusCode(HttpStatus.SC_OK)
        .body(is("42"));
  }

  @Test
  public void shouldCallRestServiceMultiplication() {
    given()
        .when()
        .get("/multiplication/2/40")
        .then()
        .statusCode(HttpStatus.SC_OK)
        .body(is("80"));
  }

}
