package ch.zli.eb.m223.finalproject.controller;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

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
    
    @BeforeEach
    public void prepareTesting(){
        CwSUser cwSuser = new CwSUser();
        cwSuser.setBirthdate(LocalDate.of(2000, 5, 5));
        cwSuser.setName("a");
        cwSuser.setFirstname("a");
        cwSuser.setEmail("");
        cwSuser.setHashedPassword("");
        cwSuser.setAdmin(false);

        booking = new Booking();
        booking.setDate(LocalDate.of(2022, 12, 10));
        booking.setUser(cwSuser);

        cwSuser.setBookings(List.of(booking));
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
    public void testUserUpdate(){
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
            .body("date", is(""));
    }
    
}
