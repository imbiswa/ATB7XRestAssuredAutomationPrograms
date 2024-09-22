package Sept_21st_2024.CRUD;
import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import io.qameta.allure.Allure;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static org.assertj.core.api.Assertions.*;

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
                .contentType(ContentType.JSON).body(payload).log().all();
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
        req.baseUri("https://restful-booker.herokuapp.com/")
                .basePath("/booking")
                .contentType(ContentType.JSON)
                .body(post_Payload).
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
        token=getToken();
        bookingId= getBookingId();
         String put_payload="{\n" +
                 "    \"firstname\" : \"Biswa\",\n" +
                 "    \"lastname\" : \"RN\",\n" +
                 "    \"totalprice\" : 111,\n" +
                 "    \"depositpaid\" : true,\n" +
                 "    \"bookingdates\" : {\n" +
                 "        \"checkin\" : \"2018-01-01\",\n" +
                 "        \"checkout\" : \"2019-01-01\"\n" +
                 "    },\n" +
                 "    \"additionalneeds\" : \"Breakfast\"\n" +
                 "}";

         req= RestAssured.given();
         req.baseUri("https://restful-booker.herokuapp.com/")
                 .basePath("/booking/"+ bookingId)
                 .contentType(ContentType.JSON)
                 .cookie("token",token)
                 .body(put_payload)
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
        String delete_payload ="{\n" +
                "                    \"username\" : \"admin\",\n" +
                "                    \"password\" : \"password123\"\n" +
                "                }";
       // bookingId=getBookingId();
        //token=getToken(); not require we are using sa,e
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

}
