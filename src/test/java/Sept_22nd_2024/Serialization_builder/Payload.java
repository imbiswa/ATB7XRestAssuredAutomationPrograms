package Sept_22nd_2024.Serialization_builder;

import Sept_22nd_2024.POJO_Classes.Booking;
import Sept_22nd_2024.POJO_Classes.BookingDates;
import com.google.gson.Gson;

public class Payload
{


    String post_payload;
    String put_payload;


    public String payload_Post()
    {
        Booking book = new Booking();
        book.setFirstname("Biswajit");
        book.setLastname("Mallick");
        book.setTotalprice("111");
        book.setDepositpaid(true);


        BookingDates bookdate = new BookingDates();
        bookdate.setCheckin("2024-01-01");
        bookdate.setCheckout("2024-02-01");

        book.setBookingdates(bookdate);
        book.setAdditonalneeds("Snacks");
        //now Pojo or java object needs to ->convert to JSON string (byteStream) - Serialization
        Gson gson = new Gson();
        post_payload=gson.toJson(book);
        //System.out.println(post_payload);

        return post_payload;
    }

    public String payload_put()
    {
        Booking book = new Booking();
        book.setFirstname("Biswa");
        book.setLastname("RN");
        book.setTotalprice("111");
        book.setDepositpaid(true);


        BookingDates bookdate = new BookingDates();
        bookdate.setCheckin("2024-01-01");
        bookdate.setCheckout("2024-02-01");

        book.setBookingdates(bookdate);
        book.setAdditonalneeds("Snacks");
        //now Pojo or java object needs to ->convert to JSON string (byteStream) - Serialization
        Gson gson = new Gson();
        put_payload=gson.toJson(book);
        //System.out.println(post_payload);

        return put_payload;
    }




   // book.



}
