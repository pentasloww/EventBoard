package org.example.repository;

import org.example.model.Participant;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParticipantRepository {

    public List<Participant> findByEventId(Long eventId) {
        List<Participant> list = new ArrayList<>();
        String sql = "SELECT * FROM participants WHERE event_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, eventId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Participant p = new Participant();
                    p.setId(rs.getLong("id"));
                    p.setEventId(rs.getLong("event_id"));
                    p.setStudentName(rs.getString("student_name"));
                    p.setStudentEmail(rs.getString("student_email"));
                    list.add(p);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void save(Participant participant) {
        String sql = "INSERT INTO participants (event_id, student_name, student_email) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, participant.getEventId());
            ps.setString(2, participant.getStudentName());
            ps.setString(3, participant.getStudentEmail());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}