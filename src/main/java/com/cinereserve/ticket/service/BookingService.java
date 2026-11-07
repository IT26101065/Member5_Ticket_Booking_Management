package com.cinereserve.ticket.service;

import com.cinereserve.ticket.model.*;
import com.cinereserve.ticket.repository.BookingRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class BookingService {

    private final BookingRepository repository;

    public BookingService() {
        this.repository = new BookingRepository();
    }

    public BookingService(BookingRepository repository) {
        this.repository = repository;
    }

    /**
     * Creates and records a customer booking with polymorphic ticket pricing.
     */
    public Booking createBooking(String userId, String showtimeId, List<Ticket> tickets,
                                 double baseShowPrice, double hallMultiplier, double discountRate) {
        if (tickets == null || tickets.isEmpty()) {
            throw new IllegalArgumentException("Booking must contain at least one ticket");
        }

        double totalAmount = 0.0;
        List<String> seats = new ArrayList<>();
        List<String> ticketTypes = new ArrayList<>();

        for (Ticket t : tickets) {
            // Polymorphic method call
            double ticketCost = t.calculatePrice(baseShowPrice, hallMultiplier);
            totalAmount += ticketCost;
            seats.add(t.getSeatNumber());
            ticketTypes.add(t.getTicketType());
        }

        double discountAmount = totalAmount * discountRate;
        double finalAmount = totalAmount - discountAmount;

        String bookingId = "BKG-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        String datePrefix = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String randomSuffix = String.format("%04d", new Random().nextInt(10000));
        String bookingRef = "CR-" + datePrefix + "-" + randomSuffix;
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Booking booking = new Booking(
                bookingId, bookingRef, userId, showtimeId,
                String.join(",", seats),
                String.join(",", ticketTypes),
                totalAmount, discountAmount, finalAmount,
                "ONLINE_CARD", Booking.PAYMENT_PAID, Booking.STATUS_CONFIRMED, now
        );

        repository.save(booking);
        return booking;
    }

    public boolean cancelBooking(String bookingId) {
        Optional<Booking> opt = repository.findById(bookingId);
        if (!opt.isPresent()) return false;
        Booking b = opt.get();
        if (b.isCancelled()) return false;

        b.setBookingStatus(Booking.STATUS_CANCELLED);
        b.setPaymentStatus(Booking.PAYMENT_REFUNDED);
        return repository.update(b);
    }

    public List<Booking> getBookingsByUser(String userId) {
        return repository.findAll().stream()
                .filter(b -> b.getUserId().equalsIgnoreCase(userId))
                .collect(Collectors.toList());
    }

    public Optional<Booking> getBookingByRef(String ref) {
        return repository.findByRef(ref);
    }

    public List<Booking> getAllBookings() {
        return repository.findAll();
    }

    public double calculateTotalRevenue() {
        return repository.findAll().stream()
                .filter(Booking::isConfirmed)
                .mapToDouble(Booking::getFinalAmount)
                .sum();
    }
}