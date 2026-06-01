package org.example.model;

public class Participant {
    private Long id;
    private Long eventId;
    private String studentName;
    private String studentEmail;

    public Participant() {}

    public Participant(Long id, Long eventId, String studentName, String studentEmail) {
        this.id = id;
        this.eventId = eventId;
        this.studentName = studentName;
        this.studentEmail = studentEmail;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getEventId() { return eventId; }
    public void setEventId(Long eventId) { this.eventId = eventId; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getStudentEmail() { return studentEmail; }
    public void setStudentEmail(String studentEmail) { this.studentEmail = studentEmail; }
}