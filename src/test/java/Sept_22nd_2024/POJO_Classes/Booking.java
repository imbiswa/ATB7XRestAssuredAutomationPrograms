package Sept_22nd_2024.POJO_Classes;

public class Booking {

    private String firstname;
    private String lastname;
    private String totalprice;
    private Boolean depositpaid;
    private BookingDates bookingdates;
    private String additonalneeds;

    public BookingDates getBookingdates() {
        return bookingdates;
    }

    public void setBookingdates(BookingDates bookingdates) {
        this.bookingdates = bookingdates;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(String totalprice) {
        this.totalprice = totalprice;
    }

    public Boolean getDepositpaid() {
        return depositpaid;
    }

    public void setDepositpaid(Boolean depositpaid) {
        this.depositpaid = depositpaid;
    }

    public String getAdditonalneeds() {
        return additonalneeds;
    }

    public void setAdditonalneeds(String additonalneeds) {
        this.additonalneeds = additonalneeds;
    }





}
