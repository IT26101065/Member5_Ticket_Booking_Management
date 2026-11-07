package com.cinereserve.ticket.model;

/**
 * Standard admission ticket (1.0x rate).
 */
public class StandardTicket extends Ticket {
    private static final long serialVersionUID = 1L;

    public StandardTicket(String ticketId, String bookingId, String seatNumber, double basePrice) {
        super(ticketId, bookingId, seatNumber, basePrice);
    }

    @Override
    public double calculatePrice(double baseShowPrice, double hallMultiplier) {
        return baseShowPrice * hallMultiplier; // standard rate
    }

    @Override
    public String getTicketType() {
        return TYPE_STANDARD;
    }

    @Override
    public String getPerksDescription() {
        return "General admission, standard assigned seating.";
    }

    @Override
    public String generatePassBarcode() {
        return "STD-" + getTicketId().replace("-", "").toUpperCase();
    }
}