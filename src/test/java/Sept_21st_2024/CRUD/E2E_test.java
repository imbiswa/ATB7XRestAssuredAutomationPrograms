package Sept_21st_2024.CRUD;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
public class E2E_test {
    RequestSpecification requestSpecification;
    ValidatableResponse validatableResponse;
    Response response;
    String token;
    Integer bookingId;
    String firstname;
    String lastname;

    // Create a Token
    public String getToken() {
        String payload = "{ \"username\" : \"admin\", \"password\" : \"password123\" }";

        requestSpecification = RestAssured.given()
                .baseUri("https://restful-booker.herokuapp.com")
                .basePath("/auth")
                .contentType(ContentType.JSON)
                .body(payload)
                .log().all();

        //response = requestSpecification.when().post(); //we can use .when() as well. We can use any of the two.
        response = requestSpecification.post();

        validatableResponse = response.then();
        validatableResponse.statusCode(200);

        token = response.jsonPath().getString("token");
        System.out.println("Token generated: " + token);

        assertThat(token).isNotEmpty().isNotBlank();
        return token;
    }

    // Get BookingID
    public Integer getBookingID() {
        String payload_POST = "{ \"firstname\" : \"Pramod\", \"lastname\" : \"Dutta\", \"totalprice\" : 111, \"depositpaid\" : false, \"bookingdates\" : { \"checkin\" : \"2024-01-01\", \"checkout\" : \"2024-01-01\" }, \"additionalneeds\" : \"Lunch\" }";

        requestSpecification = RestAssured.given()
                .baseUri("https://restful-booker.herokuapp.com/")
                .basePath("/booking")
                .contentType(ContentType.JSON)
                .body(payload_POST)
                .log().all();

        response = requestSpecification.post();

        validatableResponse = response.then();
        validatableResponse.statusCode(200);

        bookingId = response.jsonPath().getInt("bookingid");
        System.out.println("Booking ID created: " + bookingId);

        assertThat(bookingId).isNotNull().isPositive();
        return bookingId;
    }

    // Test Case: PUT Request
    @Test(priority = 1)
    public void test_update_request_put() {
        System.out.println("Starting test_update_request_put...");
        token = getToken();
        bookingId = getBookingID();

        String payloadPUT = "{ \"firstname\" : \"Anshul\", \"lastname\" : \"Ji\", \"totalprice\" : 111, \"depositpaid\" : false, \"bookingdates\" : { \"checkin\" : \"2024-01-01\", \"checkout\" : \"2024-01-01\" }, \"additionalneeds\" : \"Lunch\" }";

        requestSpecification = RestAssured.given()
                .baseUri("https://restful-booker.herokuapp.com/")
                .basePath("/booking/" + bookingId)
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .body(payloadPUT)
                .log().all();

        response = requestSpecification.put();

        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(200);

        firstname = response.then().extract().path("firstname");
        lastname = response.then().extract().path("lastname");

        // Assertions
        assertThat(firstname).isEqualTo("Anshul").isNotEmpty().isNotBlank();
        assertThat(lastname).isEqualTo("Ji").isNotEmpty().isNotBlank();
        System.out.println("Completed test_update_request_put.");
    }

    // Test Case: GET Request
    @Test(priority = 2)
    public void test_update_request_get() {
        System.out.println("Starting test_update_request_get...");
        //bookingId = getBookingID(); -> no need to retrieve new Booking ID as we want to get the details for same Booking ID

        requestSpecification = RestAssured.given()
                .baseUri("https://restful-booker.herokuapp.com/")
                .basePath("/booking/" + bookingId)
                .contentType(ContentType.JSON);

        response = requestSpecification.get();

        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(200);

        firstname = response.then().extract().path("firstname");
        lastname = response.then().extract().path("lastname");

        // Assertions
        assertThat(firstname).isEqualTo("Anshul").isNotEmpty().isNotBlank();
        assertThat(lastname).isEqualTo("Ji").isNotEmpty().isNotBlank();
        System.out.println("Completed test_update_request_get.");
    }

    // Test Case: DELETE Booking
    @Test(priority = 3)
    public void test_delete_booking() {
        System.out.println("Starting test_delete_booking...");
        bookingId = getBookingID();
        token = getToken();

        requestSpecification = RestAssured.given()
                .baseUri("https://restful-booker.herokuapp.com/")
                .basePath("/booking/" + bookingId)
                .cookie("token", token)
                .log().all();

        response = requestSpecification.delete();

        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(201);

        // Assertion: Response code is 201 indicating successful deletion
        assertThat(validatableResponse.extract().statusCode()).isEqualTo(201);
        System.out.println("Completed test_delete_booking.");
    }

    // Test Case: Verify Booking Doesn't Exist
    @Test(priority = 4)
    public void test_after_delete_request_get() {
        System.out.println("Starting test_after_delete_request_get...");
        requestSpecification = RestAssured.given()
                .baseUri("https://restful-booker.herokuapp.com/")
                .basePath("/booking/" + bookingId)
                .contentType(ContentType.JSON);

        response = requestSpecification.get();

        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(404);

        // Assertion: Status code is 404, indicating booking does not exist
        assertThat(validatableResponse.extract().statusCode()).isEqualTo(404);
        System.out.println("Completed test_after_delete_request_get.");
    }
}
