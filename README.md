# Member 5: Ticket & Reservation Management Component
### SE1020 - Object-Oriented Programming University Project

---

## 📌 Assigned Member Responsibilities
- **Subsystem:** Ticket Price Calculation Engine, Tiered Admission Passes (Standard, VIP, Student), Booking Reservations, Unique Reference Code Generator (`CR-YYYYMMDD-XXXX`), Cancellation & Refund Processing.
- **Core OOP Concepts Implemented:**
  - **Abstraction:** Abstract base class `Ticket` declaring contractual pricing and pass methods (`calculatePrice`, `getTicketType`, `getPerksDescription`, `generatePassBarcode`).
  - **Inheritance:** Concrete subclasses `StandardTicket`, `VIPTicket`, and `StudentTicket` extending `Ticket`.
  - **Polymorphism:** Runtime dynamic dispatch for seat tier pricing surcharges and concessions.
  - **Encapsulation:** Unique alphanumeric booking pass generation, immutability of ticket items, and booking lifecycle state machine (`CONFIRMED` &rarr; `CANCELLED`).
  - **Persistence:** Local thread-safe text file I/O (`data/bookings.txt`) via `Repository<Booking, String>`.

---

## 🚀 How to Run in IntelliJ IDEA
1. Open IntelliJ IDEA &rarr; **File** &rarr; **Open...**
2. Select this folder: `Member5_Ticket_Booking_Management`.
3. Allow IntelliJ to import it as a **Maven project**.
4. Navigate to: `src/main/java/com/cinereserve/ticket/TicketBookingManagementMain.java`.
5. Right-click &rarr; **Run 'TicketBookingManagementMain.main()'**.
6. To run tests, right-click `src/test/java/com/cinereserve/ticket/TicketBookingManagementTest.java` &rarr; **Run 'TicketBookingManagementTest'**.

---

## 📤 Step-by-Step Instructions: Push to GitHub from IntelliJ IDEA
1. In IntelliJ IDEA top menu, click **Git** &rarr; **Enable Version Control Integration...**
2. Select **Git** and click **OK**.
3. In the top menu, click **Git** &rarr; **GitHub** &rarr; **Share Project on GitHub**.
4. Enter repository name (e.g., `cinereserve-ticket-booking`), select Public, and click **Share**.