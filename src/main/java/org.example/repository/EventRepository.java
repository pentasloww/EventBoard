package org.example.repository;

import org.example.model.Event;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventRepository {

    public List<Event> findAll() {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT e.id, e.title, e.event_date, e.max_seats, " +
                "(e.max_seats - COUNT(p.id)) AS available_seats " +
                "FROM events e " +
                "LEFT JOIN participants p ON e.id = p.event_id " +
                "WHERE e.event_date >= CURRENT_DATE " +
                "GROUP BY e.id, e.title, e.event_date, e.max_seats " +
                "ORDER BY e.event_date";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Event event = new Event();
                event.setId(rs.getLong("id"));
                event.setTitle(rs.getString("title"));
                event.setEventDate(rs.getDate("event_date").toLocalDate());
                event.setMaxSeats(rs.getInt("max_seats"));
                event.setAvailableSeats(rs.getInt("available_seats"));
                events.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // В реальних проєктах краще логувати, але для ТЗ поки ок
        }
        return events;
    }

    public Event findById(Long id) {
        String sql = "SELECT e.id, e.title, e.event_date, e.max_seats, " +
                "(e.max_seats - COUNT(p.id)) AS available_seats " +
                "FROM events e " +
                "LEFT JOIN participants p ON e.id = p.event_id " +
                "WHERE e.id = ? " +
                "GROUP BY e.id, e.title, e.event_date, e.max_seats";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Event event = new Event();
                    event.setId(rs.getLong("id"));
                    event.setTitle(rs.getString("title"));
                    event.setEventDate(rs.getDate("event_date").toLocalDate());
                    event.setMaxSeats(rs.getInt("max_seats"));
                    event.setAvailableSeats(rs.getInt("available_seats"));
                    return event;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void save(Event event) {
        String sql = "INSERT INTO events (title, event_date, max_seats) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, event.getTitle());
            ps.setDate(2, Date.valueOf(event.getEventDate()));
            ps.setInt(3, event.getMaxSeats());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}