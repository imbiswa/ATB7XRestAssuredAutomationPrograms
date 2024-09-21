package Sept_21st_2024.CRUD;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class E2E_Integration {

    RequestSpecification req;
    ValidatableResponse val;
    Response res;
    String token;
    String bookingId;
@BeforeTest
    public String getToken() {

        String payload ="{\n" +
                "                    \"username\" : \"admin\",\n" +
                "                    \"password\" : \"password123\"\n" +
                "                }";
        req= RestAssured.given();
        req.baseUri("https://restful-booker.herokuapp.com")
                .basePath("/auth")
                .contentType(ContentType.JSON).log().all().body(payload);
                res=req.when().post();
               val =res.then();
               val.statusCode(200);
               token= res.jsonPath().getString("token");
        System.out.println(token);
        return token;
    }
@BeforeTest
@Test(dependsOnMethods ="gettoken" )
    public String getBookingId() {
        String post_Payload = "{\n" +
                "    \"firstname\" : \"Biswajit\",\n" +
                "    \"lastname\" : \"Mallick\",\n" +
                "    \"totalprice\" : 100,\n" +
                "    \"depositpaid\" : false,\n" +
                "    \"bookingdates\" : {\n" +
                "        \"checkin\" : \"2024-01-01\",\n" +
                "        \"checkout\" : \"2024-01-01\"\n" +
                "    },\n" +
                "    \"additionalneeds\" : \"Lunch\"\n" +
                "}";
        req=RestAssured.given();
        req.baseUri("https://restful-booker.herokuapp.com/").basePath("/booking")
                .contentType(ContentType.JSON)
                .body(post_Payload).log().all();

        res=req.when().post();

        val=res.then().log().all();
        val.statusCode(200);
        bookingId=res.jsonPath().getString("bookingid");
        System.out.println(bookingId);
        return bookingId;
    }
    @Test
    public void test_update_request_put()
    {
        token=getToken();
        bookingId= getBookingId();
         String put_payload="{\n" +
                 "    \"firstname\" : \"Biswa\",\n" +
                 "    \"lastname\" : \"RN\",\n" +
                 "    \"totalprice\" : 100,\n" +
                 "    \"depositpaid\" : false,\n" +
                 "    \"bookingdates\" : {\n" +
                 "        \"checkin\" : \"2024-01-01\",\n" +
                 "        \"checkout\" : \"2024-01-01\"\n" +
                 "    },\n" +
                 "    \"additionalneeds\" : \"Lunch\"\n" +
                 "}";

         req= RestAssured.given();
         req.baseUri("https://restful-booker.herokuapp.com/")
                 .basePath("/booking/"+bookingId).contentType(ContentType.JSON)
                 .cookie("token",token)
                 .body(put_payload).log().all();
         res=req.when().put();
         val =res.then().log().all();
         val.statusCode(200);
    }

    @Test
    public void test_update_request_get()
    {
        bookingId=getBookingId();
        req=RestAssured.given();
        req.baseUri("https://restful-booker.herokuapp.com/").basePath("/booking/"+bookingId).log().all();

                        res=req.when().get();

                        val=res.then().log().all();
                        val.statusCode(200);
        String firstname = res.then().extract().path("booking.firstname");
       // Assert.assertEquals(firstname,"Biswa");
        //System.out.println(bookingId);
    }

    @Test
    public void test_delete_booking()
    {
        String delete_payload ="{\n" +
                "                    \"username\" : \"admin\",\n" +
                "                    \"password\" : \"password123\"\n" +
                "                }";
        bookingId=getBookingId();
        token=getToken();
        req= RestAssured.given();
        req.baseUri("https://restful-booker.herokuapp.com/")
                .basePath("/booking/"+bookingId).contentType(ContentType.JSON)
                .cookie("token",token)
                .body(delete_payload).log().all();
        res=req.when().delete();
        val =res.then().log().all();
        val.statusCode(201);
    }
    @Test
    public void test_after_delete_request_get()
    {
        bookingId=getBookingId();
        req=RestAssured.given();
        req.baseUri("https://restful-booker.herokuapp.com/").basePath("/booking/"+bookingId).log().all();
        res=req.when().get();

        val=res.then().log().all();
        val.statusCode(204);

    }

}
