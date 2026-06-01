package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.Event;
import org.example.model.Participant;
import org.example.service.EventService;
import org.example.service.ParticipantService;

import java.io.IOException;
import java.util.List;

public class EventDetailServlet extends HttpServlet {

    private EventService eventService;
    private ParticipantService participantService;

    @Override
    public void init() {
        org.example.repository.EventRepository eventRepository = new org.example.repository.EventRepository();
        org.example.repository.ParticipantRepository participantRepository = new org.example.repository.ParticipantRepository();

        this.eventService = new EventService(eventRepository);
        this.participantService = new ParticipantService(participantRepository, this.eventService);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Long id = Long.parseLong(req.getParameter("id"));
        Event event = eventService.getEventById(id);
        List<Participant> participants = participantService.getParticipantsByEvent(id);

        req.setAttribute("event", event);
        req.setAttribute("participants", participants);
        req.getRequestDispatcher("/WEB-INF/views/event-detail.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Long id = Long.parseLong(req.getParameter("eventId"));
        String name = req.getParameter("studentName");
        String email = req.getParameter("studentEmail");

        try {
            participantService.register(id, name, email);
        } catch (IllegalStateException e) {
            req.setAttribute("error", "No available seats for this event!");
            Event event = eventService.getEventById(id);
            List<Participant> participants = participantService.getParticipantsByEvent(id);
            req.setAttribute("event", event);
            req.setAttribute("participants", participants);
            req.getRequestDispatcher("/WEB-INF/views/event-detail.jsp").forward(req, resp);
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/event?id=" + id);
    }
}