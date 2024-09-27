package Sept_22nd_2024.Test_design;

import Sept_22nd_2024.POJO_Classes.BookingResponse;
import Sept_22nd_2024.Serialization_builder.Payload;
import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

public class Test_case extends Payload {

    RequestSpecification req;
    Response res;
    ValidatableResponse val;

    @Test
    public void verify_create_booking()
    {
        String post_payload= payload_Post();

       req = RestAssured.given();
       req.baseUri("https://restful-booker.herokuapp.com/")
               .basePath("/booking")
               .contentType(ContentType.JSON)
             .body(post_payload).log().all();

        res=req.when().post();
        System.out.println(res);
        val= res.then();
        val.statusCode(200);

        String responseString=res.asString();
        System.out.println(responseString);
        Gson gson = new Gson();
        BookingResponse bookres = gson.fromJson(responseString, BookingResponse.class);
        System.out.println(bookres.getBookingid());

    }

    public void update_booking()
    {



    }
}
