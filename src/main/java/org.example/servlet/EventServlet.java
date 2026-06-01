package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.Event;
import org.example.service.EventService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class EventServlet extends HttpServlet {

    private EventService eventService;

    @Override
    public void init() {
        org.example.repository.EventRepository eventRepository = new org.example.repository.EventRepository();

        this.eventService = new EventService(eventRepository);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<Event> events = eventService.getAllUpcomingEvents();
        req.setAttribute("events", events);
        req.getRequestDispatcher("/WEB-INF/views/events.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String title = req.getParameter("title");
        String dateStr = req.getParameter("eventDate");
        String seatsStr = req.getParameter("maxSeats");

        Event event = new Event();
        event.setTitle(title);
        event.setEventDate(LocalDate.parse(dateStr));
        event.setMaxSeats(Integer.parseInt(seatsStr));

        eventService.createEvent(event);

        resp.sendRedirect(req.getContextPath() + "/events");
    }
}