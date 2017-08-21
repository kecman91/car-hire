package dto;

import lombok.Data;

import java.util.Date;

@Data
public class BookingDto {
    private Date dateFrom;
    private short numberOfDays;
    private boolean paymentReceived;
    private CustomerDto customer;
    private int customerId;
    private String vehicleRegNumber;
}
