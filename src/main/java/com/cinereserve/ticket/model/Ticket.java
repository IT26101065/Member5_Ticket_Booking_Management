package com.cinereserve.ticket.model;

import java.io.Serializable;

/**
 * =========================================================================
 * OOP PRINCIPLE DEMONSTRATED: ABSTRACTION & ENCAPSULATION
 * =========================================================================
 * Abstract base class representing an individual cinema seat ticket.
 */
public abstract class Ticket implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String TYPE_STANDARD = "STANDARD";
    public static final String TYPE_VIP = "VIP";
    public static final String TYPE_STUDENT = "STUDENT";

    private String ticketId;
    private String bookingId;
    private String seatNumber; // e.g. "B6", "D4"
    private double basePrice;

    public Ticket() {}

    public Ticket(String ticketId, String bookingId, String seatNumber, double basePrice) {
        this.ticketId = ticketId;
        this.bookingId = bookingId;
        this.seatNumber = seatNumber;
        this.basePrice = basePrice;
    }

    // =========================================================================
    // ABSTRACT METHODS (ABSTRACTION & POLYMORPHISM)
    // =========================================================================

    public abstract double calculatePrice(double baseShowPrice, double hallMultiplier);
    public abstract String getTicketType();
    public abstract String getPerksDescription();
    public abstract String generatePassBarcode();

    public String getTicketId() { return ticketId; }
    public void setTicketId(String ticketId) { this.ticketId = ticketId; }

    public String getBookingId() { return bookingId; }
    public void setBookingId(String bookingId) { this.bookingId = bookingId; }

    public String getSeatNumber() { return seatNumber; }
    public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }

    public double getBasePrice() { return basePrice; }
    public void setBasePrice(double basePrice) { this.basePrice = basePrice; }

    @Override
    public String toString() {
        return String.format("[%s] Seat %s | ID: %s", getTicketType(), seatNumber, ticketId);
    }
}