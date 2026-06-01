package org.example.service;

import org.example.model.Event;
import org.example.repository.EventRepository;
import java.util.List;

public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getAllUpcomingEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id);
    }

    public void createEvent(Event event) {
        eventRepository.save(event);
    }
}