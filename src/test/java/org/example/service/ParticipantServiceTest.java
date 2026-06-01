package org.example.service;

import org.example.model.Event;
import org.example.repository.ParticipantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ParticipantServiceTest {

    @Mock
    private ParticipantRepository participantRepository;

    @Mock
    private EventService eventService;

    private ParticipantService participantService;

    @BeforeEach
    void setUp() {
        participantService = new ParticipantService(participantRepository, eventService);
    }

    @Test
    void testRegister_Success() {
        // Given: створюємо захід через конструктор без параметрів та сетери
        Long eventId = 1L;
        Event event = new Event();
        event.setId(eventId);
        event.setTitle("JavaPro Workshop");
        event.setEventDate(LocalDate.now().plusDays(5));
        event.setMaxSeats(10);
        event.setAvailableSeats(5); // тут усе ок, сетер є в моделі

        when(eventService.getEventById(eventId)).thenReturn(event);

        // When & Then
        assertDoesNotThrow(() -> participantService.register(eventId, "Mykyta Sirobaba", "mykyta@example.com"));

        verify(participantRepository, times(1)).save(any());
    }

    @Test
    void testRegister_NoAvailableSeats_ThrowsException() {
        // Given: створюємо захід, де вільних місць немає
        Long eventId = 2L;
        Event event = new Event();
        event.setId(eventId);
        event.setTitle("Sold Out Event");
        event.setEventDate(LocalDate.now().plusDays(2));
        event.setMaxSeats(10);
        event.setAvailableSeats(0);

        when(eventService.getEventById(eventId)).thenReturn(event);

        // When & Then
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            participantService.register(eventId, "Ivan Ivanov", "ivan@example.com");
        });

        assertEquals("No available seats", exception.getMessage());

        verify(participantRepository, never()).save(any());
    }
}