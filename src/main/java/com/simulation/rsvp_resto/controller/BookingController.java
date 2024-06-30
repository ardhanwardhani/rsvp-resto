package com.simulation.rsvp_resto.controller;

import com.simulation.rsvp_resto.dto.request.BookingOrderRequest;
import com.simulation.rsvp_resto.dto.response.WeeklyReservationResponse;
import com.simulation.rsvp_resto.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/booking")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @PostMapping("/create")
    public ResponseEntity<String> makeReservation(@RequestBody BookingOrderRequest request){
        String response = bookingService.makeReservation(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/weekely-reservations")
    public ResponseEntity<List<WeeklyReservationResponse>> getWeeklyReservations(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate){
        List<WeeklyReservationResponse> reservationResponses = bookingService.getWeeklyReservations(startDate, endDate);
        return ResponseEntity.ok(reservationResponses);
    }
}
