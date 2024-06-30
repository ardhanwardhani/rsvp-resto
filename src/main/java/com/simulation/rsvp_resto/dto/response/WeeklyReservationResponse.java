package com.simulation.rsvp_resto.dto.response;

import com.simulation.rsvp_resto.model.BookingOrder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class WeeklyReservationResponse {
    private Date weekStartDate;
    private Date weekEndDate;
    private List<BookingOrder> reservations;
}
