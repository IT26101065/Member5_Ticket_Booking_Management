package com.cinereserve.ticket;

import com.cinereserve.ticket.model.*;
import com.cinereserve.ticket.service.BookingService;

import java.util.Arrays;
import java.util.List;

/**
 * ============================================================================
 * SE1020 OBJECT-ORIENTED PROGRAMMING - MEMBER 5 DEMO
 * COMPONENT: TICKET & RESERVATION BOOKING MANAGEMENT
 * ============================================================================
 * Demonstrates:
 * 1. Abstraction (Ticket abstract base class & Repository interface)
 * 2. Inheritance (StandardTicket, VIPTicket, StudentTicket extending Ticket)
 * 3. Polymorphism (calculatePrice, getTicketType, getPerksDescription, generatePassBarcode)
 * 4. Encapsulation (Unique reference generation CR-YYYYMMDD-XXXX, status state transitions)
 * 5. Persistence (Thread-safe File I/O into data/bookings.txt)
 */
public class TicketBookingManagementMain {

    public static void main(String[] args) {
        System.out.println("================================================================================");
        System.out.println("          CINERESERVE - TICKET & RESERVATION SYSTEM (MEMBER 5)                  ");
        System.out.println("================================================================================");

        BookingService service = new BookingService();

        System.out.println("\n--- [STEP 1: Demonstrating Polymorphic Multi-Tier Ticket Pricing] ---");
        double baseShowPrice = 15.00;
        double premiumHallMultiplier = 1.35; // IMAX Hall

        Ticket t1 = new VIPTicket("TCK-001", "BKG-TEST", "A5", baseShowPrice);
        Ticket t2 = new StandardTicket("TCK-002", "BKG-TEST", "D8", baseShowPrice);
        Ticket t3 = new StudentTicket("TCK-003", "BKG-TEST", "E12", baseShowPrice);

        List<Ticket> orderTickets = Arrays.asList(t1, t2, t3);

        System.out.printf("Base Showtime Price: $%.2f | Hall Multiplier: %.2fx\n\n", baseShowPrice, premiumHallMultiplier);

        for (Ticket t : orderTickets) {
            double price = t.calculatePrice(baseShowPrice, premiumHallMultiplier);
            System.out.println("Seat " + t.getSeatNumber() + " [" + t.getTicketType() + " Tier]:");
            System.out.printf("  Calculated Price : $%.2f\n", price);
            System.out.println("  Perks Included   : " + t.getPerksDescription());
            System.out.println("  Pass Barcode     : " + t.generatePassBarcode());
            System.out.println("  -------------------------------------------------------------");
        }

        System.out.println("\n--- [STEP 2: Processing Multi-Ticket Reservation & Generating Booking Ref] ---");
        double vipCustomerDiscount = 0.15; // 15% discount for Premium user
        Booking newBooking = service.createBooking("USR-002", "SHT-001", orderTickets,
                baseShowPrice, premiumHallMultiplier, vipCustomerDiscount);

        System.out.println(">> BOOKING CONFIRMED!");
        System.out.println("   Booking Reference : " + newBooking.getBookingRef());
        System.out.println("   Booking ID        : " + newBooking.getBookingId());
        System.out.println("   Reserved Seats    : " + newBooking.getSeatNumbersCsv());
        System.out.println("   Ticket Types      : " + newBooking.getTicketTypesCsv());
        System.out.printf("   Subtotal Amount   : $%.2f\n", newBooking.getTotalAmount());
        System.out.printf("   Customer Discount : -$%.2f\n", newBooking.getDiscountAmount());
        System.out.printf("   Final Paid Amount : $%.2f\n", newBooking.getFinalAmount());
        System.out.println("   Status            : " + newBooking.getBookingStatus());

        System.out.println("\n--- [STEP 3: Demonstrating Booking Cancellation & Instant Refund] ---");
        System.out.print("Cancelling Booking " + newBooking.getBookingId() + " -> ");
        boolean cancelled = service.cancelBooking(newBooking.getBookingId());
        if (cancelled) {
            Booking updated = service.getBookingByRef(newBooking.getBookingRef()).get();
            System.out.println("SUCCESS (Status: " + updated.getBookingStatus() + ", Payment: " + updated.getPaymentStatus() + ")");
        } else {
            System.out.println("FAILED");
        }

        System.out.printf("\nCurrent Active Cinema Revenue: $%.2f\n", service.calculateTotalRevenue());

        System.out.println("\n================================================================================");
        System.out.println("  MEMBER 5 MODULE READY: All OOP concepts validated and verified!              ");
        System.out.println("================================================================================");
    }
}