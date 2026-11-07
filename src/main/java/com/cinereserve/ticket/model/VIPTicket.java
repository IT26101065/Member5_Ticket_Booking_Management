package com.cinereserve.ticket.model;

/**
 * VIP Luxury ticket with recliner access, 1.40x surcharge, and complimentary snack coupon.
 */
public class VIPTicket extends Ticket {
    private static final long serialVersionUID = 1L;

    public VIPTicket(String ticketId, String bookingId, String seatNumber, double basePrice) {
        super(ticketId, bookingId, seatNumber, basePrice);
    }

    @Override
    public double calculatePrice(double baseShowPrice, double hallMultiplier) {
        // VIP surcharge: 1.40x price
        return (baseShowPrice * hallMultiplier) * 1.40;
    }

    @Override
    public String getTicketType() {
        return TYPE_VIP;
    }

    @Override
    public String getPerksDescription() {
        return "Luxury VIP recliner, complimentary popcorn & drink voucher, VIP lounge entry.";
    }

    @Override
    public String generatePassBarcode() {
        return "VIP-" + getTicketId().replace("-", "").toUpperCase();
    }
}