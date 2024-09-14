package org.thetestingacademy;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Test002 {

    public static void main(String[] args) {

        RestAssured.given()
                    .baseUri("https://restful-booker.herokuapp.com")
                    .basePath("/booking/1").log().all()
                .when()
                   .get()
                .then()
                .statusCode(200).log().all();

        RestAssured.given().baseUri("https://restful-booker.herokuapp.com/booking").log().all()

                .when().get()
                .then().statusCode(200).log().all();




    }
}
