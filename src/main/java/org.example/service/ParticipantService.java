package org.example.service;

import org.example.model.Event;
import org.example.model.Participant;
import org.example.repository.ParticipantRepository;
import java.util.List;

public class ParticipantService {

    private final ParticipantRepository participantRepository;
    private final EventService eventService;

    public ParticipantService(ParticipantRepository participantRepository, EventService eventService) {
        this.participantRepository = participantRepository;
        this.eventService = eventService;
    }

    public List<Participant> getParticipantsByEvent(Long eventId) {
        return participantRepository.findByEventId(eventId);
    }

    public void register(Long eventId, String name, String email) {
        Event event = eventService.getEventById(eventId);

        if (event == null) {
            throw new IllegalArgumentException("Event not found");
        }
        if (event.getAvailableSeats() <= 0) {
            throw new IllegalStateException("No available seats");
        }

        Participant participant = new Participant();
        participant.setEventId(eventId);
        participant.setStudentName(name);
        participant.setStudentEmail(email);

        participantRepository.save(participant);
    }
}