package org.example.model;

import java.time.LocalDate;

public class Event {
    private Long id;
    private String title;
    private LocalDate eventDate;
    private int maxSeats;
    private int availableSeats;

    public Event() {}

    public Event(Long id, String title, LocalDate eventDate, int maxSeats) {
        this.id = id;
        this.title = title;
        this.eventDate = eventDate;
        this.maxSeats = maxSeats;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public LocalDate getEventDate() { return eventDate; }
    public void setEventDate(LocalDate eventDate) { this.eventDate = eventDate; }

    public int getMaxSeats() { return maxSeats; }
    public void setMaxSeats(int maxSeats) { this.maxSeats = maxSeats; }

    public int getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(int availableSeats) { this.availableSeats = availableSeats; }
}