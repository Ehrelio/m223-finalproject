package ch.zli.eb.m223.finalproject.controller;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ch.zli.eb.m223.finalproject.model.CwSGroup;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@TransactionalQuarkusTest
public class GroupControllerTest {
    CwSGroup cwSGroup;

    @BeforeEach
    public void prepareTesting(){
        cwSGroup = new CwSGroup();
        cwSGroup.setName("group");
        cwSGroup.setMembers(Collections.emptyList());
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"admin"})
    public void testGroupIndex(){
        given()
        .when().get("/groups")
        .then()
            .statusCode(200)
            .body(is("[]"));
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"admin"})
    public void testGroupDelete(){
        var createResponse = given()
        .contentType(ContentType.JSON)
        .body(cwSGroup)
        .when().post("/groups");

        int id = createResponse.jsonPath().get("id");

        given()
        .when().delete("/groups/" + id)
        .then()
            .statusCode(204);
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"admin"})
    public void testGroupUpdate(){
        var createResponse = given()
        .contentType(ContentType.JSON)
        .body(cwSGroup)
        .when().post("/groups");

        int id = createResponse.jsonPath().get("id");

        var updatedGroup = cwSGroup;
        updatedGroup.setName("newNAme");

        given()
        .contentType(ContentType.JSON)
        .body(updatedGroup)
        .when().put("/groups/" + id)
        .then()
            .statusCode(200)
            .body("name",is("newNAme"));
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"admin"})
    public void testGroupGet(){
        var createResponse = given()
        .contentType(ContentType.JSON)
        .body(cwSGroup)
        .when().post("/groups");

        int id = createResponse.jsonPath().get("id");

        given().when().get("/groups/" + id)
        .then()
            .statusCode(200)
            .body("id", is(id));
    }
    
}
