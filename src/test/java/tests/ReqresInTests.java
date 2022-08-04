package tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class ReqresInTests {

    @BeforeAll
    public static void beforAll() {
        RestAssured.baseURI = "https://reqres.in/";
    }

    @Test
    public void singleUser() {
        given()
                .log().uri()
                .log().body()
                .when()
                .get("/api/users?page=2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("total_pages", is(2));
    }

    @Test
    public void singleUserNotFound() {
        given()
                .log().uri()
                .log().body()
                .when()
                .get("/api/users/23")
                .then()
                .log().all()
                .statusCode(404);
    }

    @Test
    public void createUser() {
        given()
                .log().uri()
                .log().body()
                .contentType(JSON)
                .body("{ \"name\": \"Test\"}")
                .when()
                .post("/api/users")
                .then()
                .log().all()
                .statusCode(201)
                .body("name", is("Test"));
    }

    @Test
    public void deleteUser() {
        given()
                .log().uri()
                .log().body()
                .contentType(JSON)
                .when()
                .delete("/api/users/2")
                .then()
                .log().all()
                .statusCode(204);
    }

    @Test
    void negative415LoginTest(){
        given()
                .log().uri()
                .log().body()
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(415);
    }
}

