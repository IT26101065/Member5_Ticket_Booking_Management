package com.cinereserve.ticket.model;

import java.io.Serializable;
import java.util.*;

public class Booking implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String STATUS_CONFIRMED = "CONFIRMED";
    public static final String STATUS_CANCELLED = "CANCELLED";

    public static final String PAYMENT_PAID = "PAID";
    public static final String PAYMENT_REFUNDED = "REFUNDED";

    private String bookingId;
    private String bookingRef;
    private String userId;
    private String showtimeId;
    private String seatNumbersCsv; // e.g. "B6,B7"
    private String ticketTypesCsv; // e.g. "VIP,VIP"
    private double totalAmount;
    private double discountAmount;
    private double finalAmount;
    private String paymentMethod;
    private String paymentStatus;
    private String bookingStatus;
    private String bookingTime;

    public Booking() {}

    public Booking(String bookingId, String bookingRef, String userId, String showtimeId,
                   String seatNumbersCsv, String ticketTypesCsv, double totalAmount,
                   double discountAmount, double finalAmount, String paymentMethod,
                   String paymentStatus, String bookingStatus, String bookingTime) {
        this.bookingId = bookingId;
        this.bookingRef = bookingRef;
        this.userId = userId;
        this.showtimeId = showtimeId;
        this.seatNumbersCsv = seatNumbersCsv;
        this.ticketTypesCsv = ticketTypesCsv;
        this.totalAmount = totalAmount;
        this.discountAmount = discountAmount;
        this.finalAmount = finalAmount;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus != null ? paymentStatus : PAYMENT_PAID;
        this.bookingStatus = bookingStatus != null ? bookingStatus : STATUS_CONFIRMED;
        this.bookingTime = bookingTime;
    }

    public boolean isConfirmed() { return STATUS_CONFIRMED.equalsIgnoreCase(bookingStatus); }
    public boolean isCancelled() { return STATUS_CANCELLED.equalsIgnoreCase(bookingStatus); }

    public List<String> getSeatsList() {
        if (seatNumbersCsv == null || seatNumbersCsv.trim().isEmpty()) return Collections.emptyList();
        String[] parts = seatNumbersCsv.split(",");
        List<String> list = new ArrayList<>();
        for (String p : parts) if (!p.trim().isEmpty()) list.add(p.trim());
        return list;
    }

    public List<String> getTicketTypesList() {
        if (ticketTypesCsv == null || ticketTypesCsv.trim().isEmpty()) return Collections.emptyList();
        String[] parts = ticketTypesCsv.split(",");
        List<String> list = new ArrayList<>();
        for (String p : parts) if (!p.trim().isEmpty()) list.add(p.trim());
        return list;
    }

    public int getTicketCount() { return getSeatsList().size(); }

    public String getBookingId() { return bookingId; }
    public void setBookingId(String bookingId) { this.bookingId = bookingId; }

    public String getBookingRef() { return bookingRef; }
    public void setBookingRef(String bookingRef) { this.bookingRef = bookingRef; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getShowtimeId() { return showtimeId; }
    public void setShowtimeId(String showtimeId) { this.showtimeId = showtimeId; }

    public String getSeatNumbersCsv() { return seatNumbersCsv; }
    public void setSeatNumbersCsv(String seatNumbersCsv) { this.seatNumbersCsv = seatNumbersCsv; }

    public String getTicketTypesCsv() { return ticketTypesCsv; }
    public void setTicketTypesCsv(String ticketTypesCsv) { this.ticketTypesCsv = ticketTypesCsv; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public double getDiscountAmount() { return discountAmount; }
    public void setDiscountAmount(double discountAmount) { this.discountAmount = discountAmount; }

    public double getFinalAmount() { return finalAmount; }
    public void setFinalAmount(double finalAmount) { this.finalAmount = finalAmount; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    public String getBookingStatus() { return bookingStatus; }
    public void setBookingStatus(String bookingStatus) { this.bookingStatus = bookingStatus; }

    public String getBookingTime() { return bookingTime; }
    public void setBookingTime(String bookingTime) { this.bookingTime = bookingTime; }

    @Override
    public String toString() {
        return String.format("[%s] Ref: %s | User: %s | Seats: %s | Paid: $%.2f",
                bookingStatus, bookingRef, userId, seatNumbersCsv, finalAmount);
    }
}