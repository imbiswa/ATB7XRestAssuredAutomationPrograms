package Sept_22nd_2024.Deserialization_builder;

import Sept_22nd_2024.POJO_Classes.BookingResponse;
import Sept_22nd_2024.Serialization_builder.Payload;
import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

public class Post_Response_Payload extends Payload {
    RequestSpecification req;
    Response res;
    ValidatableResponse val;

    @Test
    public String get_responce_Payload()
    {
        String post_payload = payload_Post();

        req = RestAssured.given();
        req.baseUri("https://restful-booker.herokuapp.com/")
                .basePath("/booking")
                .contentType(ContentType.JSON)
                .body(post_payload).log().all();

        res = req.when().log().all().post();
        String responseString=res.asString();
        System.out.println(res);
        val = res.then();
        //val.statusCode(200);
        Gson gson = new Gson();
        BookingResponse bookres = gson.fromJson(responseString, BookingResponse.class);

        System.out.println(bookres.getBookingid());
        return responseString;
    }


}
