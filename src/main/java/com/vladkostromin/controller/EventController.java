package com.vladkostromin.controller;

import com.vladkostromin.model.Event;
import com.vladkostromin.service.EventService;

import java.util.List;

public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    public Event getEvent(Integer id) {
        return eventService.getEventById(id);
    }

    public Event createEvent(Event event) {
        return eventService.saveEvent(event);
    }

    public Event updateEvent(Event event) {
        return eventService.updateEvent(event);
    }

    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    public Event deleteEvent(Integer id) {
        return eventService.deleteEvent(id);
    }

}
