package com.cinereserve.ticket.repository;

import com.cinereserve.ticket.model.Booking;
import com.cinereserve.ticket.util.StringUtil;

import java.util.*;

public class BookingRepository implements Repository<Booking, String> {

    private static final String FILE_NAME = "bookings.txt";
    private final FileStorageService storage = FileStorageService.getInstance();

    public BookingRepository() {
        storage.ensureFileExists(FILE_NAME);
    }

    @Override
    public Optional<Booking> findById(String id) {
        if (id == null) return Optional.empty();
        return findAll().stream().filter(b -> id.equalsIgnoreCase(b.getBookingId())).findFirst();
    }

    public Optional<Booking> findByRef(String ref) {
        if (ref == null) return Optional.empty();
        return findAll().stream().filter(b -> ref.equalsIgnoreCase(b.getBookingRef())).findFirst();
    }

    @Override
    public List<Booking> findAll() {
        List<String> lines = storage.readAllLines(FILE_NAME);
        List<Booking> list = new ArrayList<>();
        for (String line : lines) {
            Booking b = parseLine(line);
            if (b != null) list.add(b);
        }
        return list;
    }

    @Override
    public boolean save(Booking b) {
        if (b == null || existsById(b.getBookingId())) return false;
        return storage.appendLine(FILE_NAME, formatLine(b));
    }

    @Override
    public boolean update(Booking b) {
        if (b == null) return false;
        List<Booking> all = findAll();
        boolean found = false;
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getBookingId().equalsIgnoreCase(b.getBookingId())) {
                all.set(i, b);
                found = true;
                break;
            }
        }
        if (!found) return false;
        List<String> lines = new ArrayList<>();
        for (Booking item : all) lines.add(formatLine(item));
        return storage.writeAllLines(FILE_NAME, lines);
    }

    @Override
    public boolean deleteById(String id) {
        List<Booking> all = findAll();
        boolean removed = all.removeIf(b -> b.getBookingId().equalsIgnoreCase(id));
        if (!removed) return false;
        List<String> lines = new ArrayList<>();
        for (Booking item : all) lines.add(formatLine(item));
        return storage.writeAllLines(FILE_NAME, lines);
    }

    @Override
    public boolean existsById(String id) {
        return findById(id).isPresent();
    }

    private Booking parseLine(String line) {
        try {
            String[] p = StringUtil.splitRecord(line);
            if (p.length < 13) return null;
            return new Booking(
                    p[0], p[1], p[2], p[3], p[4], p[5],
                    Double.parseDouble(p[6]),
                    Double.parseDouble(p[7]),
                    Double.parseDouble(p[8]),
                    p[9], p[10], p[11], p[12]
            );
        } catch (Exception e) {
            return null;
        }
    }

    private String formatLine(Booking b) {
        return StringUtil.joinRecord(
                b.getBookingId(),
                b.getBookingRef(),
                b.getUserId(),
                b.getShowtimeId(),
                b.getSeatNumbersCsv(),
                b.getTicketTypesCsv(),
                String.format(Locale.US, "%.2f", b.getTotalAmount()),
                String.format(Locale.US, "%.2f", b.getDiscountAmount()),
                String.format(Locale.US, "%.2f", b.getFinalAmount()),
                b.getPaymentMethod(),
                b.getPaymentStatus(),
                b.getBookingStatus(),
                b.getBookingTime()
        );
    }
}