package com.cinereserve.ticket.model;

/**
 * Concessionary student ticket with 20% discount (0.80x rate).
 */
public class StudentTicket extends Ticket {
    private static final long serialVersionUID = 1L;

    public StudentTicket(String ticketId, String bookingId, String seatNumber, double basePrice) {
        super(ticketId, bookingId, seatNumber, basePrice);
    }

    @Override
    public double calculatePrice(double baseShowPrice, double hallMultiplier) {
        // 20% student discount
        return (baseShowPrice * hallMultiplier) * 0.80;
    }

    @Override
    public String getTicketType() {
        return TYPE_STUDENT;
    }

    @Override
    public String getPerksDescription() {
        return "Student concession admission (valid Student ID required at usher check-in).";
    }

    @Override
    public String generatePassBarcode() {
        return "STU-" + getTicketId().replace("-", "").toUpperCase();
    }
}