package Sept_22nd_2024.PayloadUsingHashmap;

import com.google.gson.Gson;
import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class E2E_Integration_HashMap {
    Gson gson = new Gson();

    RequestSpecification req;
    ValidatableResponse val;
    Response res;
    String token;
    String bookingId;
   @BeforeTest
    public String getToken() {
        Map<String, Object> e2EIntegrationHashMap = new LinkedHashMap<>();

    e2EIntegrationHashMap.put("username","admin");
    e2EIntegrationHashMap.put("password" , "password123");
    String tokenvalue=gson.toJson(e2EIntegrationHashMap);

        req= RestAssured.given();
        req.baseUri("https://restful-booker.herokuapp.com")
                .basePath("/auth")
                .contentType(ContentType.JSON).body(tokenvalue).log().all();
                res=req.when().post();
               val =res.then();
               val.statusCode(200);
               token= res.jsonPath().getString("token");
        System.out.println("Generated token: "+token);
        return token;
    }
@BeforeTest
@Test(dependsOnMethods ="getToken" )
    public String getBookingId() {


        Map<String, Object> bookingidpayload = new LinkedHashMap<>();
        bookingidpayload.put("firstname" ,"Biswajit");
        bookingidpayload.put("lastname" ,"Mallick");
        bookingidpayload.put("totalprice","100");
        bookingidpayload.put("depositpaid" ,"false");

        Map<String, Object> bookingnDatesMap = new LinkedHashMap<>();
        bookingnDatesMap.put("checkin","2024-01-01");
        bookingnDatesMap.put("checkout","2024-01-01");
        bookingidpayload.put("bookingdates",bookingnDatesMap);
        bookingidpayload.put("additionalneeds","lunch");

    String bookingidvalue =gson.toJson(bookingidpayload);


        req=RestAssured.given();
        req.baseUri("https://restful-booker.herokuapp.com/")
                .basePath("/booking")
                .contentType(ContentType.JSON)
                .body(bookingidvalue).
                log().all();

        res=req.when().post();

        val=res.then();
        val.statusCode(200);

        bookingId=res.jsonPath().getString("bookingid");
        System.out.println("Generated Booking id: "+bookingId);
        return bookingId;
    }
    @Test
    @Description("Test-1")

    public void test_update_request_put()
    {
        Map <String, Object> bookingidpayloadput = new LinkedHashMap<>();
        bookingidpayloadput.put("firstname" ,"Biswa");
        bookingidpayloadput.put("lastname" ,"RN");
        bookingidpayloadput.put("totalprice","100");
        bookingidpayloadput.put("depositpaid" ,"false");

        Map<String, Object> bookingnDatesMap = new LinkedHashMap<>();
        bookingnDatesMap.put("checkin","2024-01-01");
        bookingnDatesMap.put("checkout","2024-01-01");
        bookingidpayloadput.put("bookingdates",bookingnDatesMap);
        bookingidpayloadput.put("additionalneeds","lunch");

        String putpayload =gson.toJson(bookingidpayloadput);

         req= RestAssured.given();
         req.baseUri("https://restful-booker.herokuapp.com/")
                 .basePath("/booking/"+ bookingId)
                 .contentType(ContentType.JSON)
                 .cookie("token",token)
                 .body(putpayload)
                 .log().all();

         res=req.when().put();


         val =res.then().log().all();
         val.statusCode(200);


       String firstname = res.then().extract().path("firstname");
       String  lastname = res.then().extract().path("lastname");
        assertThat(firstname).isEqualTo("Biswa").isNotEmpty().isNotBlank();
        assertThat(lastname).isEqualTo("RN").isNotEmpty().isNotBlank();
    }

    @Test
    @Description("Test-2")
    public void test_update_request_get()
    {
        //bookingId=getBookingId();
        req=RestAssured.given();
        req.baseUri("https://restful-booker.herokuapp.com/")
                .basePath("/booking/"+ bookingId)
                .contentType(ContentType.JSON).
                log().all();

                        res=req.when().get();

                        val=res.then().log().all();
                        val.statusCode(200);
        String firstname = res.then().extract().path("firstname");
        String lastname =res.then().extract().path("lastname");

        assertThat(firstname).isEqualTo("Biswa").isNotNull().isNotNull().isNotEmpty();
        assertThat(lastname).isEqualTo("RN").isNotNull().isNotNull().isNotEmpty();


    }

    @Test
    @Description("Test-3")
    public void test_delete_booking()
    {
        Map<String, Object> tokenCredentialdelete = new LinkedHashMap<>();
        tokenCredentialdelete.put("username","admin");
        tokenCredentialdelete.put("password" , "password123");
        String deleteCred =gson.toJson(tokenCredentialdelete);

        req= RestAssured.given();
        req.baseUri("https://restful-booker.herokuapp.com/")
                .basePath("/booking/"+ bookingId)
                .cookie("token",token)
                .log().all();
        res=req.when().delete();
        val =res.then().log().all();
        val.statusCode(201);
    }
    @Test
    @Description("Test-4")
    public void test_after_delete_request_get()
    {
        //bookingId=getBookingId();
        req=RestAssured.given();
        req.baseUri("https://restful-booker.herokuapp.com/")
                .basePath("/booking/"+bookingId)
        .contentType(ContentType.JSON).log().all();

        res=req.when().get();

        val=res.then().log().all();
        val.statusCode(404);

    }

   @Test
   @Description("Test-5")
    public void test_verify_invalid_payload()
    {
        Map<String, Object> bookingidpayloadinvalid = new LinkedHashMap<>();
        bookingidpayloadinvalid.put("firstname" ,"Biswajit");
        bookingidpayloadinvalid.put("lastname" ,"Mallick");
        bookingidpayloadinvalid.put("lastname[]" ,"Mallick{}");


        String bookingidvalue =gson.toJson(bookingidpayloadinvalid);


        req=RestAssured.given();
        req.baseUri("https://restful-booker.herokuapp.com/")
                .basePath("/booking")
                .contentType(ContentType.JSON)
                .body(bookingidvalue).
                log().all();

        res=req.when().post();

        val=res.then();
        val.statusCode(500);


    }
    @Test
    @Description("Test-6")
    public void test_verify_update_deleted_request_put() {
        Map<String, Object> payloadfordeleted = new LinkedHashMap<>();
        payloadfordeleted.put("firstname", "test");
        payloadfordeleted.put("lastname", "test");
        payloadfordeleted.put("totalprice", "100");
        payloadfordeleted.put("depositpaid", "false");

        Map<String, Object> bookingnDatesMap = new LinkedHashMap<>();
        bookingnDatesMap.put("checkin", "2024-01-01");
        bookingnDatesMap.put("checkout", "2024-01-01");
        payloadfordeleted.put("bookingdates", bookingnDatesMap);
        payloadfordeleted.put("additionalneeds", "lunch");

        String putpayloaddeleted = gson.toJson(payloadfordeleted);

        req = RestAssured.given();
        req.baseUri("https://restful-booker.herokuapp.com/")
                .basePath("/booking/" + bookingId)
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .body(putpayloaddeleted)
                .log().all();

        res = req.when().put();

        val = res.then().log().all();
        val.statusCode(405);

    }

}
