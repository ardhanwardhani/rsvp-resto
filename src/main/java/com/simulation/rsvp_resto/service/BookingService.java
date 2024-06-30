package com.simulation.rsvp_resto.service;

import com.simulation.rsvp_resto.dto.request.BookingOrderRequest;
import com.simulation.rsvp_resto.dto.response.WeeklyReservationResponse;
import com.simulation.rsvp_resto.model.BookingOrder;
import com.simulation.rsvp_resto.repository.BookingOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookingService {
    @Autowired
    BookingOrderRepository bookingOrderRepository;

    public String makeReservation(BookingOrderRequest request){
        Date bookingDate = request.getBookingDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(bookingDate);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        String day = new SimpleDateFormat("EEEE").format(bookingDate);

        List<BookingOrder> bookedList = bookingOrderRepository.findByBookingDate(bookingDate);

        if (day.equals("Wednesday") || day.equals("Friday")){
            return "Libur";
        }else if (bookedList.size() >= 2){
            return "Reservasi Penuh";
        }else {
            BookingOrder bookingOrder = new BookingOrder();
            bookingOrder.setCustomerName(request.getCustomerName());
            bookingOrder.setBookingDate(request.getBookingDate());
            bookingOrderRepository.save(bookingOrder);
            return "Reservasi berhasil";
        }
    }

    public List<WeeklyReservationResponse> getWeeklyReservations(Date startDate, Date endDate){
        List<BookingOrder> allReservations = bookingOrderRepository.findByBookingDateBetween(startDate, endDate);

        Map<Integer, List<BookingOrder>> groupedByWeek = allReservations.stream()
                .collect(Collectors.groupingBy(reservations -> {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(reservations.getBookingDate());
                    return calendar.get(Calendar.WEEK_OF_YEAR);
                }));

        List<WeeklyReservationResponse> weeklyReservationResponses = new ArrayList<>();

        for (Map.Entry<Integer, List<BookingOrder>> entry : groupedByWeek.entrySet()){
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.WEEK_OF_YEAR, entry.getKey());
            calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
            Date weekStartDate = calendar.getTime();
            calendar.add(Calendar.DATE, 6);
            Date weekEndDate = calendar.getTime();

            WeeklyReservationResponse response = new WeeklyReservationResponse();
            response.setWeekStartDate(weekStartDate);
            response.setWeekEndDate(weekEndDate);
            response.setReservations(entry.getValue());
            weeklyReservationResponses.add(response);
        }

        return weeklyReservationResponses;
    }
}
