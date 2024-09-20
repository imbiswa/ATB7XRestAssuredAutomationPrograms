package Sept_15th_2024.POST;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

public class BDDStyle {


    // POST Request
    // URL - https://restful-booker.herokuapp.com/auth
    // BODY - PAYLOAD - JSON
    // {
    //    "username" : "admin",
    //    "password" : "password123"
    //}
    // HEADER - Content Type -> application/json

    @Test

    public void test_post()
    {
        String payload ="{\n" +
        "                    \"username\" : \"admin\",\n" +
                "             \"password\" : \"password123\"\n" +
                "                }";


        RestAssured
                .given()
                . baseUri("https://restful-booker.herokuapp.com")
                .basePath("/auth").contentType(ContentType.JSON)
                .log().all()
                .body(payload)
                .when().log().all()

                .then().log().all().statusCode(200);

    }
}
