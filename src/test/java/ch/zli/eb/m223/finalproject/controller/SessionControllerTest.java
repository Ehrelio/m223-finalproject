package ch.zli.eb.m223.finalproject.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ch.zli.eb.m223.finalproject.model.Credential;
import ch.zli.eb.m223.finalproject.model.CwSUser;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import java.time.LocalDate;
import java.util.Collections;

@TransactionalQuarkusTest
public class SessionControllerTest {
    
    @BeforeEach
    @TestSecurity(user = "testUser", roles = {"admin"})
    public void prepareTesting(){
        var cwSuser = new CwSUser();
        cwSuser.setBirthdate(LocalDate.of(2000, 5, 5));
        cwSuser.setName("a");
        cwSuser.setFirstname("a");
        cwSuser.setEmail("email@email.com");
        cwSuser.setHashedPassword("paswd");
        cwSuser.setAdmin(false);
        cwSuser.setBookings(Collections.emptyList());
        given()
        .contentType(ContentType.JSON)
        .body(cwSuser)
        .when().post("/users");
    }
    @Test
    public void testCredential(){
        Credential credential = new Credential();
        credential.setEmail("email@email.com");
        credential.setPassword("paswd");

        given()
        .contentType(ContentType.JSON)
        .body(credential)
        .when().post("/session")
        .then()
            .statusCode(200)
            .body("email", is("email@email.com"));
        
    }
}
