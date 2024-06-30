package com.simulation.rsvp_resto.dto.request;

import lombok.Data;

import java.util.Date;

@Data
public class BookingOrderRequest {
    private String customerName;
    private Date bookingDate;
}
