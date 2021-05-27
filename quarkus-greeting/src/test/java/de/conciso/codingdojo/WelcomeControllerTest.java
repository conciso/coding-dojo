package de.conciso.codingdojo;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class WelcomeControllerTest {

    @Test
    public void testMessageWithoutParameterFails() {
        given()
                .contentType(ContentType.JSON)
          .when().post("/welcome")

          .then()
             .statusCode(400);
    }

    @Test
    public void testMessageWithValidNameReturns200() {
        given()
                .contentType(ContentType.JSON)
                .body(new Message("Conciso"))
                .when().post("/welcome")
                .then()
                .statusCode(200);
    }

    @Test
    public void testMessageWithValidNameReturnsHelloWithName() {
        given()
                .contentType(ContentType.JSON)
                .body(new Message("Conciso"))
                .when().post("/welcome")
                .then()
                .body(is("{\"message\":\"Hello Conciso\"}"));
    }

    @Test
    public void testMessageWithBlankNameReturnsBadRequest() {
        given()
                .contentType(ContentType.JSON)
                .body(new Message(""))
                .when().post("/welcome")
                .then()
                .statusCode(400);

    }

}