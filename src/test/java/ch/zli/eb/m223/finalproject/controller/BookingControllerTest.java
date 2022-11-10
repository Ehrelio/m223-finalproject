package ch.zli.eb.m223.finalproject.controller;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.eclipse.sisu.plexus.Roles;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ch.zli.eb.m223.finalproject.controller.TransactionalQuarkusTest;
import ch.zli.eb.m223.finalproject.model.Booking;
import ch.zli.eb.m223.finalproject.model.CwSUser;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;


@TransactionalQuarkusTest
public class BookingControllerTest {
    Booking booking;
    CwSUser cwSUser;
    
    @BeforeEach
    @TestSecurity(user = "testUser", roles = {"admin"})
    public void prepareTesting(){
        cwSUser = new CwSUser();
        cwSUser.setBirthdate(LocalDate.of(2000, 5, 5));
        cwSUser.setName("a");
        cwSUser.setFirstname("a");
        cwSUser.setEmail("");
        cwSUser.setHashedPassword("");
        cwSUser.setAdmin(false);
        cwSUser.setBookings(Collections.emptyList());

        booking = new Booking();
        booking.setDate(LocalDate.of(2022, 12, 10));
        booking.setUser(cwSUser);

        var createResponse = given()
        .contentType(ContentType.JSON)
        .body(cwSUser)
        .when().post("/users");
        Long id = Integer.toUnsignedLong(createResponse.jsonPath().get("id"));

        cwSUser.setId(id);

    }

    @Test
    @TestSecurity(user = "testUser", roles = {"admin"})
    public void testBookingIndex(){
        given()
        .when().get("/bookings")
        .then()
            .statusCode(200)
            .body(is("[]"));
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"admin", "user"})
    public void testBookingDelete(){
       

        var createResponse = given()
        .contentType(ContentType.JSON)
        .body(booking)
        .when().post("/bookings");

        int id = createResponse.jsonPath().get("id");
      
        given()
        .when().delete("/bookings/" + id)
        .then()
            .statusCode(204);
    }

    @Test
    @TestSecurity(user = "testUser", roles = {"admin", "user"})
    public void testBookingUpdate(){
        var createResponse = given()
        .contentType(ContentType.JSON)
        .body(booking)
        .when().post("/bookings");
        int id = createResponse.jsonPath().get("id");


        var updatedBooking = booking;
        updatedBooking.setDate(LocalDate.of(2022, 1, 28));

        given()
        .contentType(ContentType.JSON)
        .body(updatedBooking)
        .when().put("/bookings/" + id)
        .then()
            .statusCode(200)
            .body("date", is("2022-01-28"));
    }
    
    @Test
    @TestSecurity(user = "testUser", roles = {"admin", "user"})
    public void testBookingGet(){
        var createResponse = given()
        .contentType(ContentType.JSON)
        .body(booking)
        .when().post("/bookings");
        int id = createResponse.jsonPath().get("id");

        given().when().get("/bookings/" + id)
        .then()
            .statusCode(200)
            .body("id", is(id));
    }
}
