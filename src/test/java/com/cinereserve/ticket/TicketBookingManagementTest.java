package com.cinereserve.ticket;

import com.cinereserve.ticket.model.*;
import com.cinereserve.ticket.service.BookingService;
import org.junit.jupiter.api.*;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class TicketBookingManagementTest {

    private BookingService bookingService;

    @BeforeEach
    void setUp() {
        bookingService = new BookingService();
    }

    @Test
    void testStandardTicketPolymorphism() {
        Ticket std = new StandardTicket("T1", "B1", "D4", 10.0);
        // Base $10 * 1.0 = $10.00
        assertEquals(10.00, std.calculatePrice(10.0, 1.0));
        assertEquals("STANDARD", std.getTicketType());
        assertTrue(std.generatePassBarcode().startsWith("STD-"));
    }

    @Test
    void testVIPTicketPolymorphism() {
        Ticket vip = new VIPTicket("T2", "B1", "A1", 10.0);
        // Base $10 * 1.0 * 1.40 = $14.00
        assertEquals(14.00, vip.calculatePrice(10.0, 1.0));
        assertEquals("VIP", vip.getTicketType());
        assertTrue(vip.generatePassBarcode().startsWith("VIP-"));
    }

    @Test
    void testStudentTicketPolymorphism() {
        Ticket student = new StudentTicket("T3", "B1", "E1", 10.0);
        // Base $10 * 1.0 * 0.80 = $8.00
        assertEquals(8.00, student.calculatePrice(10.0, 1.0));
        assertEquals("STUDENT", student.getTicketType());
        assertTrue(student.generatePassBarcode().startsWith("STU-"));
    }

    @Test
    void testCreateAndCancelBooking() {
        Ticket t = new StandardTicket("T9", "B9", "C3", 20.0);
        Booking b = bookingService.createBooking("USR-99", "SHT-99", Arrays.asList(t), 20.0, 1.0, 0.0);
        assertNotNull(b.getBookingRef());
        assertTrue(b.isConfirmed());

        // Cancel
        assertTrue(bookingService.cancelBooking(b.getBookingId()));
        Booking cancelled = bookingService.getBookingByRef(b.getBookingRef()).get();
        assertTrue(cancelled.isCancelled());
        assertEquals("REFUNDED", cancelled.getPaymentStatus());
    }
}