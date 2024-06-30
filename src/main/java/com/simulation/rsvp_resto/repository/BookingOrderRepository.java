package com.simulation.rsvp_resto.repository;

import com.simulation.rsvp_resto.model.BookingOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface BookingOrderRepository extends JpaRepository<BookingOrder, Long> {
    List<BookingOrder> findByBookingDate(Date bookingDate);

    List<BookingOrder> findByBookingDateBetween(Date startDate, Date endDate);
}
