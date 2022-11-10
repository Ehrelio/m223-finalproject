package ch.zli.eb.m223.finalproject.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ch.zli.eb.m223.finalproject.model.CwSUser;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import java.time.LocalDate;
import java.util.Collections;


@TransactionalQuarkusTest
public class UserControllerTest {
    CwSUser cwSuser;

    @BeforeEach
    public void prepareTesting(){
        cwSuser = new CwSUser();
        cwSuser.setBirthdate(LocalDate.of(2000, 5, 5));
        cwSuser.setName("a");
        cwSuser.setFirstname("a");
        cwSuser.setEmail("");
        cwSuser.setHashedPassword("");
        cwSuser.setAdmin(false);
        cwSuser.setBookings(Collections.emptyList());
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"admin"})
    public void testUserIndex(){    
        given()
        .when().get("/users")
        .then()
            .statusCode(200)
            .body(is("[]"));
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"admin", "user"})
    public void testUserDelete(){
        var createResponse = given()
        .contentType(ContentType.JSON)
        .body(cwSuser)
        .when().post("/users");

        int id = createResponse.jsonPath().get("id");

        given()
        .when().delete("/users/" + id)
        .then()
            .statusCode(204);
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"admin", "user"})
    public void testUserUpdate(){
        var createResponse = given()
        .contentType(ContentType.JSON)
        .body(cwSuser)
        .when().post("/users");

        int id = createResponse.jsonPath().get("id");

        var updatedUser = cwSuser;
        updatedUser.setFirstname("sscur");

        given()
        .contentType(ContentType.JSON)
        .body(updatedUser)
        .when().put("/users/" + id)
        .then()
            .statusCode(200)
            .body("firstname", is("sscur"));
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"admin", "user"})
    public void testUserGet(){
        var createResponse = given()
        .contentType(ContentType.JSON)
        .body(cwSuser)
        .when().post("/users");

        int id = createResponse.jsonPath().get("id");

        given()
        .when().get("/users/" + id)
        .then()
            .statusCode(200)
            .body("id", is(id));
    }
}
